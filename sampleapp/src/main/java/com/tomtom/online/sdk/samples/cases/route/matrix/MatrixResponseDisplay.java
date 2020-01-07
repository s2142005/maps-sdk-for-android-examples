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

import android.content.Context;

import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingResponse;
import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingResultKey;
import com.tomtom.online.sdk.samples.cases.route.matrix.data.AmsterdamPoi;

import java.util.Set;

public class MatrixResponseDisplay {

    private MatrixRouteMarkerDrawer markerDrawer;
    private MatrixResponseDisplayCallback resultsHandledCallback;

    MatrixResponseDisplay(Context context, MatrixResponseDisplayCallback resultsCallback) {
        this.resultsHandledCallback = resultsCallback;
        this.markerDrawer = new MatrixRouteMarkerDrawer(context);
    }

    void displayPoiOnMap(MatrixRoutingResponse routingResponse) {

        final Set<MatrixRoutingResultKey> matrixRoutingKeys = routingResponse.getResults().keySet();

        for (MatrixRoutingResultKey matrixRoutingResultKey : matrixRoutingKeys) {
            if (routingResponse.getResults().get(matrixRoutingResultKey) == null) {
                //Route to originPoi was not found.
                continue;
            }

            final AmsterdamPoi originPoi = AmsterdamPoi.parsePoiByLocation(matrixRoutingResultKey.getOrigin());
            final AmsterdamPoi destinationPoi = AmsterdamPoi.parsePoiByLocation(matrixRoutingResultKey.getDestination());

            drawPoi(routingResponse, matrixRoutingResultKey, originPoi, destinationPoi);
        }

    }

    private void drawPoi(MatrixRoutingResponse matrixRoutingResponse, MatrixRoutingResultKey matrixRoutingResultKey, AmsterdamPoi originPoi, AmsterdamPoi destinationPoi) {
        if (originPoi == null || destinationPoi == null) {
            //Route to poi was not found.
            return;
        }

        resultsHandledCallback.onMarkerForPoiCreated(markerDrawer.addMarkerForPoi(originPoi));
        resultsHandledCallback.onMarkerForPoiCreated(markerDrawer.addMarkerForPoi(destinationPoi));
        resultsHandledCallback.onPolylineForPoiCreated(new MatrixRoutePolylineDrawer(matrixRoutingResponse, matrixRoutingResultKey)
                .createPolylineForPoi(originPoi, destinationPoi));
    }
}
