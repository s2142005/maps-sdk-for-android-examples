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
package com.tomtom.online.sdk.samples.ktx.cases.driving.matching.route

import android.app.Application
import com.tomtom.online.sdk.routing.route.information.FullRoute
import com.tomtom.online.sdk.samples.ktx.cases.driving.matching.MatchingViewModel
import com.tomtom.online.sdk.samples.ktx.utils.driving.RouteSimulator
import com.tomtom.online.sdk.samples.ktx.utils.driving.interpolator.RandomizeInterpolator
import com.tomtom.online.sdk.samples.ktx.utils.routes.convertFromRoutesToLocations

class RouteMatchingViewModel(application: Application) : MatchingViewModel(application) {

    override lateinit var simulator: com.tomtom.online.sdk.samples.ktx.utils.driving.Simulator
    private var lastVisitedLocationIdx = INITIAL_VISITED_LOCATION_IDX

    fun createSimulator(routes: List<FullRoute>) {
        simulator = RouteSimulator(convertFromRoutesToLocations(routes), RandomizeInterpolator())
    }

    fun startSimulation() {
        if (lastVisitedLocationIdx == INITIAL_VISITED_LOCATION_IDX) {
            simulator.play(this)
        } else {
            simulator.resume(this, lastVisitedLocationIdx)
        }
    }
}

