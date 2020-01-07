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

package com.tomtom.online.sdk.samples.ktx.cases.driving.tracking

import android.app.Application
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.cases.driving.DrivingViewModel
import com.tomtom.online.sdk.samples.ktx.utils.driving.ChevronSimulatorUpdater
import com.tomtom.online.sdk.samples.ktx.utils.driving.RouteSimulator
import com.tomtom.online.sdk.samples.ktx.utils.driving.interpolator.BasicInterpolator
import com.tomtom.online.sdk.samples.ktx.utils.routes.LodzCityCenterRouteConfig
import com.tomtom.online.sdk.samples.ktx.utils.routes.convertFromRoutesToLocations

class ChevronTrackingViewModel(application: Application) : DrivingViewModel(application) {

    private lateinit var simulator: com.tomtom.online.sdk.samples.ktx.utils.driving.Simulator
    private var lastVisitedLocation = 0

    fun startSimulation(chevronSimulatorUpdater: ChevronSimulatorUpdater) {
        simulator.play(chevronSimulatorUpdater)
    }

    fun stopSimulation() {
        lastVisitedLocation = simulator.stop()
    }

    fun restoreSimulation(chevronSimulatorUpdater: ChevronSimulatorUpdater) {
        simulator.resume(chevronSimulatorUpdater, lastVisitedLocation)
    }

    fun createSimulator(routes: List<FullRoute>) {
        simulator = RouteSimulator(convertFromRoutesToLocations(routes), BasicInterpolator())
    }

}

val DEFAULT_ROUTE = LodzCityCenterRouteConfig()