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

package com.tomtom.online.sdk.samples.ktx.cases.route.types

import android.app.Application
import com.tomtom.online.sdk.routing.data.*
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToRotterdamRouteConfig

class RouteTypesViewModel(application: Application) : RouteViewModel(application) {

    fun planFastestRoute() {
        planRoute(RouteType.FASTEST)
    }

    fun planShortestRoute() {
        planRoute(RouteType.SHORTEST)
    }

    fun planEcoRoute() {
        planRoute(RouteType.ECO)
    }

    private fun planRoute(routeType: RouteType) {
        val routeQuery = prepareRouteQuery(routeType)
        planRoute(routeQuery)
    }

    private fun prepareRouteQuery(routeType: RouteType): RouteQuery {
        val origin = AmsterdamToRotterdamRouteConfig().origin
        val destination = AmsterdamToRotterdamRouteConfig().destination
        //tag::doc_route_type[]
        val routeQuery = RouteQueryBuilder.create(origin, destination)
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withRouteType(routeType)
                .withConsiderTraffic(false)
                .build()
        //end::doc_route_type[]
        return routeQuery
    }

}
