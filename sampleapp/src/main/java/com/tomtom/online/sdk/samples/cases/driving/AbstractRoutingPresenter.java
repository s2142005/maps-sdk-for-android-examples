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
package com.tomtom.online.sdk.samples.cases.driving;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.common.service.ServiceException;
import com.tomtom.online.sdk.map.ChevronPosition;
import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.map.RouteBuilder;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RouteCallback;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.routing.data.RouteResponse;
import com.tomtom.online.sdk.samples.routes.LodzCityCenterRouteConfig;
import com.tomtom.online.sdk.samples.routes.RouteConfigExample;

import java.util.List;

public abstract class AbstractRoutingPresenter extends AbstractTrackingPresenter {

    protected static final RouteConfigExample ROUTE_CONFIG = new LodzCityCenterRouteConfig();

    @Override
    protected void onPresenterCreated() {
        super.onPresenterCreated();
        planRoute();
    }

    @Override
    protected void onPresenterRestored() {
        super.onPresenterRestored();
        restoreRouteOverview();
    }

    private void planRoute() {
        RoutingApi routingApi = OnlineRoutingApi.create(getContext());
        routingApi.planRoute(getRouteQuery(), routeCallback);
    }

    private RouteQuery getRouteQuery() {
        return RouteQueryBuilder.create(ROUTE_CONFIG.getOrigin(), ROUTE_CONFIG.getDestination())
                .withWayPointsList(ROUTE_CONFIG.getWaypoints())
                .build();
    }

    private FullRoute getFirstRouteFromResponse(RouteResponse getFirstRouteFromResponse) {
        return getFirstRouteFromResponse.getRoutes().get(0);
    }

    private LatLng getRouteOrigin(FullRoute fullRoute) {
        return fullRoute.getCoordinates().get(0);
    }

    protected List<LatLng> getFirstRouteFromMap() {
        return tomtomMap.getRoutes().get(0).getCoordinates();
    }

    protected List<Route> getRoutesFromMap() {
        return tomtomMap.getRoutes();
    }

    protected void restoreRouteOverview() {
        if (tomtomMap.getDrivingSettings().isTracking()) {
            tomtomMap.getRouteSettings().displayRoutesOverview();
        }
    }

    protected void showRoute(FullRoute route) {
        tomtomMap.addRoute(new RouteBuilder(route.getCoordinates()));
        tomtomMap.getRouteSettings().displayRoutesOverview();
    }

    private RouteCallback routeCallback = new RouteCallback() {
        @Override
        public void onRoutePlannerResponse(@NonNull RouteResponse routeResult) {
            FullRoute route = getFirstRouteFromResponse(routeResult);
            onRouteReady(route);
        }

        @Override
        public void onRoutePlannerError(ServiceException exception) {
            onRouteError(exception);
        }
    };

    protected void onRouteReady(FullRoute fullRoute) {
        ChevronPosition chevronPosition = new ChevronPosition.Builder(getRouteOrigin(fullRoute).toLocation()).build();
        getChevron().setPosition(chevronPosition);
        showRoute(fullRoute);
        restoreSimulator();
    }

    protected void onRouteError(ServiceException exception) {
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }

}
