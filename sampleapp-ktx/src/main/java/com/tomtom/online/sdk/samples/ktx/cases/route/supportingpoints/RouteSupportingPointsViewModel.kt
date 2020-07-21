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

package com.tomtom.online.sdk.samples.ktx.cases.route.supportingpoints

import android.app.Application
import com.google.common.collect.ImmutableList
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.routing.route.RouteCalculationDescriptor
import com.tomtom.online.sdk.routing.route.RouteDescriptor
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.CondeixaToCoimbraRouteConfig

class RouteSupportingPointsViewModel(application: Application) : RouteViewModel(application) {

    fun planFastestRoute() {
        planRoute(ZERO_METERS)
    }

    fun planShortestRoute() {
        planRoute(TEN_KILOMETERS)
    }

    private fun planRoute(minDeviationDistance: Int) {
        val routeSpecification = prepareSpecification(minDeviationDistance)
        planRoute(routeSpecification)
    }

    private fun prepareSpecification(minDeviationDistance: Int): RouteSpecification {
        val origin = CondeixaToCoimbraRouteConfig().origin
        val destination = CondeixaToCoimbraRouteConfig().destination
        //tag::doc_route_supporting_points[]
        val routeDescriptor = RouteDescriptor.Builder()
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .maxAlternatives(MAX_ALTERNATIVES)
            .minDeviationTime(MIN_DEVIATION_TIME)
            .supportingPoints(SUPPORTING_POINTS)
            .minDeviationDistance(minDeviationDistance)
            .build()

        val routeSpecification = RouteSpecification.Builder(origin, destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        //end::doc_route_supporting_points[]
        return routeSpecification
    }

    private companion object {
        private const val ZERO_METERS = 0
        private const val TEN_KILOMETERS = 10000 //in meters
        private const val MAX_ALTERNATIVES = 1
        private const val MIN_DEVIATION_TIME = 0
        private val SUPPORTING_POINTS = ImmutableList.of(
            LatLng(40.10995732392718, -8.501433134078981),
            LatLng(40.11115121590874, -8.500000834465029),
            LatLng(40.11089684892725, -8.497683405876161),
            LatLng(40.11192251642396, -8.498423695564272),
            LatLng(40.209408, -8.423741)
        )
    }
}
