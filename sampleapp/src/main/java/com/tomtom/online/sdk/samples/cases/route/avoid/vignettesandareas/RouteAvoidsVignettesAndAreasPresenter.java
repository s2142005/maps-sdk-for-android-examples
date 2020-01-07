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
package com.tomtom.online.sdk.samples.cases.route.avoid.vignettesandareas;

import android.graphics.Color;

import com.tomtom.online.sdk.common.location.BoundingBox;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.PolylineBuilder;
import com.tomtom.online.sdk.map.RouteStyleBuilder;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.MultiRoutesPlannerPresenter;
import com.tomtom.online.sdk.samples.cases.MultiRoutesQueryAdapter;
import com.tomtom.online.sdk.samples.cases.MultiRoutesRoutingUiListener;
import com.tomtom.online.sdk.samples.cases.route.RouteQueryFactory;
import com.tomtom.online.sdk.samples.routes.CzechRepublicToRomaniaRouteConfig;
import com.tomtom.online.sdk.samples.routes.RouteConfigExample;

import java.util.ArrayList;
import java.util.List;

public class RouteAvoidsVignettesAndAreasPresenter extends MultiRoutesPlannerPresenter {

    private final LatLng ARAD_TOP_LEFT_NEIGHBORHOOD = new LatLng(46.241223, 21.012896);
    private final LatLng ARAD_BOTTOM_RIGHT_NEIGHBORHOOD = new LatLng(45.861624, 21.506465);
    private final LatLng ARAD_BOTTOM_LEFT_NEIGHBORHOOD = new LatLng(45.861624, 21.012896);
    private final LatLng ARAD_TOP_RIGHT_NEIGHBORHOOD = new LatLng(46.241223, 21.506465);
    private final LatLng BUDAPEST_LOCATION = new LatLng(47.498733, 19.072646);
    private final RouteConfigExample routeConfig;

    RouteAvoidsVignettesAndAreasPresenter(MultiRoutesRoutingUiListener viewModel) {
        super(viewModel);
        routeConfig = new CzechRepublicToRomaniaRouteConfig();
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new RouteAvoidsVignettesAndAreasFunctionalExample();
    }

    private void setupRouteDisplay() {
        tomtomMap.clear();
        viewModel.showRoutingInProgressDialog();
    }

    public void startRoutingNoAvoids() {
        setupRouteDisplay();

        RouteQuery baseRouteQuery = RouteQueryFactory.createBaseRouteForAvoidsVignettesAndAreas(routeConfig);

        MultiRoutesQueryAdapter baseQueryAdapter = new MultiRoutesQueryAdapter(baseRouteQuery);
        baseQueryAdapter.setRouteTag(view.getString(R.string.label_no_avoids));
        baseQueryAdapter.setPrimary(true);

        showRoutes(baseQueryAdapter);
    }

    @Override
    public void centerOnDefaultLocation() {
        tomtomMap.centerOn(CameraPosition.builder(BUDAPEST_LOCATION)
                .bearing(MapConstants.ORIENTATION_NORTH)
                .zoom(DEFAULT_ZOOM_FOR_EXAMPLE)
                .build());
    }

    public void startRoutingAvoidVignettes() {
        setupRouteDisplay();

        //tag::doc_route_avoid_vignettes[]
        List<String> avoidVignettesList = new ArrayList<>();
        avoidVignettesList.add("HUN");
        avoidVignettesList.add("CZE");
        avoidVignettesList.add("SVK");

        RouteQuery routeQuery = RouteQueryFactory.createRouteForAvoidsVignettesAndAreas(avoidVignettesList, routeConfig);
        //end::doc_route_avoid_vignettes[]
        RouteQuery baseRouteQuery = RouteQueryFactory.createBaseRouteForAvoidsVignettesAndAreas(routeConfig);

        MultiRoutesQueryAdapter baseQueryAdapter = new MultiRoutesQueryAdapter(baseRouteQuery);
        baseQueryAdapter.setRouteTag(view.getString(R.string.label_no_avoids));

        MultiRoutesQueryAdapter withAvoidingVignettesQueryAdapter = new MultiRoutesQueryAdapter(routeQuery);
        withAvoidingVignettesQueryAdapter.setRouteStyle(RouteStyleBuilder.create().withFillColor(Color.MAGENTA).build());
        withAvoidingVignettesQueryAdapter.setRouteTag(view.getString(R.string.label_with_avoiding_vignettes));
        withAvoidingVignettesQueryAdapter.setPrimary(true);


        showRoutes(baseQueryAdapter, withAvoidingVignettesQueryAdapter);
    }

    public void startRoutingAvoidArea() {
        setupRouteDisplay();

        tomtomMap.getOverlaySettings().addOverlay(PolylineBuilder.create()
                .coordinate(ARAD_TOP_LEFT_NEIGHBORHOOD)
                .coordinate(ARAD_BOTTOM_LEFT_NEIGHBORHOOD)
                .coordinate(ARAD_BOTTOM_RIGHT_NEIGHBORHOOD)
                .coordinate(ARAD_TOP_RIGHT_NEIGHBORHOOD)
                .coordinate(ARAD_TOP_LEFT_NEIGHBORHOOD)
                .color(Color.BLUE)
                .build());

        //tag::doc_route_avoid_area[]
        BoundingBox boundingBox = new BoundingBox(ARAD_TOP_LEFT_NEIGHBORHOOD, ARAD_BOTTOM_RIGHT_NEIGHBORHOOD);

        RouteQuery routeQuery = RouteQueryFactory.createRouteForAvoidsArea(boundingBox, routeConfig);
        //end::doc_route_avoid_area[]

        RouteQuery baseRouteQuery = RouteQueryFactory.createBaseRouteForAvoidsVignettesAndAreas(routeConfig);

        MultiRoutesQueryAdapter baseQueryAdapter = new MultiRoutesQueryAdapter(baseRouteQuery);
        baseQueryAdapter.setRouteTag(view.getString(R.string.label_no_avoids));

        MultiRoutesQueryAdapter withAvoidingAreaQueryAdapter = new MultiRoutesQueryAdapter(routeQuery);
        withAvoidingAreaQueryAdapter.setRouteStyle(RouteStyleBuilder.create().withFillColor(Color.GREEN).build());
        withAvoidingAreaQueryAdapter.setRouteTag(view.getString(R.string.label_with_avoiding_area));
        withAvoidingAreaQueryAdapter.setPrimary(true);

        showRoutes(baseQueryAdapter, withAvoidingAreaQueryAdapter);
    }

}
