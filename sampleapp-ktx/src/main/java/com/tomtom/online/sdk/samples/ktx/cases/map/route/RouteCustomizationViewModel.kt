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

package com.tomtom.online.sdk.samples.ktx.cases.map.route

import android.app.Application
import com.tomtom.online.sdk.routing.data.RouteQuery
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations

class RouteCustomizationViewModel(application: Application) : RouteViewModel(application) {

    var isCustom: Boolean = false

    fun planDefaultRoute() {
        isCustom = false

        val routeBuilder = prepareQuery()
        planRoute(routeBuilder)
    }

    fun planCustomRoute() {
        isCustom = true

        val routeBuilder = prepareQuery()
        planRoute(routeBuilder)
    }

    private fun prepareQuery(): RouteQuery {
        return RouteQueryBuilder.create(Locations.AMSTERDAM, Locations.ROTTERDAM)
                .withMaxAlternatives(0)
                .withConsiderTraffic(false)
                .build()
    }
}
