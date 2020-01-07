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
package com.tomtom.online.sdk.samples.cases.runtimestyle.zorder;

import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.RouteStyle;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.RoutingUiListener;
import com.tomtom.online.sdk.samples.cases.runtimestyle.zorder.displayers.GeoJsonLayerOperationDisplayer;
import com.tomtom.online.sdk.samples.cases.runtimestyle.zorder.displayers.ImageLayersOperationDisplayer;
import com.tomtom.online.sdk.samples.cases.runtimestyle.zorder.displayers.RoadsLayersOperationDisplayer;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

public class ZOrderPresenter extends RoutePlannerPresenter {


    public static double DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 7.5;

    private ImageLayersOperationDisplayer imageLayersOperationDisplayer;
    private GeoJsonLayerOperationDisplayer geojsonLayerOperationDisplayer;
    private RoadsLayersOperationDisplayer roadsLayersOperationDisplayer;

    public ZOrderPresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        initLayersOperationDelegates(map);
        if (!view.isMapRestored()) {
            imageLayersOperationDisplayer.addAllImages();
            planExampleRoute();
        } else {
            restoreLayersDelegates();
        }
    }

    private void restoreLayersDelegates() {
        imageLayersOperationDisplayer.restore();
        geojsonLayerOperationDisplayer.restore();
        roadsLayersOperationDisplayer.restore();
    }

    private void initLayersOperationDelegates(TomtomMap map) {
        imageLayersOperationDisplayer = new ImageLayersOperationDisplayer(getContext(), map);
        geojsonLayerOperationDisplayer = new GeoJsonLayerOperationDisplayer(map);
        roadsLayersOperationDisplayer = new RoadsLayersOperationDisplayer(map);
    }

    private void planExampleRoute() {
        tomtomMap.clearRoute();
        viewModel.showRoutingInProgressDialog();
        showRoute(RouteQueryBuilder.create(Locations.SAN_FRANCISCO, Locations.SANTA_CRUZ).build());
    }

    @Override
    protected void addMapRoute(RouteStyle routeStyle, Icon startIcon, Icon endIcon, FullRoute route) {
        // Do not add map routes
    }

    @Override
    protected void displayInfoAboutRoute(FullRoute routeResult) {
        geojsonLayerOperationDisplayer.createGeoJsonSource(routeResult);
        geojsonLayerOperationDisplayer.addGeoJsonLayer();
        viewModel.hideRoutingInProgressDialog();
        //Once we have route, it is added on top of other layers
        //Therefore, we need to move image layers on top as it used to be
        moveImagesLayersToFront();
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new ZOrderFunctionalExample();
    }

    @Override
    public void cleanup() {
        //Reload style so that we go back to the original order of layers
        tomtomMap.getUiSettings().setStyleUrl("asset://styles/mapssdk-default-style.json");
    }

    @Override
    public void centerOnDefaultLocation() {
        centerOnSanJose();
    }

    private void centerOnSanJose() {
        centerOn(Locations.SAN_JOSE_9RD, DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE);
    }

    void moveImagesLayersToFront() {
        imageLayersOperationDisplayer.moveLayersToFront();
    }

    void moveGeoJsonLayerToFront() {
        geojsonLayerOperationDisplayer.moveLayerToFront();
    }

    void moveRoadsLayerToFront() {
        roadsLayersOperationDisplayer.moveLayersToFront();
    }
}
