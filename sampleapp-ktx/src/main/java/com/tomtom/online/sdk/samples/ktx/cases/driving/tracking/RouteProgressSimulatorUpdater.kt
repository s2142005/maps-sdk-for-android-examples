/*
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.driving.tracking

import android.location.Location
import android.os.Handler
import com.tomtom.online.sdk.map.Chevron
import com.tomtom.online.sdk.map.RouteSettings
import com.tomtom.online.sdk.map.route.RouteLayerStyle
import com.tomtom.online.sdk.samples.ktx.utils.driving.ChevronSimulatorUpdater

class RouteProgressSimulatorUpdater(
    chevron: Chevron,
    private val routeSettings: RouteSettings,
    private val routeId: Long
) : ChevronSimulatorUpdater(chevron) {

    private val handler = Handler()

    init {
        //tag::doc_create_route_progress_style[]
        val style = RouteLayerStyle.Builder()
            .color(DARK_BLUE)
            .build()
        //end::doc_create_route_progress_style[]
        //tag::doc_activate_route_progress[]
        routeSettings.activateProgressAlongRoute(routeId, style)
        //end::doc_activate_route_progress[]
    }

    override fun onNewRoutePointVisited(location: Location) {
        super.onNewRoutePointVisited(location)
        handler.postDelayed(
            {
                //tag::doc_update_route_progress[]
                routeSettings.updateProgressAlongRoute(routeId, location)
                //end::doc_update_route_progress[]
            },
            ONE_SECOND_IN_MILLIS
        )
    }

    companion object {
        private const val DARK_BLUE = 0x99006699.toInt()
        private const val ONE_SECOND_IN_MILLIS = 1000L
    }
}
