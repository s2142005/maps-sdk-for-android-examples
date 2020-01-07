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
package com.tomtom.online.sdk.samples.cases.route.avoid;

import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.routing.data.Avoid;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteQueryFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.routes.AmsterdamToOsloRouteConfig;

public class RouteAvoidsPresenter extends RoutePlannerPresenter {

    private Avoid avoidOnRoute;

    public RouteAvoidsPresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        if (avoidOnRoute != null) {
            displayRoute(avoidOnRoute);
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new RouteAvoidsFunctionalExample();
    }

    void displayRoute(Avoid routeAvoid) {
        tomtomMap.clearRoute();
        viewModel.showRoutingInProgressDialog();
        showRoute(getRouteQuery(routeAvoid));
    }

    @VisibleForTesting
    protected RouteQuery getRouteQuery(Avoid routeAvoid) {
       return RouteQueryFactory.createAvoidRouteQuery(routeAvoid,  new AmsterdamToOsloRouteConfig());
    }

    public void startRoutingAvoidTollRoads() {
        displayRoute(Avoid.TOLL_ROADS);
    }

    public void startRoutingAvoidFerries() {
        displayRoute(Avoid.FERRIES);
    }

    public void startRoutingAvoidMotorways() {
        displayRoute(Avoid.MOTORWAYS);
    }

    public Avoid getAvoid() {
        return avoidOnRoute;
    }

    public void setAvoid(Avoid avoidOnRoute) {
        this.avoidOnRoute = avoidOnRoute;
    }

}
