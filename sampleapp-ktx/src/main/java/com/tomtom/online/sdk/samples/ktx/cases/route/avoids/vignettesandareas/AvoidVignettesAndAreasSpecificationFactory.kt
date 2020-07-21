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
import com.tomtom.online.sdk.routing.route.RouteCalculationDescriptor
import com.tomtom.online.sdk.routing.route.RouteDescriptor
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.routing.route.calculation.InstructionsType
import com.tomtom.online.sdk.routing.route.diagnostic.ReportType
import com.tomtom.online.sdk.samples.ktx.utils.routes.CzechRepublicToRomaniaRouteConfig

class AvoidVignettesAndAreasSpecificationFactory {

    fun createBaseRouteForAvoidVignettesAndAreas(): RouteSpecification {
        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(createRouteDescriptor())
            .maxAlternatives(MAX_ALTERNATIVES)
            .reportType(ReportType.NONE)
            .instructionType(InstructionsType.NONE)
            .build()

        return RouteSpecification.Builder(routeConfig.origin, routeConfig.destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
    }

    fun createRouteForAvoidsVignettes(avoidVignettesList: List<String>): RouteSpecification {
        //tag::doc_route_avoid_vignettes[]
        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(createRouteDescriptor())
            .maxAlternatives(MAX_ALTERNATIVES)
            .reportType(ReportType.NONE)
            .instructionType(InstructionsType.NONE)
            .avoidVignettes(avoidVignettesList)
            .build()

        val routeSpecification = RouteSpecification.Builder(routeConfig.origin, routeConfig.destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        //end::doc_route_avoid_vignettes[]
        return routeSpecification
    }

    fun createRouteForAvoidsAreas(avoidArea: BoundingBox): RouteSpecification {
        //tag::doc_route_avoid_area[]
        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(createRouteDescriptor())
            .maxAlternatives(MAX_ALTERNATIVES)
            .reportType(ReportType.NONE)
            .instructionType(InstructionsType.NONE)
            .avoidAreas(listOf(avoidArea))
            .build()

        val routeSpecification = RouteSpecification.Builder(routeConfig.origin, routeConfig.destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        //end::doc_route_avoid_area[]
        return routeSpecification
    }

    private fun createRouteDescriptor(): RouteDescriptor {
        return RouteDescriptor.Builder()
            .considerTraffic(false)
            .build()
    }

    companion object {
        private const val MAX_ALTERNATIVES = 0
        private val routeConfig = CzechRepublicToRomaniaRouteConfig()
    }
}