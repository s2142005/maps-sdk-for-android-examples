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
package com.tomtom.online.sdk.samples.ktx.utils.routes

import com.google.common.collect.ImmutableList
import com.tomtom.online.sdk.common.location.LatLng

class LodzCityCenterRouteConfig : RouteConfigExample {

    override val origin: LatLng
        get() = LatLng(51.773136, 19.4233983)

    override val destination: LatLng
        get() = LatLng(51.772756, 19.423065)

    override val waypoints: List<LatLng>?
        get() = ImmutableList.of(
                LatLng(51.780990, 19.451229),
                LatLng(51.786451, 19.449562),
                LatLng(51.791383, 19.420641)
        )
}