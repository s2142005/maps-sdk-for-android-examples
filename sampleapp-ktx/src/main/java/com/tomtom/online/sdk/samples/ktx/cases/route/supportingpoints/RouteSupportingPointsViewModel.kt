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
import com.tomtom.online.sdk.routing.data.RouteQuery
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder
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
        val routeQuery = prepareQuery(minDeviationDistance)
        planRoute(routeQuery)
    }

    private fun prepareQuery(minDeviationDistance: Int): RouteQuery {
        val origin = CondeixaToCoimbraRouteConfig().origin
        val destination = CondeixaToCoimbraRouteConfig().destination
        //tag::doc_route_supporting_points[]
        val routeQuery = RouteQueryBuilder.create(origin, destination)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withMinDeviationTime(MIN_DEVIATION_TIME)
                .withSupportingPoints(SUPPORTING_POINTS)
                .withMinDeviationDistance(minDeviationDistance)
                .withConsiderTraffic(false)
                .build()
        //end::doc_route_supporting_points[]
        return routeQuery
    }

    companion object {
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
