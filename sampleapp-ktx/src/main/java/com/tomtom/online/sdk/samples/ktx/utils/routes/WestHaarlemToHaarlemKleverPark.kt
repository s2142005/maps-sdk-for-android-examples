package com.tomtom.online.sdk.samples.ktx.utils.routes

import com.tomtom.online.sdk.common.location.LatLng

class WestHaarlemToHaarlemKleverPark : RouteConfigExample {

    override val origin: LatLng
        get() = Locations.HAARLEM_WEST

    override val destination: LatLng
        get() = Locations.HAARLEM_KLEVERPARK

    override val waypoints: List<LatLng>?
        get() = null
}