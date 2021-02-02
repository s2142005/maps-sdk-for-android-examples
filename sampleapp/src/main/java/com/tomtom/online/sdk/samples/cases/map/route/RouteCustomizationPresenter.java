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
package com.tomtom.online.sdk.samples.cases.map.route;

import android.graphics.Color;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.RouteStyle;
import com.tomtom.online.sdk.map.RouteStyleBuilder;
import com.tomtom.online.sdk.routing.RoutingException;
import com.tomtom.online.sdk.routing.route.RouteCalculationDescriptor;
import com.tomtom.online.sdk.routing.route.RouteCallback;
import com.tomtom.online.sdk.routing.route.RouteDescriptor;
import com.tomtom.online.sdk.routing.route.RoutePlan;
import com.tomtom.online.sdk.routing.route.RouteSpecification;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.routes.AmsterdamToRotterdamRouteConfig;

public class RouteCustomizationPresenter extends RoutePlannerPresenter {

    private static final double DEFAULT_SCALE_FOR_SAMPLE_APP_ICONS = 4.5;

    public RouteCustomizationPresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new RouteCustomizationFunctionalExample();
    }

    void displayCustomRoute() {
        tomtomMap.clearRoute();
        viewModel.showRoutingInProgressDialog();
        showRoute(getRouteSpecification(), routeCallback);
    }

    void displayDefaultRoute() {
        tomtomMap.clearRoute();
        viewModel.showRoutingInProgressDialog();
        showRoute(getRouteSpecification());
    }

    @VisibleForTesting
    protected RouteSpecification getRouteSpecification() {
        AmsterdamToRotterdamRouteConfig routeConfig = new AmsterdamToRotterdamRouteConfig();
        RouteDescriptor routeDescriptor = new RouteDescriptor.Builder()
                .considerTraffic(false)
                .build();

        RouteCalculationDescriptor routeCalculationDescriptor = new RouteCalculationDescriptor.Builder()
                .routeDescription(routeDescriptor)
                .build();

        return new RouteSpecification.Builder(routeConfig.getOrigin(), routeConfig.getDestination())
                .routeCalculationDescriptor(routeCalculationDescriptor)
                .build();
    }

    @VisibleForTesting
    RouteStyle createCustomRouteStyle() {
        return
                //tag::doc_create_custom_route_style[]
                RouteStyleBuilder.create()
                        .withWidth(2.0)
                        .withFillColor(Color.BLACK)
                        .withOutlineColor(Color.RED)
                        .build();
        //end::doc_create_custom_route_style[]
    }

    Icon loadStartIcon() {
        return prepareRouteIconFromDrawable(R.drawable.ic_map_route_departure);
    }

    Icon loadEndIconForCustomStyle() {
        return prepareRouteIconFromDrawable(R.drawable.ic_map_fav);
    }

    private Icon prepareRouteIconFromDrawable(@DrawableRes int routeIcon) {
        return Icon.Factory.fromResources(view.getContext(), routeIcon);
    }

    private RouteCallback routeCallback = new RouteCallback() {
        @Override
        public void onSuccess(@NonNull RoutePlan routePlan) {
            displayRoutes(routePlan, createCustomRouteStyle(), loadStartIcon(), loadEndIconForCustomStyle());
        }

        @Override
        public void onError(@NonNull RoutingException error) {
            proceedWithError(error.getMessage());
        }
    };
}