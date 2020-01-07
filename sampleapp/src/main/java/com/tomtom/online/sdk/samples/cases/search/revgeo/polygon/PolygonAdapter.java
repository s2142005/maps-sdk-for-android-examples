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
package com.tomtom.online.sdk.samples.cases.search.revgeo.polygon;

import android.graphics.Color;

import com.tomtom.online.sdk.common.geojson.geometry.Polygon;
import com.tomtom.online.sdk.map.PolygonBuilder;

class PolygonAdapter {

    private static final float OVERLAYS_OPACITY = 0.5f;

    PolygonAdapter() {
    }

    com.tomtom.online.sdk.map.Polygon convertToMapPolygon(Polygon polygon) {
        return PolygonBuilder.create()
                .coordinates(polygon.getExteriorRing().getCoordinates())
                .color(Color.RED)
                .opacity(OVERLAYS_OPACITY)
                .build();
    }
}
