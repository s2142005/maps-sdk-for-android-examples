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
package com.tomtom.online.sdk.samples.cases.search;

import android.content.Context;

import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteQueryFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.routes.AmsterdamToHaarlemRouteConfig;

import timber.log.Timber;

public class SearchAlongRoutePresenter extends RoutePlannerPresenter {

    protected Context context;
    protected FunctionalExampleFragment fragment;
    private SearchAlongRouteRequester searchRequester;

    public SearchAlongRoutePresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        context = view.getContext();
        fragment = view;

        searchRequester = new SearchAlongRouteRequester(view.getContext(), new SearchAlongRouteResultDisplay(tomtomMap, view));
        displayRoute();
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new SearchAlongRouteFunctionalExample();
    }

    void displayRoute() {
        viewModel.showRoutingInProgressDialog();
        showRoute(getRouteQuery());
        tomtomMap.clearRoute();
    }

    protected RouteQuery getRouteQuery() {
        return RouteQueryFactory.createRouteForAlongRouteSearch(new AmsterdamToHaarlemRouteConfig());
    }

    public void performSearch(String term) {

        fragment.disableOptionsView();

        if (routesMap.isEmpty()) {
            Timber.d("performSearch(): no routes available for term " + term);
            return;
        }

        FullRoute route = (FullRoute) routesMap.values().toArray()[0];

        searchRequester.performSearch(term, route);
    }
}
