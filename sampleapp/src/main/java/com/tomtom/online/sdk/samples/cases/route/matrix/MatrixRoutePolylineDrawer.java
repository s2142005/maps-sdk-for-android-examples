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
package com.tomtom.online.sdk.samples.cases.route.matrix;

import android.graphics.Color;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.Polyline;
import com.tomtom.online.sdk.map.PolylineBuilder;
import com.tomtom.online.sdk.routing.data.Summary;
import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingResponse;
import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingResult;
import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingResultKey;
import com.tomtom.online.sdk.samples.cases.route.matrix.data.AmsterdamPoi;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

class MatrixRoutePolylineDrawer {

    private final List<LatLng> originDestinationLine = new ArrayList<>();
    private MatrixRoutingResponse matrixRoutingResponse;
    private MatrixRoutingResultKey matrixRoutingResultKey;

    MatrixRoutePolylineDrawer(MatrixRoutingResponse matrixRoutingResponse, MatrixRoutingResultKey matrixRoutingResultKey) {
        this.matrixRoutingResponse = matrixRoutingResponse;
        this.matrixRoutingResultKey = matrixRoutingResultKey;
    }


    Polyline createPolylineForPoi(AmsterdamPoi originPoi, AmsterdamPoi destinationPoi) {
        originDestinationLine.add(originPoi.getLocation());
        originDestinationLine.add(destinationPoi.getLocation());

        return PolylineBuilder.create()
                .coordinates(originDestinationLine)
                .color(determineColor(matrixRoutingResultKey, matrixRoutingResponse))
                .build();
    }

    private int determineColor(MatrixRoutingResultKey matrixRoutingResultKey, MatrixRoutingResponse matrixRoutingResponse) {

        Summary summary = matrixRoutingResponse.getResults().get(matrixRoutingResultKey).getSummary();
        if (summary == null) {
            return Color.GRAY;
        }
        DateTime currentRouteEta = summary.getArrivalTimeWithZone();
        DateTime minEta = currentRouteEta;

        for (MatrixRoutingResultKey key : matrixRoutingResponse.getResults().keySet()) {
            if (key.getOrigin().equals(matrixRoutingResultKey.getOrigin())) {
                final MatrixRoutingResult result = matrixRoutingResponse.getResults().get(key);
                if (result.getSummary() != null) {
                    final DateTime arrivalTime = result.getSummary().getArrivalTimeWithZone();
                    if (arrivalTime.isBefore(minEta)) {
                        minEta = arrivalTime;
                    }
                }
            }
        }
        return currentRouteEta == minEta ? Color.GREEN : Color.GRAY;
    }
}
