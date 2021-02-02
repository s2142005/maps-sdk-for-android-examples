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
package com.tomtom.online.sdk.samples.cases.route.matrix;

import android.graphics.Color;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.common.util.DateFormatter;
import com.tomtom.online.sdk.map.Polyline;
import com.tomtom.online.sdk.map.PolylineBuilder;
import com.tomtom.online.sdk.routing.matrix.MatrixRoutesPlan;
import com.tomtom.online.sdk.routing.matrix.route.MatrixRoute;
import com.tomtom.online.sdk.routing.matrix.route.MatrixRouteKey;
import com.tomtom.online.sdk.routing.route.description.Summary;
import com.tomtom.online.sdk.samples.cases.route.matrix.data.AmsterdamPoi;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

class MatrixRoutePolylineDrawer {

    private final List<LatLng> originDestinationLine = new ArrayList<>();
    private MatrixRoutesPlan matrixRoutesPlan;
    private MatrixRouteKey matrixRoutingKey;

    MatrixRoutePolylineDrawer(MatrixRoutesPlan matrixRoutesPlan, MatrixRouteKey matrixRoutingKey) {
        this.matrixRoutesPlan = matrixRoutesPlan;
        this.matrixRoutingKey = matrixRoutingKey;
    }

    Polyline createPolylineForPoi(AmsterdamPoi originPoi, AmsterdamPoi destinationPoi) {
        originDestinationLine.add(originPoi.getLocation());
        originDestinationLine.add(destinationPoi.getLocation());

        return PolylineBuilder.create()
                .coordinates(originDestinationLine)
                .color(determineColor(matrixRoutingKey, matrixRoutesPlan))
                .build();
    }

    private int determineColor(MatrixRouteKey matrixRoutingKey, MatrixRoutesPlan matrixRoutesPlan) {

        Summary summary = matrixRoutesPlan.getRoutes().get(matrixRoutingKey).getSummary();
        if (summary == null) {
            return Color.GRAY;
        }
        DateFormatter dateFormatter = new DateFormatter();

        DateTime currentRouteEta = dateFormatter.formatWithTimeZone(summary.getArrivalTime());
        DateTime minEta = currentRouteEta;

        for (MatrixRouteKey key : matrixRoutesPlan.getRoutes().keySet()) {
            if (key.getOrigin().equals(matrixRoutingKey.getOrigin())) {
                final MatrixRoute result = matrixRoutesPlan.getRoutes().get(key);
                if (result.getSummary() != null) {
                    final DateTime arrivalTime = dateFormatter.formatWithTimeZone(result.getSummary().getArrivalTime());
                    if (arrivalTime.isBefore(minEta)) {
                        minEta = arrivalTime;
                    }
                }
            }
        }
        return currentRouteEta == minEta ? Color.GREEN : Color.GRAY;
    }
}
