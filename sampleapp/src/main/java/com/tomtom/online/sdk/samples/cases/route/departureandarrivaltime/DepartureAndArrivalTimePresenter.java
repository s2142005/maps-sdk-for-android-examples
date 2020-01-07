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
package com.tomtom.online.sdk.samples.cases.route.departureandarrivaltime;

import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteQueryFactory;
import com.tomtom.online.sdk.samples.routes.AmsterdamToRotterdamRouteConfig;

import org.joda.time.DateTime;

import java.util.Date;

public class DepartureAndArrivalTimePresenter extends RoutePlannerPresenter {

    public DepartureAndArrivalTimePresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new DepartureAndArrivalTimeFunctionalExample();
    }

    public void clearRoute() {
        tomtomMap.clearRoute();
    }

    public void displayArrivalAtRoute(DateTime arrivalDateTime) {
        viewModel.showRoutingInProgressDialog();
        showRoute(getArrivalRouteQuery(arrivalDateTime.toDate()));
    }

    @VisibleForTesting
    protected RouteQuery getArrivalRouteQuery(Date arrivalTime) {
        return RouteQueryFactory.createArrivalRouteQuery(arrivalTime, new AmsterdamToRotterdamRouteConfig());
    }

    public void displayDepartureAtRoute(DateTime departureDate) {
        viewModel.showRoutingInProgressDialog();
        showRoute(getDepartureRouteQuery(departureDate.toDate()));
    }

    protected RouteQuery getDepartureRouteQuery(Date departureTime) {
        return RouteQueryFactory.createDepartureRouteQuery(departureTime, new AmsterdamToRotterdamRouteConfig());
    }

}
