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

package com.tomtom.online.sdk.samples.ktx.cases.route.modes

import android.app.Application
import com.tomtom.online.sdk.routing.route.RouteCalculationDescriptor
import com.tomtom.online.sdk.routing.route.RouteDescriptor
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.routing.route.description.TravelMode
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToRotterdamRouteConfig
import com.tomtom.online.sdk.samples.ktx.utils.routes.RouteConfigExample
import com.tomtom.online.sdk.samples.ktx.utils.routes.WestHaarlemToHaarlemKleverPark

class RouteTravelModesViewModel(application: Application) : RouteViewModel(application) {

    private val WEST_HAARLEM_TO_HAARLEM_KLEVERPARK_ROUTE = WestHaarlemToHaarlemKleverPark()
    private val AMSTERDAM_TO_ROTTERDAM_ROUTE = AmsterdamToRotterdamRouteConfig()

    var isDotted: Boolean = false
        private set

    fun planRouteForCar() {
        isDotted = false
        planRoute(TravelMode.CAR)
    }

    fun planRouteForTruck() {
        isDotted = false
        planRoute(TravelMode.TRUCK)
    }

    fun planRouteForPedestrian() {
        isDotted = true
        planRoute(TravelMode.PEDESTRIAN, WEST_HAARLEM_TO_HAARLEM_KLEVERPARK_ROUTE)
    }

    private fun planRoute(travelMode: TravelMode, routeConfig: RouteConfigExample = AMSTERDAM_TO_ROTTERDAM_ROUTE) {
        val routeSpecification = prepareRouteSpecification(travelMode, routeConfig)
        planRoute(routeSpecification)
    }

    private fun prepareRouteSpecification(travelMode: TravelMode, routeConfig: RouteConfigExample): RouteSpecification {
        val origin = routeConfig.origin
        val destination = routeConfig.destination
        //tag::doc_route_travel_mode[]
        val routeDescriptor = RouteDescriptor.Builder()
            .travelMode(travelMode)
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .build()

        return RouteSpecification.Builder(origin, destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        //end::doc_route_travel_mode[]
    }
}
