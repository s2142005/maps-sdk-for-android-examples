/**
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.cases.route.batch.reachablerange;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.PolylineBuilder;
import com.tomtom.online.sdk.map.RouteStyle;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.RoutingException;
import com.tomtom.online.sdk.routing.batch.BatchRoutesCallback;
import com.tomtom.online.sdk.routing.batch.BatchRoutesPlan;
import com.tomtom.online.sdk.routing.batch.BatchRoutesSpecification;
import com.tomtom.online.sdk.routing.reachablerange.ReachableRangeSpecification;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.MultiRoutesRoutingUiListener;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.reachablerange.ReachableRangeSpecificationFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;

import java.util.ArrayList;
import java.util.List;

public class BatchReachableRangePresenter extends RoutePlannerPresenter {

    private final static LatLng ARNHEM_LOCATION = new LatLng(52.006572, 5.820376);
    private final static int DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 6;
    private final TomtomMapCallback.OnPolylineClickListener onPolylineClickListener = polyline -> {
        if (viewModel instanceof MultiRoutesRoutingUiListener) {
            ((MultiRoutesRoutingUiListener) viewModel).updateTextOnCurrentRouteBar(
                    polyline.getTag().toString(), view.getString(R.string.empty));
        }
    };

    BatchReachableRangePresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new BatchReachableRangeFunctionalExample();
    }

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        setupIfInitialization(view);
        initPolylineClickListeners();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        tomtomMap.removeOnPolylineClickListener(onPolylineClickListener);
    }

    private void setupIfInitialization(FunctionalExampleFragment view) {
        if (!view.isMapRestored()) {
            planBatchReachableRange(getReachableRangeBatchRouteSpecification());
        }
    }

    private void initPolylineClickListeners() {
        tomtomMap.addOnPolylineClickListener(onPolylineClickListener);
    }

    @VisibleForTesting
    void planBatchReachableRange(BatchRoutesSpecification batchRoutesSpecification) {
        tomtomMap.clear();
        viewModel.showRoutingInProgressDialog();
        //tag::doc_submit_specification_to_batch_routing[]
        getRoutePlannerAPI().planRoutes(batchRoutesSpecification, batchRoutesCallback);
        //end::doc_submit_specification_to_batch_routing[]
    }

    private BatchRoutesCallback batchRoutesCallback = new BatchRoutesCallback() {
        @Override
        public void onSuccess(@NonNull BatchRoutesPlan routePlan) {
            displayRouteAndSetDescription(routePlan);
        }

        @Override
        public void onError(@NonNull RoutingException error) {
            proceedWithError(error.getMessage());
        }
    };

    @VisibleForTesting
    RoutingApi getRoutePlannerAPI() {
        return routingApi;
    }

    private void displayRouteAndSetDescription(BatchRoutesPlan batchRoutesPlan) {
        FuncUtils.forEachIndexed(batchRoutesPlan.getReachableRangeAreas(),
                (area, index) -> {
                    List<LatLng> boundary = area.getBoundary();
                    LatLng center = area.getCenter();

                    List<LatLng> closedPolylineCoordinates = normalizeToClosedPolyline(boundary);

                    tomtomMap.addMarker(new MarkerBuilder(center).icon(defaultStartIcon));
                    tomtomMap.getOverlaySettings()
                            .addOverlay(PolylineBuilder.create()
                                    .coordinates(closedPolylineCoordinates)
                                    .color(determinePolylineColor(index))
                                    .tag(determinePolylineDescription(index))
                                    .build());
                });
        viewModel.hideRoutingInProgressDialog();
    }

    @NonNull
    private List<LatLng> normalizeToClosedPolyline(List<LatLng> boundary) {
        if (!boundary.isEmpty()) {
            boundary.add(boundary.get(0));
        }
        return boundary;
    }

    private String determinePolylineDescription(int index) {
        switch (index) {
            case 0:
                return view.getString(R.string.label_batch_reachablerange_electric);
            case 1:
                return view.getString(R.string.label_batch_reachablerange_combustion);
            default:
                return view.getString(R.string.label_batch_reachablerange_electric_2h);
        }
    }

    private int determinePolylineColor(int index) {
        switch (index) {
            case 0:
                return Color.GREEN;
            case 1:
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }

    @Override
    protected void selectFirstRouteAsActive(RouteStyle routeStyle) {
        // Not needed
    }

    @Override
    public void centerOnDefaultLocation() {
        tomtomMap.centerOn(CameraPosition.builder(ARNHEM_LOCATION)
                .bearing(MapConstants.ORIENTATION_NORTH)
                .zoom(DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE)
                .build());
    }

    @VisibleForTesting
    protected BatchRoutesSpecification getReachableRangeBatchRouteSpecification() {
        ReachableRangeSpecificationFactory factory = new ReachableRangeSpecificationFactory();

        List<ReachableRangeSpecification> batchRoutesSpecifications = new ArrayList<>();
        batchRoutesSpecifications.add(factory.createReachableRangeSpecificationForElectric());
        batchRoutesSpecifications.add(factory.createReachableRangeSpecificationForCombustion());
        batchRoutesSpecifications.add(factory.createReachableRangeSpecificationForElectricLimitTo2Hours());

        return
                //tag::doc_create_batch_reachable_range_specification[]
                new BatchRoutesSpecification.Builder()
                        .reachableRangeSpecifications(batchRoutesSpecifications)
                        .build();
        //end::doc_create_batch_reachable_range_specification[]
    }
}