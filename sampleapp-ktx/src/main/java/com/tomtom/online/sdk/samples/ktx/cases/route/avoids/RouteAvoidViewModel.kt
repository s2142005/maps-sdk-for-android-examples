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
import com.tomtom.online.sdk.routing.route.RouteCalculationDescriptor
import com.tomtom.online.sdk.routing.route.RouteDescriptor
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.routing.route.calculation.InstructionsType
import com.tomtom.online.sdk.routing.route.description.AvoidType
import com.tomtom.online.sdk.routing.route.diagnostic.ReportType
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToOsloRouteConfig

class RouteAvoidViewModel(application: Application) : RouteViewModel(application) {

    fun avoidMotorways() {
        planRoute(AvoidType.MOTORWAYS)
    }

    fun avoidTollRoads() {
        planRoute(AvoidType.TOLL_ROADS)
    }

    fun avoidFerries() {
        planRoute(AvoidType.FERRIES)
    }

    private fun planRoute(avoidType: AvoidType) {
        val routeSpecification = prepareRouteSpecification(avoidType)
        planRoute(routeSpecification)
    }

    private fun prepareRouteSpecification(avoidType: AvoidType): RouteSpecification {
        val origin = AmsterdamToOsloRouteConfig().origin
        val destination = AmsterdamToOsloRouteConfig().destination
        //tag::doc_route_avoids[]
        val routeDescriptor = RouteDescriptor.Builder()
            .avoidType(listOf(avoidType))
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .maxAlternatives(MAX_ALTERNATIVES)
            .reportType(ReportType.NONE)
            .instructionType(InstructionsType.NONE)
            .build()

        val routeSpecification = RouteSpecification.Builder(origin, destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        //end::doc_route_avoids[]
        return routeSpecification
    }

    companion object {
        private const val MAX_ALTERNATIVES = 0
    }

}
