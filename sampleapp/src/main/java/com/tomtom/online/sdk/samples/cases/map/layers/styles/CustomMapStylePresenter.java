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
package com.tomtom.online.sdk.samples.cases.map.layers.styles;

import com.tomtom.online.sdk.map.LayerSetConfiguration;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

public class CustomMapStylePresenter extends BaseFunctionalExamplePresenter {

    private static final String TRAFFIC_FLOW_SOURCE_ID = "tomtom-flow-vector-reduced-sensitivity";
    private static final String TRAFFIC_INCIDENTS_SOURCE_ID = "tomtom-incidents-vector";
    private static final String MAP_TILES_SOURCE_ID = "tomtom-raster-basic-main";

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        if (!view.isMapRestored()) {
            centerOn(Locations.AMSTERDAM_LOCATION);
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new CustomMapStyleFunctionalExample();
    }

    @Override
    public void cleanup() {
        showBaseStyle();
    }

    public void showNightStyle() {
        //tag::doc_set_style[]
        tomtomMap.getUiSettings().setStyleUrl("asset://styles/night.json");
        tomtomMap.getUiSettings().getLogoView().applyInvertedLogo();
        //end::doc_set_style[]
    }

    public void showBaseStyle() {
        //tag::set_default_style[]
        tomtomMap.getUiSettings().loadDefaultStyle();
        tomtomMap.getUiSettings().getLogoView().applyDefaultLogo();
        //end::set_default_style[]
    }

    @SuppressWarnings("unused")
    public void showCustomStyle() {
        //tag::doc_set_custom_style[]
        tomtomMap.getUiSettings().setStyleUrl(
                "asset://styles/custom-traffic.json",
                new LayerSetConfiguration.Builder()
                        .mapTilesConfiguration(MAP_TILES_SOURCE_ID)
                        .trafficFlowTilesConfiguration(TRAFFIC_FLOW_SOURCE_ID)
                        .trafficIncidentsTilesConfiguration(TRAFFIC_INCIDENTS_SOURCE_ID)
                        .build()
        );
        //end::doc_set_custom_style[]
    }
}
