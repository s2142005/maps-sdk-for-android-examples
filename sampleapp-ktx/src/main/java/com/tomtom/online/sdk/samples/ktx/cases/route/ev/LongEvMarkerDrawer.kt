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

package com.tomtom.online.sdk.samples.ktx.cases.route.ev

import android.content.Context
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.route.information.FullRoute
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.sdk.examples.R

class LongEvMarkerDrawer(private val context: Context, private val tomtomMap: TomtomMap) {

    fun createMarkersForChargingWaypoints(chargingStations: List<ChargingWaypoint>) {
        chargingStations.forEach {
            val balloon =
                LongEvMarkerBalloon("Wait time: ${it.chargingTime} minutes")
            tomtomMap.markerSettings.markerBalloonViewAdapter = LongEvBalloonViewAdapter()
            tomtomMap.markerSettings.addMarker(
                MarkerBuilder(it.coordinates).markerBalloon(balloon)
                    .icon(Icon.Factory.fromResources(context, R.drawable.ic_pin_ev_station))
            )
        }
    }
}