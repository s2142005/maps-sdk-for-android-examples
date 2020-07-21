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
package com.tomtom.online.sdk.samples.ktx.cases.route.waypoints

import android.app.Application
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.routing.route.RouteCalculationDescriptor
import com.tomtom.online.sdk.routing.route.RouteDescriptor
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToBerlinRouteConfig

class RouteWaypointsViewModel(application: Application) : RouteViewModel(application) {

    lateinit var waypoints: List<LatLng>

    fun planInitialOrderRoute() {
        assignWaypoints()
        planRouteWithWaypoints()
    }

    fun planNoWaypointsRoute() {
        assignEmptyArray()
        planRouteWithWaypoints()
    }

    private fun planRouteWithWaypoints() {
        val routeSpecification = prepareRouteSpecification(waypoints)
        planRoute(routeSpecification)
    }

    private fun prepareRouteSpecification(waypoints: List<LatLng>): RouteSpecification {
        val origin = AmsterdamToBerlinRouteConfig().origin
        val destination = AmsterdamToBerlinRouteConfig().destination
        //tag::doc_route_waypoints[]
        val routeDescriptor = RouteDescriptor.Builder()
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .waypoints(waypoints)
            .build()

        val routeSpecification = RouteSpecification.Builder(origin, destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        //end::doc_route_waypoints[]
        return routeSpecification
    }

    private fun assignWaypoints() {
        //tag::doc_route_waypoints_array[]
        waypoints = listOf(WAYPOINT_HAMBURG, WAYPOINT_ZURICH, WAYPOINT_BRUSSELS)
        //end::doc_route_waypoints_array[]
    }

    private fun assignEmptyArray() {
        waypoints = listOf()
    }

    companion object {
        private val WAYPOINT_BRUSSELS = LatLng(50.854751, 4.305694)
        private val WAYPOINT_HAMBURG = LatLng(53.560096, 9.788492)
        private val WAYPOINT_ZURICH = LatLng(47.385150, 8.476178)
    }
}
