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
package com.tomtom.online.sdk.samples.cases.route.maneuvers;

import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.routing.route.RouteSpecification;
import com.tomtom.online.sdk.routing.route.information.FullRoute;
import com.tomtom.online.sdk.routing.route.information.Instruction;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteSpecificationFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.routes.AmsterdamToBerlinRouteConfig;

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
    protected void displayInfoAboutRoute(FullRoute routeResult) {
        super.displayInfoAboutRoute(routeResult);
        //tag::doc_route_maneuvers[]
        List<Instruction> instructions = routeResult.getGuidance().getInstructions();
        //end::doc_route_maneuvers[]
        maneuversFragment.updateInstructions(instructions);
    }

    public void startRouting(String language) {
        tomtomMap.clearRoute();
        viewModel.showRoutingInProgressDialog();
        showRoute(getRouteSpecification(language));
    }

    @VisibleForTesting
    protected RouteSpecification getRouteSpecification(String language) {
        return RouteSpecificationFactory.createRouteManeuversSpecification(language, new AmsterdamToBerlinRouteConfig());
    }
}
