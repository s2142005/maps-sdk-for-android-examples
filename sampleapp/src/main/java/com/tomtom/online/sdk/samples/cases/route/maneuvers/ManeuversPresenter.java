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
package com.tomtom.online.sdk.samples.cases.route.maneuvers;

import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.Instruction;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteQueryFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.routes.AmsterdamToBerlinRouteConfig;

import java.util.Arrays;
import java.util.List;

public class ManeuversPresenter extends RoutePlannerPresenter {

    private ManeuversFragment maneuversFragment;

    public ManeuversPresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        maneuversFragment = (ManeuversFragment) view;
        maneuversFragment.onMapReady();
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new ManeuversFunctionalExample();
    }

    @Override
    //tag::doc_route_maneuvers[]
    protected void displayInfoAboutRoute(FullRoute routeResult) {
        super.displayInfoAboutRoute(routeResult);
        List<Instruction> instructions = Arrays.asList(routeResult.getGuidance().getInstructions());
        maneuversFragment.updateInstructions(instructions);
    }
    //end::doc_route_maneuvers[]

    public void startRouting(String language){
        tomtomMap.clearRoute();
        viewModel.showRoutingInProgressDialog();
        showRoute(getRouteQuery(language));
    }

    @VisibleForTesting
    protected RouteQuery getRouteQuery(String language) {
        return RouteQueryFactory.createRouteManeuversQuery(language, new AmsterdamToBerlinRouteConfig());
    }
}
