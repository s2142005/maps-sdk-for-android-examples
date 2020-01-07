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

package com.tomtom.online.sdk.samples.ktx.cases.route.batch

import com.tomtom.online.sdk.routing.data.*
import com.tomtom.online.sdk.routing.data.batch.BatchRoutingQuery
import com.tomtom.online.sdk.routing.data.batch.BatchRoutingQueryBuilder
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToOsloRouteConfig
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToRotterdamRouteConfig
import com.tomtom.online.sdk.samples.ktx.utils.routes.RouteConfigExample

class BatchRoutingQueryFactory {

    private fun createAvoidRouteQuery(routeAvoid: Avoid, routeConfig: RouteConfigExample): RouteQuery {
        return RouteQueryBuilder.create(routeConfig.origin, routeConfig.destination)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withReport(Report.NONE)
                .withInstructionsType(InstructionsType.NONE)
                .withAvoidType(routeAvoid)
                .withConsiderTraffic(false)
                .build()
    }

    private fun createRouteTravelModesQuery(travelMode: TravelMode, routeConfig: RouteConfigExample): RouteQuery {
        //tag::doc_common_params_for_travel_mode[]
        val routeQuery = RouteQueryBuilder.create(routeConfig.origin, routeConfig.destination)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withTravelMode(travelMode)
                .withConsiderTraffic(false)
                .build()
        //end::doc_common_params_for_travel_mode[]
        return routeQuery
    }

    private fun createRouteTypesQuery(routeType: RouteType, routeConfig: RouteConfigExample): RouteQuery {
        return RouteQueryBuilder.create(routeConfig.origin, routeConfig.destination)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withRouteType(routeType)
                .withConsiderTraffic(false)
                .build()
    }


    fun prepareTravelModeBatchRouteQuery(): BatchRoutingQuery {
        //tag::doc_batch_query[]
        val routeQuery = BatchRoutingQueryBuilder.create()
                .withRouteQuery(createRouteTravelModesQuery(TravelMode.CAR, AmsterdamToRotterdamRouteConfig()))
                .withRouteQuery(createRouteTravelModesQuery(TravelMode.TRUCK, AmsterdamToRotterdamRouteConfig()))
                .withRouteQuery(createRouteTravelModesQuery(TravelMode.PEDESTRIAN, AmsterdamToRotterdamRouteConfig()))
                .build()
        //end::doc_batch_query[]
        return routeQuery
    }

    fun prepareRouteTypeBatchRouteQuery(): BatchRoutingQuery {
        return BatchRoutingQueryBuilder.create()
                .withRouteQuery(createRouteTypesQuery(RouteType.FASTEST, AmsterdamToRotterdamRouteConfig()))
                .withRouteQuery(createRouteTypesQuery(RouteType.SHORTEST, AmsterdamToRotterdamRouteConfig()))
                .withRouteQuery(createRouteTypesQuery(RouteType.ECO, AmsterdamToRotterdamRouteConfig()))
                .build()
    }

    fun prepareAvoidsBatchRouteQuery(): BatchRoutingQuery {
        return BatchRoutingQueryBuilder.create()
                .withRouteQuery(createAvoidRouteQuery(Avoid.MOTORWAYS, AmsterdamToOsloRouteConfig()))
                .withRouteQuery(createAvoidRouteQuery(Avoid.TOLL_ROADS, AmsterdamToOsloRouteConfig()))
                .withRouteQuery(createAvoidRouteQuery(Avoid.FERRIES, AmsterdamToOsloRouteConfig()))
                .build()
    }

    companion object {
        private const val MAX_ALTERNATIVES = 0
    }

}