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
import com.tomtom.online.sdk.routing.data.Avoid;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteResponse;
import com.tomtom.online.sdk.routing.data.RouteType;
import com.tomtom.online.sdk.routing.data.TravelMode;
import com.tomtom.online.sdk.routing.data.batch.BatchRoutingQuery;
import com.tomtom.online.sdk.routing.data.batch.BatchRoutingQueryBuilder;
import com.tomtom.online.sdk.routing.data.batch.BatchRoutingResponse;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteQueryFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.routes.AmsterdamToOsloRouteConfig;
import com.tomtom.online.sdk.samples.routes.AmsterdamToRotterdamRouteConfig;
import com.tomtom.online.sdk.samples.routes.RouteConfigExample;
import com.tomtom.online.sdk.samples.utils.RouteUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;

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
        planRoute(getTravelModeBatchRouteQuery());
    }

    public void startRoutingDependsOnRouteType() {
        planRoute(getRouteTypeBatchRouteQuery());
    }

    public void startRoutingDependsOnAvoids() {
        planRoute(getAvoidsBatchRouteQuery());
    }

    private void planRoute(BatchRoutingQuery query) {
        clearRouteSelection();
        viewModel.showRoutingInProgressDialog();
        //tag::doc_execute_batch_routing[]
        Disposable subscribe = routePlannerAPI.planBatchRoute(query)
                //end::doc_execute_batch_routing[]
                .subscribeOn(getWorkingScheduler())
                .observeOn(getResultScheduler())
                .subscribe(this::displayRouteAndSetDescription,
                        throwable -> proceedWithError(throwable.getMessage()));

        compositeDisposable.add(subscribe);
    }

    private void clearRouteSelection() {
        tomtomMap.clearRoute();
        routesMap.clear();
    }

    private void displayRouteAndSetDescription(BatchRoutingResponse batchRoutingResponse) {
        tomtomMap.clearRoute();
        int i = 0;
        for (RouteResponse routeResponse : batchRoutingResponse.getRouteRoutingResponses()) {
            for (FullRoute fullRoute : routeResponse.getRoutes()) {
                fullRoute.setTag(getContext().getString(routesDescription[i++]));
            }
            displayFullRoutes(routeResponse);
        }

        if (batchRoutingResponse.getRouteRoutingResponses().isEmpty()) {
            return;
        }

        setRouteActiveIfApply();
        displayInfoAboutRouteIfApply(batchRoutingResponse);
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

    private void displayInfoAboutRouteIfApply(BatchRoutingResponse batchRoutingResponse) {
        List<FullRoute> routes = batchRoutingResponse.getRouteRoutingResponses().get(0).getRoutes();
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
    protected BatchRoutingQuery getTravelModeBatchRouteQuery() {
        routesDescription = new int[]{R.string.batch_travel_mode_car_text, R.string.batch_travel_mode_truck_text, R.string.batch_travel_mode_pedestrian_text};
        //tag::doc_batch_query[]
        return BatchRoutingQueryBuilder.create()
                .withRouteQuery(getTravelModeQuery(TravelMode.CAR))
                .withRouteQuery(getTravelModeQuery(TravelMode.TRUCK))
                .withRouteQuery(getTravelModeQuery(TravelMode.PEDESTRIAN))
                .build();
        //end::doc_batch_query[]
    }

    @NonNull
    private RouteQuery getTravelModeQuery(TravelMode travelMode) {
        //tag::doc_common_params_for_travel_mode[]
        return RouteQueryFactory.createRouteTravelModesQuery(travelMode, getRouteConfig());
        //end::doc_common_params_for_travel_mode[]
    }

    @VisibleForTesting
    protected BatchRoutingQuery getRouteTypeBatchRouteQuery() {
        routesDescription = new int[]{R.string.batch_route_type_fastest, R.string.batch_route_type_shortest, R.string.batch_route_type_eco};
        return BatchRoutingQueryBuilder.create()
                .withRouteQuery(getRouteTypeQuery(RouteType.FASTEST))
                .withRouteQuery(getRouteTypeQuery(RouteType.SHORTEST))
                .withRouteQuery(getRouteTypeQuery(RouteType.ECO))
                .build();
    }

    @NonNull
    private RouteQuery getRouteTypeQuery(RouteType type) {
        return RouteQueryFactory.createRouteTypesQuery(type, getRouteConfig());
    }

    @VisibleForTesting
    protected BatchRoutingQuery getAvoidsBatchRouteQuery() {
        routesDescription = new int[]{R.string.batch_avoid_motorways, R.string.batch_avoid_toll_roads, R.string.batch_avoid_ferries};
        return BatchRoutingQueryBuilder.create()
                .withRouteQuery(getAvoidRouteQuery(Avoid.MOTORWAYS))
                .withRouteQuery(getAvoidRouteQuery(Avoid.TOLL_ROADS))
                .withRouteQuery(getAvoidRouteQuery(Avoid.FERRIES))
                .build();
    }

    @NonNull
    private RouteQuery getAvoidRouteQuery(Avoid avoidType) {
        return RouteQueryFactory.createAvoidRouteQuery(avoidType, getRouteOsloConfig());
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
