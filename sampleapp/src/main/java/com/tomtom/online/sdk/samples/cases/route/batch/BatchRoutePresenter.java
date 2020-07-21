/**
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.cases.route.batch;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.google.common.base.Optional;
import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.map.RouteSettings;
import com.tomtom.online.sdk.map.RouteStyle;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.RoutingException;
import com.tomtom.online.sdk.routing.batch.BatchRoutesCallback;
import com.tomtom.online.sdk.routing.batch.BatchRoutesPlan;
import com.tomtom.online.sdk.routing.batch.BatchRoutesSpecification;
import com.tomtom.online.sdk.routing.route.RoutePlan;
import com.tomtom.online.sdk.routing.route.RouteSpecification;
import com.tomtom.online.sdk.routing.route.description.AvoidType;
import com.tomtom.online.sdk.routing.route.description.RouteType;
import com.tomtom.online.sdk.routing.route.description.TravelMode;
import com.tomtom.online.sdk.routing.route.information.FullRoute;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteSpecificationFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.routes.AmsterdamToOsloRouteConfig;
import com.tomtom.online.sdk.samples.routes.AmsterdamToRotterdamRouteConfig;
import com.tomtom.online.sdk.samples.routes.RouteConfigExample;
import com.tomtom.online.sdk.samples.utils.RouteUtils;

import java.util.ArrayList;
import java.util.List;

public class BatchRoutePresenter extends RoutePlannerPresenter {

    public static int[] routesDescription = new int[]{R.string.empty, R.string.empty};

    public BatchRoutePresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        tomtomMap.addOnRouteClickListener(onRouteClickListener);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        tomtomMap.removeOnRouteClickListener(onRouteClickListener);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new BatchRouteFunctionalExample();
    }

    public void startRoutingDependsOnTravelMode() {
        planRoute(getTravelModeBatchRouteSpecification());
    }

    public void startRoutingDependsOnRouteType() {
        planRoute(getRouteTypeBatchRouteSpecification());
    }

    public void startRoutingDependsOnAvoids() {
        planRoute(getAvoidsBatchRouteSpecification());
    }

    private void planRoute(BatchRoutesSpecification specification) {
        clearRouteSelection();
        viewModel.showRoutingInProgressDialog();
        //tag::doc_execute_batch_routing[]
        routingApi.planRoutes(specification, batchRoutesCallback);
        //end::doc_execute_batch_routing[]
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

    private void clearRouteSelection() {
        tomtomMap.clearRoute();
        routesMap.clear();
    }

    private void displayRouteAndSetDescription(BatchRoutesPlan batchRoutesPlan) {
        tomtomMap.clearRoute();
        int i = 0;
        for (RoutePlan routeResponse : batchRoutesPlan.getRoutes()) {
            for (FullRoute fullRoute : routeResponse.getRoutes()) {
                fullRoute.setTag(getContext().getString(routesDescription[i++]));
            }
            displayFullRoutes(routeResponse);
        }

        if (batchRoutesPlan.getRoutes().isEmpty()) {
            return;
        }

        setRouteActiveIfApply();
        displayInfoAboutRouteIfApply(batchRoutesPlan);
        tomtomMap.displayRoutesOverview();
    }

    private void setRouteActiveIfApply() {
        FuncUtils.apply(getFirstRoute(tomtomMap.getRouteSettings().getRoutes()), route -> {
            long routeId = route.getId();
            RouteSettings routeSettings = tomtomMap.getRouteSettings();

            RouteUtils.setRoutesInactive(routeSettings);
            RouteUtils.setRouteActive(routeId, routeSettings);
        });
    }

    private void displayInfoAboutRouteIfApply(BatchRoutesPlan batchRoutesPlan) {
        List<FullRoute> routes = batchRoutesPlan.getRoutes().get(0).getRoutes();
        FuncUtils.apply(getFirstFullRoute(routes), this::displayInfoAboutRoute);
    }

    private Optional<Route> getFirstRoute(List<Route> routes) {
        if (routes.isEmpty()) {
            return Optional.absent();
        }
        return Optional.of(routes.get(0));
    }

    private Optional<FullRoute> getFirstFullRoute(List<FullRoute> fullRoutes) {
        if (fullRoutes.isEmpty()) {
            return Optional.absent();
        }
        return Optional.of(fullRoutes.get(0));
    }

    @Override
    protected void selectFirstRouteAsActive(RouteStyle routeStyle) {
        // Not needed
    }

    @VisibleForTesting
    protected BatchRoutesSpecification getTravelModeBatchRouteSpecification() {
        routesDescription = new int[]{R.string.batch_travel_mode_car_text, R.string.batch_travel_mode_truck_text, R.string.batch_travel_mode_pedestrian_text};
        List<RouteSpecification> routeSpecifications = new ArrayList<>();
        routeSpecifications.add(getTravelModeSpecification(TravelMode.CAR));
        routeSpecifications.add(getTravelModeSpecification(TravelMode.TRUCK));
        routeSpecifications.add(getTravelModeSpecification(TravelMode.PEDESTRIAN));

        //tag::doc_batch_specification[]
        return new BatchRoutesSpecification.Builder()
                .routeSpecifications(routeSpecifications)
                .build();
        //end::doc_batch_specification[]
    }

    @NonNull
    private RouteSpecification getTravelModeSpecification(TravelMode travelMode) {
        //tag::doc_common_params_for_travel_mode[]
        return RouteSpecificationFactory.createRouteTravelModesSpecification(travelMode, getRouteConfig());
        //end::doc_common_params_for_travel_mode[]
    }

    @VisibleForTesting
    protected BatchRoutesSpecification getRouteTypeBatchRouteSpecification() {
        routesDescription = new int[]{R.string.batch_route_type_fastest, R.string.batch_route_type_shortest, R.string.batch_route_type_eco};
        List<RouteSpecification> routeSpecifications = new ArrayList<>();
        routeSpecifications.add(getRouteTypeSpecification(RouteType.FASTEST));
        routeSpecifications.add(getRouteTypeSpecification(RouteType.SHORTEST));
        routeSpecifications.add(getRouteTypeSpecification(RouteType.ECO));

        return new BatchRoutesSpecification.Builder()
                .routeSpecifications(routeSpecifications)
                .build();
    }

    @NonNull
    private RouteSpecification getRouteTypeSpecification(RouteType type) {
        return RouteSpecificationFactory.createRouteTypesSpecification(type, getRouteConfig());
    }

    @VisibleForTesting
    protected BatchRoutesSpecification getAvoidsBatchRouteSpecification() {
        routesDescription = new int[]{R.string.batch_avoid_motorways, R.string.batch_avoid_toll_roads, R.string.batch_avoid_ferries};
        List<RouteSpecification> routeSpecifications = new ArrayList<>();
        routeSpecifications.add(getAvoidRouteSpecification(AvoidType.MOTORWAYS));
        routeSpecifications.add(getAvoidRouteSpecification(AvoidType.TOLL_ROADS));
        routeSpecifications.add(getAvoidRouteSpecification(AvoidType.FERRIES));

        return new BatchRoutesSpecification.Builder()
                .routeSpecifications(routeSpecifications)
                .build();
    }

    @NonNull
    private RouteSpecification getAvoidRouteSpecification(AvoidType avoidType) {
        return RouteSpecificationFactory.createAvoidRouteSpecification(avoidType, getRouteOsloConfig());
    }

    public RouteConfigExample getRouteOsloConfig() {
        return new AmsterdamToOsloRouteConfig();
    }

    public RouteConfigExample getRouteConfig() {
        return new AmsterdamToRotterdamRouteConfig();
    }

    private TomtomMapCallback.OnRouteClickListener onRouteClickListener = route -> {
        long routeId = route.getId();

        RouteUtils.setRoutesInactive(tomtomMap.getRouteSettings());
        RouteUtils.setRouteActive(routeId, tomtomMap.getRouteSettings());

        FullRoute fullRoute = routesMap.get(routeId);
        displayInfoAboutRoute(fullRoute);
    };

}
