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

package com.tomtom.online.sdk.samples.ktx.cases.route.avoids

import android.app.Application
import com.tomtom.online.sdk.routing.data.*
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToOsloRouteConfig

class RouteAvoidViewModel(application: Application) : RouteViewModel(application) {

    fun avoidMotorways() {
        planRoute(Avoid.MOTORWAYS)
    }

    fun avoidTollRoads() {
        planRoute(Avoid.TOLL_ROADS)
    }

    fun avoidFerries() {
        planRoute(Avoid.FERRIES)
    }

    private fun planRoute(avoidType: Avoid) {
        val routeQuery = prepareRouteQuery(avoidType)
        planRoute(routeQuery)
    }

    private fun prepareRouteQuery(avoidType: Avoid): RouteQuery {
        val origin = AmsterdamToOsloRouteConfig().origin
        val destination = AmsterdamToOsloRouteConfig().destination
        //tag::doc_route_avoids[]
        val routeQuery = RouteQueryBuilder.create(origin, destination)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withReport(Report.NONE)
                .withInstructionsType(InstructionsType.NONE)
                .withAvoidType(avoidType)
                .withConsiderTraffic(false)
                .build()
        //end::doc_route_avoids[]
        return routeQuery
    }

    companion object {
        private const val MAX_ALTERNATIVES = 0
    }

}
