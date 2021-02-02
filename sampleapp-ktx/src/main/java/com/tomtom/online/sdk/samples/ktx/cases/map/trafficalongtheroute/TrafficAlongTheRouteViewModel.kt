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

package com.tomtom.online.sdk.samples.ktx.cases.map.trafficalongtheroute

import android.app.Application
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.routing.route.RouteCalculationDescriptor
import com.tomtom.online.sdk.routing.route.RouteDescriptor
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.routing.route.calculation.SectionType
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations

class TrafficAlongTheRouteViewModel(application: Application) : RouteViewModel(application) {

    fun planRouteLondon() {
        val routeBuilder = prepareShortQuery()
        planRoute(routeBuilder)
    }

    fun planRouteLosAngeles() {
        val routeBuilder = prepareLongQuery()
        planRoute(routeBuilder)
    }

    private fun prepareShortQuery(): RouteSpecification {
        return prepareQuery(
            Locations.LONDON_CITY_AIRPORT,
            Locations.LONDON_HEATHROW,
            listOf(Locations.LONDON_TOWER, Locations.LONDON_NATIONAL_GALLERY)
        )
    }

    private fun prepareLongQuery(): RouteSpecification {
        return prepareQuery(
            Locations.LOS_ANGELES_LAX,
            Locations.ONTARIO_INTERNATIONAL_AIRPORT,
            listOf(Locations.LOS_ANGELES_DOWNTOWN, Locations.BEVERLY_HILLS, Locations.SANTA_MONICA)
        )
    }

    private fun prepareQuery(origin: LatLng, destination: LatLng, waypoints: List<LatLng>): RouteSpecification {
        val routeDescriptor = RouteDescriptor.Builder().considerTraffic(false).build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .waypoints(waypoints)
            .maxAlternatives(0)
            .sectionType(SectionType.TRAFFIC)
            .build()

        return RouteSpecification.Builder(origin, destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
    }
}
