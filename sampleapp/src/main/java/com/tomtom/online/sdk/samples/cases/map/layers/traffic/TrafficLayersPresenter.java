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

import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TrafficFlowType;
import com.tomtom.online.sdk.map.UiSettings;
import com.tomtom.online.sdk.map.model.MapTilesType;
import com.tomtom.online.sdk.map.style.layers.Layer;
import com.tomtom.online.sdk.map.style.layers.Visibility;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

import java.util.List;

import static com.tomtom.online.sdk.map.TrafficFlowType.*;

public class TrafficLayersPresenter extends BaseFunctionalExamplePresenter implements
        TrafficPresenter {

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
        //tag::doc_legacy_traffic_flow_on[]
        tomtomMap.getTrafficSettings().turnOnRasterTrafficFlowTiles();
        //end::doc_legacy_traffic_flow_on[]
    }

    @SuppressWarnings("unused")
    public void exampleOfUsingTrafficStyle() {
        //tag::doc_legacy_traffic_flow_styles[]
        tomtomMap.getTrafficSettings().turnOnRasterTrafficFlowTiles(new TrafficFlowType.RelativeTrafficFlowStyle()); //default
        tomtomMap.getTrafficSettings().turnOnRasterTrafficFlowTiles(new TrafficFlowType.AbsoluteTrafficFlowStyle());
        tomtomMap.getTrafficSettings().turnOnRasterTrafficFlowTiles(new TrafficFlowType.RelativeDelayTrafficFlowStyle());
        //end::doc_legacy_traffic_flow_styles[]

        //tag::doc_get_style_info[]
        RasterTrafficFlowType style = tomtomMap.getTrafficSettings().getTrafficRasterFlowStyle();
        //end::doc_get_style_info[]
    }

    public void showTrafficIncidents() {
        tomtomMap.getTrafficSettings().turnOffTraffic();
        tomtomMap.getTrafficSettings().turnOnRasterTrafficIncidents();
    }

    public void showTrafficFlowAndIncidentsTiles() {
        //tag::doc_legacy_traffic_flow_inc_tiles_raster_on[]
        tomtomMap.getTrafficSettings().turnOnRasterTrafficIncidents();
        //end::doc_legacy_traffic_flow_inc_tiles_raster_on[]
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

    @SuppressWarnings("unused")
    private void changeVisibilityOfTrafficFlow(Visibility visibility) {
        //tag::doc_traffic_flow_on[]
        List<Layer> layers = tomtomMap.getStyleSettings().findLayersById("tomtom-flow-raster-layer");
        FuncUtils.forEach(layers, layer -> layer.setVisibility(visibility));
        //end::doc_traffic_flow_on[]
    }

    @SuppressWarnings("unused")
    private void changeVisibilityOfTrafficIncidents(Visibility visibility) {
        //tag::doc_traffic_incidents_on[]
        List<Layer> layers = tomtomMap.getStyleSettings().findLayersById("tomtom-incidents-layer");
        FuncUtils.forEach(layers, layer -> layer.setVisibility(visibility));
        //end::doc_traffic_incidents_on[]
    }
}
