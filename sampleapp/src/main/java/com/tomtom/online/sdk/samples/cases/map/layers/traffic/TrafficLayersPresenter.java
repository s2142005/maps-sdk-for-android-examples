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
package com.tomtom.online.sdk.samples.cases.map.layers.traffic;

import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.UiSettings;
import com.tomtom.online.sdk.map.model.MapTilesType;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

import static com.tomtom.online.sdk.map.TrafficFlowType.*;

public class TrafficLayersPresenter extends BaseFunctionalExamplePresenter implements TrafficPresenter {

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        tomtomMap.getUiSettings().setMapTilesType(MapTilesType.RASTER);
        if (!view.isMapRestored()) {
            centerOnLondon();
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new TrafficLayersFunctionalExample();
    }

    @Override
    public void cleanup() {
        hideTrafficInformation();
        tomtomMap.zoomTo(DEFAULT_ZOOM_LEVEL);
        tomtomMap.getUiSettings().setMapTilesType(MapTilesType.VECTOR);
    }

    public void showTrafficFlowTiles() {
        tomtomMap.getTrafficSettings().turnOffTraffic();
        //tag::doc_traffic_flow_on[]
        tomtomMap.getTrafficSettings().turnOnRasterTrafficFlowTiles();
        //end::doc_traffic_flow_on[]
    }

    @SuppressWarnings("unused")
    public void exampleOfUsingTrafficStyle(){
        //tag::doc_traffic_flow_styles[]
        UiSettings uiSettings = tomtomMap.getUiSettings();
        //default
        uiSettings.turnOnRasterTrafficFlowTiles(new RelativeTrafficFlowStyle());
        uiSettings.turnOnRasterTrafficFlowTiles(new AbsoluteTrafficFlowStyle());
        uiSettings.turnOnRasterTrafficFlowTiles(new RelativeDelayTrafficFlowStyle());
        uiSettings.turnOnRasterTrafficFlowTiles(new ReducedSensitivityTrafficFlowStyle());
        //end::doc_traffic_flow_styles[]
    }

    public void showTrafficIncidents() {
        tomtomMap.getTrafficSettings().turnOffTraffic();
        tomtomMap.getTrafficSettings().turnOnRasterTrafficIncidents();
    }

    public void showTrafficFlowAndIncidentsTiles() {
        //tag::doc_traffic_flow_inc_tiles_raster_on[]
        tomtomMap.getTrafficSettings().turnOnRasterTrafficIncidents();
        //end::doc_traffic_flow_inc_tiles_raster_on[]
        tomtomMap.getTrafficSettings().turnOnRasterTrafficFlowTiles();

    }

    public void hideTrafficInformation() {
        tomtomMap.getTrafficSettings().turnOffTraffic();
        //tag::doc_traffic_off[]
        tomtomMap.getTrafficSettings().turnOffTrafficIncidents();
        //end::doc_traffic_off[]
    }

    public void centerOnLondon() {
        tomtomMap.centerOn(Locations.LONDON_LOCATION.getLatitude(), Locations.LONDON_LOCATION.getLongitude(), DEFAULT_ZOOM_TRAFFIC_LEVEL, MapConstants.ORIENTATION_NORTH);
    }
}
