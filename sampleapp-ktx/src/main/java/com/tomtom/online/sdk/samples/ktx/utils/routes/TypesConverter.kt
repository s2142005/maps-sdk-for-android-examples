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

import android.location.Location
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.routing.data.FullRoute
import java.util.*

private const val NEXT_POINT_TIME_DIFF_IN_MILLIS = 1200.0
private const val POINT_DEFAULT_ACCURACY = 0.0f
private const val POINT_DEFAULT_SPEED = 50.0f
private const val POINT_DEFAULT_BEARING = 0.0f

fun convertFromRoutesToLocations(routes: List<FullRoute>): List<Location> {
    if (routes.isEmpty()) {
        return emptyList()
    }

    var pointTime = Date().time
    val routePoints = routes[0].coordinates
    val routeLocations = ArrayList<Location>()

    for ((pointIdx, point) in routePoints.withIndex()) {

        var pointBearing = POINT_DEFAULT_BEARING
        if (pointIdx > 0) {
            val prevPoint = routePoints[pointIdx - 1]
            pointBearing = getBearingInDegrees(prevPoint, point)
        }

        val location = point.toLocation()
        location.bearing = pointBearing
        location.time = pointTime
        location.accuracy = POINT_DEFAULT_ACCURACY
        location.speed = POINT_DEFAULT_SPEED

        routeLocations.add(location)

        pointTime += NEXT_POINT_TIME_DIFF_IN_MILLIS.toLong()
    }

    return routeLocations
}

private fun getBearingInDegrees(from: LatLng, to: LatLng): Float {
    val fromLocation = from.toLocation()
    val toLocation = to.toLocation()
    return fromLocation.bearingTo(toLocation)
}
