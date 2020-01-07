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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder

import android.app.Application
import com.tomtom.online.sdk.routing.data.RouteQuery
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations

class DynamicLayerViewModel(application: Application): RouteViewModel(application) {

    fun planSanFranciscoToSantaCruzRoute() {
        planRoute(prepareRouteQuery())
    }

    private fun prepareRouteQuery(): RouteQuery {
        return RouteQueryBuilder.create(Locations.SAN_FRANCISCO, Locations.SANTA_CRUZ).build()
    }
}