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
import java.util.*

object Locations {

    val BERLIN = LatLng(52.520007, 13.404954)
    val ROTTERDAM = LatLng(51.935966, 4.482865)
    val AMSTERDAM = LatLng(52.377271, 4.909466)
    val AMSTERDAM_CENTER = LatLng(52.373154, 4.890659)
    val AMSTERDAM_HOOFDWEG = LatLng(52.3691851, 4.8505632)
    val AMSTERDAM_BARNDESTEEG = LatLng(52.372144, 4.899115)
    val OSLO = LatLng(59.911491, 10.757933)
    val LONDON = LatLng(51.507351, -0.127758)
    val AMSTERDAM_HENK_SNEEVLIETWEG = LatLng(52.345971, 4.844899)
    val AMSTERDAM_JAN_VAN_GALENSTRAAT = LatLng(52.372281, 4.846595)
    val AMSTERDAM_HAARLEM = LatLng(52.381222, 4.637558)
    val UTRECHT = LatLng(52.09179, 5.11457)
    val LODZ = LatLng(51.759434, 19.449011)
    val ISRAEL = LatLng(32.009929, 34.843555)
    val HOOFDDORP = LatLng(52.3058782, 4.6483191)
    val AMSTERDAM_BERLIN_CENTER = LatLng(52.575119, 9.149708)
    val COIMBRA = LatLng(40.209408, -8.423741)
    val CONDEIXA = LatLng(40.109957, -8.501433)
    val BUDAPEST = LatLng(47.498733, 19.072646)
    val ARAD_TOP_LEFT_NEIGHBORHOOD = LatLng(46.241223, 21.012896)
    val ARAD_BOTTOM_RIGHT_NEIGHBORHOOD = LatLng(45.861624, 21.506465)
    val ARAD_BOTTOM_LEFT_NEIGHBORHOOD = LatLng(45.861624, 21.012896)
    val ARAD_TOP_RIGHT_NEIGHBORHOOD = LatLng(46.241223, 21.506465)
    val HAARLEM_WEST = LatLng(52.392282, 4.634233)
    val HAARLEM_KLEVERPARK = LatLng(52.384713, 4.625929)
    val BUCKINGHAM_PALACE = LatLng(51.501197, -0.1436497)
    val SAN_JOSE = LatLng(37.176238, -122.139924)
    val SAN_JOSE_IMG1 = LatLng(37.76028, -122.454246)
    val SAN_JOSE_IMG2 = LatLng(36.926304, -121.967966)
    val SAN_JOSE_IMG3 = LatLng(37.455662, -122.492698)
    val SAN_FRANCISCO = LatLng(37.7793, -122.419)
    val SANTA_CRUZ = LatLng(36.9749416, -122.0285259)
    val CZECH_REPUBLIC = LatLng(50.746420115485755, 14.799316562712196)
    val ROMANIA = LatLng(45.33232542221267, 22.753418125212196)

    private var r = Random(System.currentTimeMillis())

    fun randomLocations(center: LatLng, number: Int, offsetMultiplier: Float): List<LatLng> {
        val latLngs = ArrayList<LatLng>()
        for (i in 0 until number) {
            val lat = center.latitude + (r.nextFloat() - 0.5f) * offsetMultiplier
            val lng = center.longitude + (r.nextFloat() - 0.5f) * offsetMultiplier
            latLngs.add(LatLng(lat, lng))
        }
        return ImmutableList.copyOf(latLngs)
    }
}
