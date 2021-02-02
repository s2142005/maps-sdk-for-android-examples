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

package com.tomtom.online.sdk.samples.ktx.cases.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.route.RouteCallback
import com.tomtom.online.sdk.routing.route.RoutePlan
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.routing.route.information.FullRoute
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.SingleLiveEvent

abstract class RouteViewModel(application: Application) : AndroidViewModel(application) {

    internal val routes = ResourceLiveData<List<FullRoute>>()
    internal var selectedRoute = SingleLiveEvent<Pair<FullRoute, Int>>()

    fun planRoute(routeSpecification: RouteSpecification) {
        selectedRoute.value = null
        routes.value = Resource.loading(null)
        RoutingRequester(getApplication()).planRoute(routeSpecification, routeCallback)
    }

    fun selectRouteIdx(idx: Int) {
        routes.value?.data?.let { filteredRoutes ->
            selectedRoute.value = Pair(filteredRoutes[idx], idx)
        }
    }

    private val routeCallback = object : RouteCallback {
        override fun onSuccess(routePlan: RoutePlan) {
            routes.value = Resource.success(routePlan.routes)
        }

        override fun onError(error: RoutingException) {
            routes.value = Resource.error(null, Error(error.message))
        }
    }
}