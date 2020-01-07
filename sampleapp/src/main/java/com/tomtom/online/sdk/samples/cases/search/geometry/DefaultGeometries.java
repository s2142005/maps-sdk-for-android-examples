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
package com.tomtom.online.sdk.samples.cases.search.geometry;

import android.graphics.Color;

import com.google.common.collect.ImmutableList;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.Circle;
import com.tomtom.online.sdk.map.CircleBuilder;
import com.tomtom.online.sdk.map.Polygon;
import com.tomtom.online.sdk.map.PolygonBuilder;

import java.util.List;

class DefaultGeometries {

    static final List<LatLng> POLYGON_POINTS = ImmutableList.of(
            new LatLng(52.37874, 4.90482),
            new LatLng(52.37664, 4.92559),
            new LatLng(52.37497, 4.94877),
            new LatLng(52.36805, 4.97246),
            new LatLng(52.34918, 4.95993),
            new LatLng(52.34016, 4.95169),
            new LatLng(52.32894, 4.91392),
            new LatLng(52.34048, 4.88611),
            new LatLng(52.33953, 4.84388),
            new LatLng(52.37067, 4.8432),
            new LatLng(52.38492, 4.84663),
            new LatLng(52.40011, 4.85058),
            new LatLng(52.38995, 4.89075)
    );

    static final LatLng CIRCLE_CENTER = new LatLng(52.3639871, 4.7953232);

    static final int CIRCLE_RADIUS = 2000;

    private static final int GEOMETRY_OVERLAY_COLOR = Color.rgb(255, 0, 0);

    private static final float GEOMETRY_OVERLAY_OPACITY = 0.5f;

    static Polygon createDefaultPolygonOverlay() {
        return PolygonBuilder.create()
                .coordinates(POLYGON_POINTS)
                .color(GEOMETRY_OVERLAY_COLOR)
                .opacity(GEOMETRY_OVERLAY_OPACITY)
                .build();
    }

    static Circle createDefaultCircleOverlay() {
        return CircleBuilder.create()
                .position(DefaultGeometries.CIRCLE_CENTER)
                .radius(DefaultGeometries.CIRCLE_RADIUS)
                .color(DefaultGeometries.GEOMETRY_OVERLAY_COLOR)
                .opacity(DefaultGeometries.GEOMETRY_OVERLAY_OPACITY)
                .fill(true)
                .build();
    }
}
