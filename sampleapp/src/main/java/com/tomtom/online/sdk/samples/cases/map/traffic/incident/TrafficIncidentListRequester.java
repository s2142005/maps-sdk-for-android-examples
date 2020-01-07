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
package com.tomtom.online.sdk.samples.cases.map.traffic.incident;

import android.content.Context;

import com.tomtom.online.sdk.common.location.BoundingBox;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.traffic.OnlineTrafficApi;
import com.tomtom.online.sdk.traffic.TrafficApi;
import com.tomtom.online.sdk.traffic.api.incident.details.IncidentDetailsResultListener;
import com.tomtom.online.sdk.traffic.incidents.query.IncidentDetailsQuery;
import com.tomtom.online.sdk.traffic.incidents.query.IncidentDetailsQueryBuilder;
import com.tomtom.online.sdk.traffic.incidents.query.IncidentStyle;

class TrafficIncidentListRequester {

    private static final BoundingBox LONDON_BOUNDING_BOX = new BoundingBox(new LatLng(51.544300, -0.176267), new LatLng(51.465582, -0.071777));
    private static final int DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 12;

    private final TrafficApi trafficApi;


    TrafficIncidentListRequester(Context context) {
        //tag::doc_traffic_init_api[]
        this.trafficApi = OnlineTrafficApi.create(context);
        //end::doc_traffic_init_api[]
    }

    void findIncidentDetails(IncidentDetailsResultListener incidentDetailsCallback) {
        trafficApi.findIncidentDetails(createSampleQuery(), incidentDetailsCallback);
    }

    private IncidentDetailsQuery createSampleQuery() {
        //tag::doc_traffic_query[]
        return IncidentDetailsQueryBuilder.create(IncidentStyle.S1, LONDON_BOUNDING_BOX, DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE, "-1")
                .withExpandCluster(true)
                .build();
        //end::doc_traffic_query[]
    }

}
