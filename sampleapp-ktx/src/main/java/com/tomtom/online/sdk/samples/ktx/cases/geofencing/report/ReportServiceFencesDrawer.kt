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
package com.tomtom.online.sdk.samples.ktx.cases.geofencing.report

import android.graphics.Color
import com.google.common.collect.ImmutableList
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.CircleBuilder
import com.tomtom.online.sdk.map.PolygonBuilder
import com.tomtom.online.sdk.map.TomtomMap

class ReportServiceFencesDrawer(private val tomtomMap: TomtomMap) {

    fun drawPolygonFence() {
        val polygon = PolygonBuilder.create()
                .coordinates(AMSTERDAM_RECTANGLE_COORDINATES)
                .color(RECTANGLE_COLOR)
                .opacity(FENCE_OPACITY)
                .build()

        tomtomMap.overlaySettings.addOverlay(polygon)
    }

    fun drawCircularFence() {
        val circle = CircleBuilder.create()
                .position(CIRCLE_CENTER)
                .radius(CIRCLE_RADIUS)
                .color(CIRCLE_COLOR)
                .opacity(FENCE_OPACITY)
                .fill(true)
                .build()

        tomtomMap.overlaySettings.addOverlay(circle)
    }

    companion object {
        //Circle over Amsterdam center
        private val CIRCLE_CENTER = LatLng(52.372144, 4.899115)
        private const val CIRCLE_RADIUS = 1300.0 //in meters
        private const val CIRCLE_COLOR = Color.GREEN

        //Rectangle over Amsterdam De Plantage
        private val RECTANGLE_BOTTOM_RIGHT = LatLng(52.367172, 4.926724)
        private val RECTANGLE_BOTTOM_LEFT = LatLng(52.366650, 4.906364)
        private val RECTANGLE_TOP_RIGHT = LatLng(52.371166, 4.913136)
        private val RECTANGLE_TOP_LEFT = LatLng(52.363494, 4.916473)
        private val AMSTERDAM_RECTANGLE_COORDINATES = ImmutableList.of(
                RECTANGLE_BOTTOM_LEFT,
                RECTANGLE_TOP_RIGHT,
                RECTANGLE_BOTTOM_RIGHT,
                RECTANGLE_TOP_LEFT
        )
        private const val RECTANGLE_COLOR = Color.RED

        //Fence color opacity
        private const val FENCE_OPACITY = 0.5f
    }

}