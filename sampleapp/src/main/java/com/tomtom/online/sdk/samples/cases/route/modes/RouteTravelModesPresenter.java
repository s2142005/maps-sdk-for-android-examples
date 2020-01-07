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
package com.tomtom.online.sdk.samples.cases.route.modes;

import android.graphics.Color;

import androidx.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;
import com.tomtom.online.sdk.map.DashDescriptor;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.RouteStyle;
import com.tomtom.online.sdk.map.RouteStyleBuilder;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.model.LineCapType;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.TravelMode;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteQueryFactory;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.routes.AmsterdamToRotterdamRouteConfig;
import com.tomtom.online.sdk.samples.routes.RouteConfigExample;
import com.tomtom.online.sdk.samples.routes.WestHaarlemToHaarlemKleverPark;

import java.util.List;

public class RouteTravelModesPresenter extends RoutePlannerPresenter {

    private static final double ROUTE_WIDTH = 0.3;
    private static final double DASH_LENGTH = 0.01;
    private static final double DASH_GAP = 2.0;
    private static final DashDescriptor ROUTE_DASH = new DashDescriptor(DASH_LENGTH, DASH_GAP);
    private static final List<DashDescriptor> DASH_LIST = ImmutableList.of(ROUTE_DASH);
    private static final int COLOR_LIGHT_BLUE = Color.rgb(26, 181, 196);
    private static final int COLOR_LIGHT_BLACK = Color.rgb(48, 48, 48);
    private static final RouteConfigExample AMSTERDAM_TO_ROTTERDAM_ROUTE = new AmsterdamToRotterdamRouteConfig();
    private static final RouteConfigExample WEST_HAARLEM_TO_HAARLEM_KLEVERPARK_ROUTE = new WestHaarlemToHaarlemKleverPark();

    private TravelMode travelMode;

    public RouteTravelModesPresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        if (travelMode != null) {
            displayRoute(travelMode);
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new RouteTravelModesFunctionalExample();
    }

    private void processRoute(TravelMode travelMode, RouteConfigExample routeConfig, RouteStyle routeStyle) {
        tomtomMap.clearRoute();
        viewModel.showRoutingInProgressDialog();
        showRoute(getRouteQuery(travelMode, routeConfig), routeStyle, loadIcon(R.drawable.ic_map_route_departure), loadIcon(R.drawable.ic_map_route_destination));
    }

    void displayRoute(TravelMode travelMode, RouteStyle routeStyle) {
        processRoute(travelMode, WEST_HAARLEM_TO_HAARLEM_KLEVERPARK_ROUTE, routeStyle);
    }

    void displayRoute(TravelMode travelMode) {
        processRoute(travelMode, AMSTERDAM_TO_ROTTERDAM_ROUTE, RouteStyle.DEFAULT_ROUTE_STYLE);
    }

    @VisibleForTesting
    protected RouteQuery getRouteQuery(TravelMode travelMode, RouteConfigExample routeConfig) {
        return RouteQueryFactory.createRouteTravelModesQuery(travelMode, routeConfig);
    }

    public void startTravelModeTruck() {
        displayRoute(TravelMode.TRUCK);
    }

    public void startTravelModePedestrian() {
        displayRoute(TravelMode.PEDESTRIAN, createDottedRouteStyle());
    }

    public void startTravelModeCar() {
        displayRoute(TravelMode.CAR);
    }

    public TravelMode getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }

    Icon loadIcon(int iconResId) {
        return Icon.Factory.fromResources(view.getContext(), iconResId, DEFAULT_ICON_SCALE);
    }

    private RouteStyle createDottedRouteStyle() {
        return RouteStyleBuilder.create()
                .withWidth(ROUTE_WIDTH)
                .withFillColor(COLOR_LIGHT_BLUE)
                .withOutlineColor(COLOR_LIGHT_BLACK)
                .withLineCapType(LineCapType.ROUND)
                .withDashList(DASH_LIST)
                .build();
    }

}
