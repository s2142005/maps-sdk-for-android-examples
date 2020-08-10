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
package com.tomtom.online.sdk.samples.ktx.cases.search.evstations

import android.app.Application
import com.tomtom.online.sdk.map.Overlay
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.route.*
import com.tomtom.online.sdk.routing.route.calculation.InstructionsType
import com.tomtom.online.sdk.routing.route.diagnostic.ReportType
import com.tomtom.online.sdk.samples.ktx.cases.route.RoutingRequester
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.SingleLiveEvent
import com.tomtom.online.sdk.samples.ktx.utils.geometry.DefaultGeometryCreator
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToHaarlemRouteConfig
import com.tomtom.online.sdk.samples.ktx.utils.routes.RouteConfigExample

class EvStationsViewModel(application: Application) : SearchViewModel(application) {

    val routingResults = ResourceLiveData<RoutePlan>()
    val chargingStationsResults = ResourceListLiveData<ChargingStationDetails>()
    val geometryOnMap: SingleLiveEvent<Overlay> = SingleLiveEvent()
    private val routingRequester = RoutingRequester(application)
    private val evStationsQueriesFactory = EvStationsQueriesFactory()

    override fun search(query: String) {
        //NO-OP
    }

    fun searchAlongRoute() {
        planDefaultRoute(object : RouteCallback {
            override fun onSuccess(routePlan: RoutePlan) {
                routingResults.value = Resource.success(routePlan)
                val alongRouteSearchQuery = evStationsQueriesFactory.createAlongRouteQuery(routePlan.routes.first())
                searchRequester.evChargingStations.search(alongRouteSearchQuery, chargingStationsResults)
            }

            override fun onError(error: RoutingException) {
                routingResults.value = Resource.error(Error(error))
            }
        })
    }

    fun searchGeometry() {
        geometryOnMap.value = POLYGON_OVERLAY_EXAMPLE
        val geometryQuery = evStationsQueriesFactory.createGeometryQuery()
        searchRequester.evChargingStations.search(geometryQuery, chargingStationsResults)
    }

    fun searchFuzzyInBoundingBox() {
        val fuzzySearchQuery = evStationsQueriesFactory.createFuzzyInBoundingBoxQuery()
        searchRequester.evChargingStations.search(fuzzySearchQuery, chargingStationsResults)
    }

    private fun planDefaultRoute(routeCallback: RouteCallback) {
        val routeSpecification = prepareRouteQuery(EV_ROUTING_CONFIG_EXAMPLE)
        routingRequester.planRoute(routeSpecification, routeCallback)
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

    companion object {
        private val EV_ROUTING_CONFIG_EXAMPLE = AmsterdamToHaarlemRouteConfig()
        private val POLYGON_OVERLAY_EXAMPLE = DefaultGeometryCreator.createDefaultPolygonOverlay()
    }
}
