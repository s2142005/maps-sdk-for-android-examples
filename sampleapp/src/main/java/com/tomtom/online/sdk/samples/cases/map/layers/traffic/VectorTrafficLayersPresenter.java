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

import com.tomtom.online.sdk.map.DefaultOnTrafficFlowClickListener;
import com.tomtom.online.sdk.map.DefaultOnTrafficIncidentClickListener;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TrafficFlowBalloonViewAdapter;
import com.tomtom.online.sdk.map.TrafficFlowType;
import com.tomtom.online.sdk.map.TrafficFlowType.VectorTrafficFlowType;
import com.tomtom.online.sdk.map.TrafficIncidentsBalloonViewAdapter;
import com.tomtom.online.sdk.map.model.MapTilesType;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

public class VectorTrafficLayersPresenter extends BaseFunctionalExamplePresenter implements TrafficPresenter {

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        tomtomMap.getUiSettings().setMapTilesType(MapTilesType.VECTOR);
        //tag::doc_traffic_flow_vector_listener[]
        tomtomMap.getTrafficSettings().setOnTrafficFlowClickListener(new DefaultOnTrafficFlowClickListener(tomtomMap));
        //end::doc_traffic_flow_vector_listener[]

        //tag::doc_traffic_flow_balloon_view_adapter[]
        tomtomMap.getTrafficSettings().setTrafficFlowBalloonViewAdapter(new TrafficFlowBalloonViewAdapter.Default());
        //end::doc_traffic_flow_balloon_view_adapter[]

        //tag::doc_traffic_incident_vector_listener[]
        tomtomMap.getTrafficSettings().setOnTrafficIncidentsClickListener(new DefaultOnTrafficIncidentClickListener(tomtomMap));
        //end::doc_traffic_incident_vector_listener[]

        //tag::doc_traffic_incident_balloon_view_adapter[]
        tomtomMap.getTrafficSettings().setTrafficIncidentsBalloonViewAdapter(new TrafficIncidentsBalloonViewAdapter.Default());
        //end::doc_traffic_incident_balloon_view_adapter[]

        if (!view.isMapRestored()) {
            centerOnLondon();
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new VectorTrafficLayersFunctionalExample();
    }

    @Override
    public void cleanup() {
        hideTrafficInformation();
        tomtomMap.zoomTo(DEFAULT_ZOOM_LEVEL);
    }


    @Override
    public void showTrafficFlowTiles() {
        //tag::doc_legacy_traffic_vector_flow_on[]
        tomtomMap.getTrafficSettings().turnOnVectorTrafficFlowTiles();
        //end::doc_legacy_traffic_vector_flow_on[]
    }

    @SuppressWarnings("unused")
    private void turnOnTrafficFlowTiles() {
        //tag::doc_traffic_vector_flow_on[]
        tomtomMap.getTrafficSettings().turnOnTrafficFlowTiles();
        //end::doc_traffic_vector_flow_on[]
    }

    @SuppressWarnings("unused")
    public void exampleOfUsingTrafficStyle() {
        //tag::doc_legacy_traffic_flow_styles[]
        tomtomMap.getTrafficSettings().turnOnVectorTrafficFlowTiles(new TrafficFlowType.RelativeTrafficFlowStyle()); //default
        tomtomMap.getTrafficSettings().turnOnVectorTrafficFlowTiles(new TrafficFlowType.AbsoluteTrafficFlowStyle());
        tomtomMap.getTrafficSettings().turnOnVectorTrafficFlowTiles(new TrafficFlowType.RelativeDelayTrafficFlowStyle());
        //end::doc_legacy_traffic_flow_styles[]
        //tag::doc_legacy_get_style_info[]
        VectorTrafficFlowType style = tomtomMap.getTrafficSettings().getTrafficVectorFlowStyle();
        //end::doc_legacy_get_style_info[]
    }

    @Override
    public void hideTrafficInformation() {
        //tag::doc_traffic_off[]
        tomtomMap.getTrafficSettings().turnOffTraffic();
        tomtomMap.getTrafficSettings().turnOffTrafficFlowTiles();
        //end::doc_traffic_off[]
    }

    public void centerOnLondon() {
        tomtomMap.centerOn(
                Locations.LONDON_LOCATION.getLatitude(),
                Locations.LONDON_LOCATION.getLongitude(),
                DEFAULT_ZOOM_TRAFFIC_LEVEL,
                MapConstants.ORIENTATION_NORTH
        );
    }

    @Override
    public void showTrafficIncidents() {
        //tag::doc_legacy_traffic_vector_incidents_on[]
        tomtomMap.getTrafficSettings().turnOnVectorTrafficIncidents();
        //end::doc_legacy_traffic_vector_incidents_on[]
    }

    @SuppressWarnings("unused")
    private void turnOnTrafficIncidents() {
        //tag::doc_traffic_vector_incidents_on[]
        tomtomMap.getTrafficSettings().turnOnTrafficIncidents();
        //end::doc_traffic_vector_incidents_on[]
    }


    @Override
    public void showTrafficFlowAndIncidentsTiles() {
        tomtomMap.getTrafficSettings().turnOffTraffic();
        tomtomMap.getTrafficSettings().turnOnVectorTrafficIncidents();
        tomtomMap.getTrafficSettings().turnOnVectorTrafficFlowTiles();
    }
}
