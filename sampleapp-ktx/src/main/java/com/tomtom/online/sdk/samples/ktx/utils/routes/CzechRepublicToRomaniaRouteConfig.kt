/*
 * Copyright (c) 2015-2019 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.utils.routes

import com.tomtom.online.sdk.common.location.LatLng

class CzechRepublicToRomaniaRouteConfig : RouteConfigExample {

    override val origin: LatLng
        get() = LatLng(50.746420115485755, 14.799316562712196)

    override val destination: LatLng
        get() = LatLng(45.33232542221267, 22.753418125212196)

    override val waypoints: List<LatLng>?
        get() = null

}