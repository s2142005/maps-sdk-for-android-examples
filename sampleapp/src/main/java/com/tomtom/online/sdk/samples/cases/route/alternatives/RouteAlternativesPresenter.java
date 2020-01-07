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
package com.tomtom.online.sdk.samples.cases.route.alternatives;

import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.map.RouteSettings;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteQueryFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.routes.AmsterdamToRotterdamRouteConfig;
import com.tomtom.online.sdk.samples.utils.RouteUtils;

public class RouteAlternativesPresenter extends RoutePlannerPresenter {

    public RouteAlternativesPresenter(RoutingUiListener viewModel) {
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
        return new RouteAlternativesFunctionalExample();
    }

    public void startRouting(int maxAlternatives) {

        tomtomMap.clearRoute();
        viewModel.showRoutingInProgressDialog();
        showRoute(getRouteQuery(maxAlternatives));
    }

    @VisibleForTesting
    protected RouteQuery getRouteQuery(int maxAlternatives) {
        return RouteQueryFactory.createRouteAlternativesQuery(maxAlternatives, new AmsterdamToRotterdamRouteConfig());
    }

    private TomtomMapCallback.OnRouteClickListener onRouteClickListener = route -> {
        long routeId = route.getId();
        RouteSettings routeSettings = tomtomMap.getRouteSettings();

        RouteUtils.setRoutesInactive(routeSettings);
        RouteUtils.setRouteActive(routeId, routeSettings);

        FullRoute fullRoute = routesMap.get(routeId);
        displayInfoAboutRoute(fullRoute);
    };

}
