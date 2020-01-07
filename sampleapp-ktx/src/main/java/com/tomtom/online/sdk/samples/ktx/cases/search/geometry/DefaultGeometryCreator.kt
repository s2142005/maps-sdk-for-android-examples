/*
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.search.geometry

import android.graphics.Color
import com.google.common.collect.ImmutableList
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.Circle
import com.tomtom.online.sdk.map.CircleBuilder
import com.tomtom.online.sdk.map.Polygon
import com.tomtom.online.sdk.map.PolygonBuilder
import com.tomtom.online.sdk.search.data.geometry.query.CircleGeometry
import com.tomtom.online.sdk.search.data.geometry.query.PolygonGeometry

object DefaultGeometryCreator {

    private val POLYGON_POINTS: List<LatLng> = ImmutableList.of(
            LatLng(52.37874, 4.90482),
            LatLng(52.37664, 4.92559),
            LatLng(52.37497, 4.94877),
            LatLng(52.36805, 4.97246),
            LatLng(52.34918, 4.95993),
            LatLng(52.34016, 4.95169),
            LatLng(52.32894, 4.91392),
            LatLng(52.34048, 4.88611),
            LatLng(52.33953, 4.84388),
            LatLng(52.37067, 4.8432),
            LatLng(52.38492, 4.84663),
            LatLng(52.40011, 4.85058),
            LatLng(52.38995, 4.89075)
    )

    private val CIRCLE_CENTER = LatLng(52.3639871, 4.7953232)
    private const val CIRCLE_RADIUS = 2000 // in meters

    private const val GEOMETRY_OVERLAY_COLOR = Color.RED
    private const val GEOMETRY_OVERLAY_OPACITY = 0.5f

    fun createDefaultPolygonOverlay(): Polygon {
        return PolygonBuilder.create()
                .coordinates(POLYGON_POINTS)
                .color(GEOMETRY_OVERLAY_COLOR)
                .opacity(GEOMETRY_OVERLAY_OPACITY)
                .build()
    }

    fun createDefaultCircleOverlay(): Circle {
        return CircleBuilder.create()
                .position(CIRCLE_CENTER)
                .radius(CIRCLE_RADIUS.toDouble())
                .color(GEOMETRY_OVERLAY_COLOR)
                .opacity(GEOMETRY_OVERLAY_OPACITY)
                .fill(true)
                .build()
    }

    fun createDefaultPolygonGeometry(): PolygonGeometry {
        return PolygonGeometry(POLYGON_POINTS)
    }

    fun createDefaultCircleGeometry(): CircleGeometry {
        return CircleGeometry(CIRCLE_CENTER, CIRCLE_RADIUS)
    }

}