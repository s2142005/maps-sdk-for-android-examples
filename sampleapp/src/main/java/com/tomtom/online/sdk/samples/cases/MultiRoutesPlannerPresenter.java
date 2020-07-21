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
package com.tomtom.online.sdk.samples.cases;

import android.annotation.SuppressLint;

import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.map.RouteBuilder;
import com.tomtom.online.sdk.map.RouteStyle;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.route.RoutePlan;
import com.tomtom.online.sdk.routing.route.information.FullRoute;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.formatter.DistanceFormatter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.tomtom.online.sdk.common.func.FuncUtils.*;

public abstract class MultiRoutesPlannerPresenter extends RoutePlannerPresenter {

    private MultiRoutesSpecificationAdapter[] multiRoutesSpecificationAdapters;

    public MultiRoutesPlannerPresenter(MultiRoutesRoutingUiListener viewModel) {
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

    @SuppressLint("CheckResult")
    public void showRoutes(final MultiRoutesSpecificationAdapter... multiRoutesSpecificationAdapters) {
        this.multiRoutesSpecificationAdapters = multiRoutesSpecificationAdapters;
        getRoutesMap().clear();
        displayRoutes(multiRoutesSpecificationAdapters);
    }

    private void displayRoutes(MultiRoutesSpecificationAdapter... multiRoutesSpecificationAdapters) {
        List<RoutePlan> routes = new ArrayList<>();
        Observable.fromArray(multiRoutesSpecificationAdapters)
                .map(adapter -> getRoutePlannerAPI().planRoute(adapter.getRouteSpecification()))
                .subscribeOn(getWorkingScheduler())
                .observeOn(getResultScheduler())
                .subscribe(routeResult -> {
                            if (routeResult.isSuccess()) {
                                routes.add(routeResult.value());
                            }
                        }, (error -> proceedWithError(error.getMessage())),
                        (() -> {
                            displayFullRoutesWithMultiRoutesAdapter(routes, multiRoutesSpecificationAdapters);
                            selectPrimaryRoute();
                            finishRouting();
                        }));
    }

    @VisibleForTesting
    protected RoutingApi getRoutePlannerAPI() {
        return routingApi;
    }

    private void finishRouting() {
        viewModel.hideRoutingInProgressDialog();
        tomtomMap.displayRoutesOverview();
    }

    private void selectPrimaryRoute() {
        forEachIndexed(tomtomMap.getRouteSettings().getRoutes(), (route, index) -> {
            if (route.getTag() instanceof String && multiRoutesSpecificationAdapters[index].isPrimary()) {
                selectRoute(route);
            }
        });
    }

    private void displayFullRoutesWithMultiRoutesAdapter(List<RoutePlan> routePlans, final MultiRoutesSpecificationAdapter[] multiRoutesQueryAdapters) {
        forEachIndexed(routePlans, (routePlan, index) -> displayFullRoutes(routePlan, multiRoutesQueryAdapters[index]));
    }

    @Override
    protected void processAddedRoutes(RouteStyle routeStyle, List<FullRoute> routes) {
        // Omitted intentionally due to custom way to process the added routes in case if we are adding more than one.
    }

    @VisibleForTesting
    public void displayFullRoutes(RoutePlan routePlan, MultiRoutesSpecificationAdapter multiRoutesQueryAdapter) {
        List<FullRoute> routes = routePlan.getRoutes();

        for (FullRoute route : routes) {
            //tag::doc_display_route[]
            RouteBuilder routeBuilder = new RouteBuilder(route.getCoordinates())
                    .tag(multiRoutesQueryAdapter.getRouteTag())
                    .endIcon(defaultEndIcon)
                    .startIcon(defaultStartIcon)
                    .style(multiRoutesQueryAdapter.getRouteStyle());
            final Route mapRoute = tomtomMap.addRoute(routeBuilder);
            //end::doc_display_route[]

            getRoutesMap().put(mapRoute.getId(), route);
        }
    }

    private TomtomMapCallback.OnRouteClickListener onRouteClickListener = this::selectRoute;

    private void selectRoute(Route route) {
        tomtomMap.bringRouteToFront(route.getId());
        FullRoute fullRoute = getRoutesMap().get(route.getId());
        updateRouteStatusBar(route, fullRoute);
    }

    private void updateRouteStatusBar(Route route, FullRoute fullRoute) {
        if (viewModel instanceof MultiRoutesRoutingUiListener && route.getTag() instanceof String) {
            ((MultiRoutesRoutingUiListener) viewModel).updateTextOnCurrentRouteBar((String) route.getTag(), DistanceFormatter.format(fullRoute.getSummary().getLengthInMeters()));
        }
    }

}
