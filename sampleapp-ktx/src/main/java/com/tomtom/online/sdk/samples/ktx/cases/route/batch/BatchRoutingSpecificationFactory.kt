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

package com.tomtom.online.sdk.samples.ktx.cases.route.batch

import com.tomtom.online.sdk.routing.batch.BatchRoutesSpecification
import com.tomtom.online.sdk.routing.route.RouteCalculationDescriptor
import com.tomtom.online.sdk.routing.route.RouteDescriptor
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.routing.route.calculation.InstructionsType
import com.tomtom.online.sdk.routing.route.description.AvoidType
import com.tomtom.online.sdk.routing.route.description.RouteType
import com.tomtom.online.sdk.routing.route.description.TravelMode
import com.tomtom.online.sdk.routing.route.diagnostic.ReportType
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToOsloRouteConfig
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToRotterdamRouteConfig
import com.tomtom.online.sdk.samples.ktx.utils.routes.RouteConfigExample

class BatchRoutingSpecificationFactory {

    private fun createAvoidRouteSpecification(routeAvoid: AvoidType, routeConfig: RouteConfigExample): RouteSpecification {
        val routeDescriptor = RouteDescriptor.Builder()
            .avoidType(listOf(routeAvoid))
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .maxAlternatives(MAX_ALTERNATIVES)
            .reportType(ReportType.NONE)
            .instructionType(InstructionsType.NONE)
            .build()

        return RouteSpecification.Builder(routeConfig.origin, routeConfig.destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
    }

    private fun createRouteTravelModesSpecification(
        travelMode: TravelMode,
        routeConfig: RouteConfigExample
    ): RouteSpecification {
        //tag::doc_common_params_for_travel_mode[]
        val routeDescriptor = RouteDescriptor.Builder()
            .travelMode(travelMode)
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .maxAlternatives(MAX_ALTERNATIVES)
            .reportType(ReportType.EFFECTIVE_SETTINGS)
            .instructionType(InstructionsType.TEXT)
            .build()

        val routeSpecification = RouteSpecification.Builder(routeConfig.origin, routeConfig.destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        //end::doc_common_params_for_travel_mode[]
        return routeSpecification
    }

    private fun createRouteTypesSpecification(
        routeType: RouteType,
        routeConfig: RouteConfigExample
    ): RouteSpecification {
        val routeDescriptor = RouteDescriptor.Builder()
            .routeType(routeType)
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .maxAlternatives(MAX_ALTERNATIVES)
            .reportType(ReportType.NONE)
            .instructionType(InstructionsType.NONE)
            .build()

        return RouteSpecification.Builder(routeConfig.origin, routeConfig.destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
    }

    fun prepareTravelModeBatchRouteSpecification(): BatchRoutesSpecification {
        //tag::doc_batch_specification[]
        val batchRoutesSpecification = BatchRoutesSpecification.Builder()
            .routeSpecifications(
                listOf(
                    createRouteTravelModesSpecification(TravelMode.CAR, AmsterdamToRotterdamRouteConfig()),
                    createRouteTravelModesSpecification(TravelMode.TRUCK, AmsterdamToRotterdamRouteConfig()),
                    createRouteTravelModesSpecification(TravelMode.PEDESTRIAN, AmsterdamToRotterdamRouteConfig())
                )
            ).build()
        //end::doc_batch_specification[]
        return batchRoutesSpecification
    }

    fun prepareRouteTypeBatchRouteSpecification(): BatchRoutesSpecification {
        return BatchRoutesSpecification.Builder()
            .routeSpecifications(
                listOf(
                    createRouteTypesSpecification(RouteType.FASTEST, AmsterdamToRotterdamRouteConfig()),
                    createRouteTypesSpecification(RouteType.SHORTEST, AmsterdamToRotterdamRouteConfig()),
                    createRouteTypesSpecification(RouteType.ECO, AmsterdamToRotterdamRouteConfig())
                )
            ).build()
    }

    fun prepareAvoidsBatchRouteSpecification(): BatchRoutesSpecification {
        return BatchRoutesSpecification.Builder()
            .routeSpecifications(
                listOf(
                    createAvoidRouteSpecification(AvoidType.MOTORWAYS, AmsterdamToOsloRouteConfig()),
                    createAvoidRouteSpecification(AvoidType.TOLL_ROADS, AmsterdamToOsloRouteConfig()),
                    createAvoidRouteSpecification(AvoidType.FERRIES, AmsterdamToOsloRouteConfig())
                )
            ).build()
    }

    private companion object {
        private const val MAX_ALTERNATIVES = 0
    }
}