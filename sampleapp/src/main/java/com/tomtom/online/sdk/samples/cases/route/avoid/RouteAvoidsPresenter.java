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
package com.tomtom.online.sdk.samples.cases.route.avoid;

import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.routing.route.RouteSpecification;
import com.tomtom.online.sdk.routing.route.description.AvoidType;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteSpecificationFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.routes.AmsterdamToOsloRouteConfig;

public class RouteAvoidsPresenter extends RoutePlannerPresenter {

    private AvoidType avoidOnRoute;

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

    void displayRoute(AvoidType routeAvoid) {
        tomtomMap.clearRoute();
        viewModel.showRoutingInProgressDialog();
        showRoute(getRouteSpecification(routeAvoid));
    }

    @VisibleForTesting
    protected RouteSpecification getRouteSpecification(AvoidType routeAvoid) {
        return RouteSpecificationFactory.createAvoidRouteSpecification(routeAvoid, new AmsterdamToOsloRouteConfig());
    }

    public void startRoutingAvoidTollRoads() {
        displayRoute(AvoidType.TOLL_ROADS);
    }

    public void startRoutingAvoidFerries() {
        displayRoute(AvoidType.FERRIES);
    }

    public void startRoutingAvoidMotorways() {
        displayRoute(AvoidType.MOTORWAYS);
    }

    public AvoidType getAvoid() {
        return avoidOnRoute;
    }

    public void setAvoid(AvoidType avoidOnRoute) {
        this.avoidOnRoute = avoidOnRoute;
    }

}
