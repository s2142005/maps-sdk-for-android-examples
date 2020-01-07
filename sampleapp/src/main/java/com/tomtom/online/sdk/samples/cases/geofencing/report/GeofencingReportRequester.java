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
package com.tomtom.online.sdk.samples.cases.geofencing.report;

import android.content.Context;
import android.location.Location;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.geofencing.GeofencingApi;
import com.tomtom.online.sdk.geofencing.ReportServiceResultListener;
import com.tomtom.online.sdk.geofencing.data.report.ReportServiceQuery;
import com.tomtom.online.sdk.geofencing.data.report.ReportServiceQueryBuilder;

import java.util.UUID;

public class GeofencingReportRequester {

    private static final float QUERY_RANGE = 5000; //in meters

    private GeofencingApi geofencingApi;
    private ReportServiceResultListener resultListener;

    GeofencingReportRequester(Context context, ReportServiceResultListener resultListener) {
        this.resultListener = resultListener;
        geofencingApi = createGeofencingApi(context);
    }

    void requestReport(LatLng position, UUID projectId) {
        ReportServiceQuery query = createQuery(position, projectId);
        //tag::doc_obtain_report[]
        geofencingApi.obtainReport(query, resultListener);
        //end::doc_obtain_report[]
    }

    private ReportServiceQuery createQuery(LatLng coordinates, UUID projectId) {
        Location location = coordinates.toLocation();
        //tag::doc_create_report_service_query[]
        return ReportServiceQueryBuilder.create(location)
                .withProject(projectId)
                .withRange(QUERY_RANGE)
                .build();
        //end::doc_create_report_service_query[]
    }

    private GeofencingApi createGeofencingApi(Context context) {
        //tag::doc_initialise_geofencing[]
        GeofencingApi geofencingApi = GeofencingApi.create(context);
        //end::doc_initialise_geofencing[]
        return geofencingApi;
    }
}
