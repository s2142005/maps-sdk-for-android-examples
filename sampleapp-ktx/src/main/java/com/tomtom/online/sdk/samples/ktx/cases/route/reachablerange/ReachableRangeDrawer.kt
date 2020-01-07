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

package com.tomtom.online.sdk.samples.ktx.cases.route.reachablerange

import android.graphics.Color
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.PolygonBuilder
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations

class ReachableRangeDrawer(private val tomtomMap: TomtomMap) {

    fun drawPolygonForReachableRange(coordinates: Array<LatLng>) {
        tomtomMap.overlaySettings.addOverlay(
                PolygonBuilder.create()
                        .coordinates(coordinates.asList())
                        .color(OVERLAYS_COLOR)
                        .opacity(OVERLAYS_OPACITY)
                        .build()
        )
    }

    fun drawCenterMarker(icon: Icon) {
        tomtomMap.markerSettings.addMarker(MarkerBuilder(Locations.AMSTERDAM_CENTER)
                .icon(icon))
    }

    companion object {
        private const val OVERLAYS_COLOR = Color.RED
        private const val OVERLAYS_OPACITY = 0.5f
    }

}