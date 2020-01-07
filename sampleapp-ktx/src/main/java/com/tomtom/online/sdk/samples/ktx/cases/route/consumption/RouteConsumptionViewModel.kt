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

package com.tomtom.online.sdk.samples.ktx.cases.route.consumption

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel

class RouteConsumptionViewModel(application: Application) : RouteViewModel(application) {

    lateinit var exampleType: ExampleType

    fun planRouteForCombustion() {
        exampleType = ExampleType.COMBUSTION

        val routeQuery = RouteConsumptionQueryFactory().prepareCombustionQuery()
        planRoute(routeQuery)
    }

    fun planRouteForElectric() {
        exampleType = ExampleType.ELECTRIC

        val routeQuery = RouteConsumptionQueryFactory().prepareElectricQuery()
        planRoute(routeQuery)
    }

    enum class ExampleType {
        ELECTRIC, COMBUSTION
    }

}
