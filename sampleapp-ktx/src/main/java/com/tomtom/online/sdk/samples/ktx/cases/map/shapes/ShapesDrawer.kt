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

package com.tomtom.online.sdk.samples.ktx.cases.map.shapes

import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.CircleBuilder
import com.tomtom.online.sdk.map.PolygonBuilder
import com.tomtom.online.sdk.map.PolylineBuilder
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.samples.ktx.utils.randomizer.ColorRandomizer.randomColor
import com.tomtom.online.sdk.samples.ktx.utils.randomizer.CoordinatesRandomizer.randomCoordinates

class ShapesDrawer(private val tomtomMap: TomtomMap) {

    fun createPolygon(position: LatLng) {
        val color = randomColor()
        val coordinates = randomCoordinates(position, DEGREES_FOR_POLYGON)

        //tag::doc_shape_polygon[]
        val polygon = PolygonBuilder.create()
                .coordinates(coordinates)
                .color(color)
                .build()

        tomtomMap.overlaySettings.addOverlay(polygon)
        //end::doc_shape_polygon[]
    }

    fun createPolyline(position: LatLng) {
        val color = randomColor()
        val coordinates = randomCoordinates(position, DEGREES_FOR_POLYLINE)

        //tag::doc_shape_polyline[]
        val polyline = PolylineBuilder.create()
                .coordinates(coordinates)
                .color(color)
                .build()

        tomtomMap.overlaySettings.addOverlay(polyline)
        //end::doc_shape_polyline[]
    }

    fun createCircle(position: LatLng) {
        val color = randomColor()

        //tag::doc_shape_circle[]
        val circle = CircleBuilder.create()
                .fill(true)
                .radius(CIRCLE_RADIUS_IN_METERS)
                .position(position)
                .color(color)
                .build()

        tomtomMap.overlaySettings.addOverlay(circle)
        //end::doc_shape_circle[]
    }

    companion object {
        private const val CIRCLE_RADIUS_IN_METERS = 5000.0
        private const val DEGREES_FOR_POLYGON = 360.0f
        private const val DEGREES_FOR_POLYLINE = 270.0f
    }
}