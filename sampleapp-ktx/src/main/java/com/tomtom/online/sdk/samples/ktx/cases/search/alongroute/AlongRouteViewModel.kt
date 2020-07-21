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

package com.tomtom.online.sdk.samples.ktx.cases.search.alongroute

import android.app.Application
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.route.*
import com.tomtom.online.sdk.routing.route.calculation.InstructionsType
import com.tomtom.online.sdk.routing.route.diagnostic.ReportType
import com.tomtom.online.sdk.routing.route.information.FullRoute
import com.tomtom.online.sdk.samples.ktx.cases.route.RoutingRequester
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToHaarlemRouteConfig
import com.tomtom.online.sdk.samples.ktx.utils.routes.RouteConfigExample
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQuery
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQueryBuilder
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResult

class AlongRouteViewModel(application: Application) : SearchViewModel(application) {

    val results = ResourceListLiveData<AlongRouteSearchResult>()
    val routingResult = ResourceLiveData<FullRoute>()

    private val routingRequester = RoutingRequester(application)


    override fun search(query: String) {
        // Not applicable to this example.
    }

    fun searchForCarRepairs() {
        routingResult.value?.data?.let { fullRoute ->
            val alongRouteSearchQuery = prepareAlongRouteQueryForTerm("REPAIR_FACILITY", fullRoute)
            searchRequester.alongRoute(alongRouteSearchQuery, results)
        }
    }

    fun searchForGasStations() {
        routingResult.value?.data?.let { fullRoute ->
            val alongRouteSearchQuery = prepareAlongRouteQueryForTerm("PETROL_STATION", fullRoute)
            searchRequester.alongRoute(alongRouteSearchQuery, results)
        }
    }

    fun searchForEvStations() {
        routingResult.value?.data?.let { fullRoute ->
            val alongRouteSearchQuery = prepareAlongRouteQueryForTerm("ELECTRIC_VEHICLE_STATION", fullRoute)
            searchRequester.alongRoute(alongRouteSearchQuery, results)
        }
    }

    fun planDefaultRoute() {
        val routeConfig = AmsterdamToHaarlemRouteConfig()
        val routeSpecification = prepareRouteQuery(routeConfig)

        results.value = Resource.loading(null)
        routingRequester.planRoute(routeSpecification, routeCallback)
    }

    private fun prepareAlongRouteQueryForTerm(term: String, route: FullRoute): AlongRouteSearchQuery {
        //tag::doc_search_along_route_query[]
        val query = AlongRouteSearchQueryBuilder(
            term,
            route.getCoordinates(),
            SEARCH_MAX_DETOUR_TIME
        )
            .withLimit(SEARCH_MAX_LIMIT)
            .build()
        //end::doc_search_along_route_query[]
        return query
    }

    private fun prepareRouteQuery(routeConfig: RouteConfigExample): RouteSpecification {
        val routeDescriptor = RouteDescriptor.Builder()
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .reportType(ReportType.NONE)
            .instructionType(InstructionsType.NONE)
            .build()

        return RouteSpecification.Builder(routeConfig.origin, routeConfig.destination)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
    }

    private val routeCallback = object : RouteCallback {
        override fun onSuccess(routePlan: RoutePlan) {
            routingResult.value = Resource.success(routePlan.routes.first())
        }

        override fun onError(error: RoutingException) {
            routingResult.value = Resource.error(null, Error(error.message))
        }
    }

    companion object {
        private const val SEARCH_MAX_DETOUR_TIME = 3600
        private const val SEARCH_MAX_LIMIT = 10
    }
}


