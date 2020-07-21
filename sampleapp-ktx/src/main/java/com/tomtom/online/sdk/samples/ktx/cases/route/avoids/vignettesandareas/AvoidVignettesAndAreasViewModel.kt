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

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.cases.route.RoutingRequester
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.sdk.examples.R

class AvoidVignettesAndAreasViewModel(application: Application) : RouteViewModel(application) {

    private val routingRequester = RoutingRequester(application)

    var routesDescription = MutableLiveData(intArrayOf())
    lateinit var exampleType: ExampleType

    fun planBaseRoute() {
        exampleType = ExampleType.NO_AVOID
        routesDescription.value = intArrayOf(R.string.no_avoids)

        val routeSpecification = AvoidVignettesAndAreasSpecificationFactory()
            .createBaseRouteForAvoidVignettesAndAreas()

        planRoute(routeSpecification)
    }

    fun planAvoidVignettesRoute(avoidVignettesList: List<String>) {
        exampleType = ExampleType.AVOID_VIGNETTES
        routesDescription.value = intArrayOf(R.string.avoid_vignettes_text, R.string.no_avoids)

        val routeSpecification = AvoidVignettesAndAreasSpecificationFactory()
            .createBaseRouteForAvoidVignettesAndAreas()
        val avoidVignettesRouteSpecification = AvoidVignettesAndAreasSpecificationFactory()
            .createRouteForAvoidsVignettes(avoidVignettesList)

        planAvoidRoutes(listOf(avoidVignettesRouteSpecification, routeSpecification))
    }

    fun planAvoidAreaRoute(boundingBox: BoundingBox) {
        exampleType = ExampleType.AVOID_AREAS
        routesDescription.value = intArrayOf(R.string.avoid_area_text, R.string.no_avoids)

        val routeSpecification = AvoidVignettesAndAreasSpecificationFactory()
            .createBaseRouteForAvoidVignettesAndAreas()
        val avoidAreaRouteSpecification = AvoidVignettesAndAreasSpecificationFactory()
            .createRouteForAvoidsAreas(boundingBox)

        planAvoidRoutes(listOf(avoidAreaRouteSpecification, routeSpecification))
    }

    private fun planAvoidRoutes(routeSpecificationList: List<RouteSpecification>) {
        selectedRoute.value = null
        routes.value = Resource.loading(null)
        routingRequester.planRoutes(routeSpecificationList, routes)
    }

    enum class ExampleType {
        NO_AVOID, AVOID_VIGNETTES, AVOID_AREAS
    }

}
