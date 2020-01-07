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

package com.tomtom.online.sdk.samples.ktx.cases.route.avoids.vignettesandareas

import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.routing.data.InstructionsType
import com.tomtom.online.sdk.routing.data.Report
import com.tomtom.online.sdk.routing.data.RouteQuery
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder
import com.tomtom.online.sdk.samples.ktx.utils.routes.CzechRepublicToRomaniaRouteConfig

class AvoidVignettesAndAreasQueryFactory {

    fun createBaseRouteForAvoidVignettesAndAreas(): RouteQuery {
        return RouteQueryBuilder.create(routeConfig.origin, routeConfig.destination)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withReport(Report.NONE)
                .withInstructionsType(InstructionsType.NONE)
                .withConsiderTraffic(false)
                .build()
    }

    fun createRouteForAvoidsVignettes(avoidVignettesList: List<String>): RouteQuery {
        //tag::doc_route_avoid_vignettes[]
        val routeQuery = RouteQueryBuilder.create(routeConfig.origin, routeConfig.destination)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withReport(Report.NONE)
                .withInstructionsType(InstructionsType.NONE)
                .withConsiderTraffic(false)
                .withAvoidVignettes(avoidVignettesList)
                .build()
        //end::doc_route_avoid_vignettes[]
        return routeQuery
    }

    fun createRouteForAvoidsAreas(avoidArea: BoundingBox): RouteQuery {
        //tag::doc_route_avoid_area[]
        val routeQuery = RouteQueryBuilder.create(routeConfig.origin, routeConfig.destination)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withReport(Report.NONE)
                .withInstructionsType(InstructionsType.NONE)
                .withConsiderTraffic(false)
                .withAvoidArea(avoidArea)
                .build()
        //end::doc_route_avoid_area[]
        return routeQuery
    }

    companion object {
        private const val MAX_ALTERNATIVES = 0
        private val routeConfig = CzechRepublicToRomaniaRouteConfig()
    }
}