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
import com.tomtom.online.sdk.common.util.DistanceCalculator
import com.tomtom.online.sdk.routing.route.information.FullRoute
import java.util.*

private const val MILLIS_IN_HOUR = 1000 * 60 * 60
private const val NEXT_POINT_TIME_DIFF_IN_MILLIS = 1200.0
private const val POINT_DEFAULT_ACCURACY = 0.0f
private const val POINT_DEFAULT_BEARING = 0.0f
private const val MAX_SPEED_LIMIT = 120.0f
private const val MIN_SPEED = 0.0f

fun convertFromRoutesToLocations(
    routes: List<FullRoute>,
    pointTimeDiff: Long = NEXT_POINT_TIME_DIFF_IN_MILLIS.toLong()
): List<Location> {
    if (routes.isEmpty()) {
        return emptyList()
    }

    var pointTime = Date().time
    val routePoints = routes[0].getCoordinates()

    val routeLocations = routePoints.mapIndexed { pointIdx, point ->
        val previous = routePoints.getOrNull(pointIdx - 1)
        pointTime += pointTimeDiff
        point.toLocation().apply {
            bearing = previous?.let { getBearingInDegrees(it, point) } ?: POINT_DEFAULT_BEARING
            accuracy = POINT_DEFAULT_ACCURACY
            speed = previous?.let { speedBetweenPoints(it, point) } ?: MIN_SPEED
            time = pointTime
        }
    }

    normalizeSpeeds(routeLocations)

    return routeLocations
}

private fun speedBetweenPoints(first: LatLng, second: LatLng): Float {
    val distance = DistanceCalculator.calcDistInKilometers(first, second)
    val timeInHours = NEXT_POINT_TIME_DIFF_IN_MILLIS / (MILLIS_IN_HOUR)
    return (distance / timeInHours).toFloat()
}

private fun normalizeSpeeds(locations: List<Location>) {
    val speeds = locations.map { it.speed }.sorted()
    val min = speeds.drop(1).firstOrNull() ?: MIN_SPEED
    val max = speeds.dropLast(1).lastOrNull() ?: MAX_SPEED_LIMIT
    locations.forEach { location ->
        location.speed = kotlin.math.max(
            MIN_SPEED,
            kotlin.math.min(
                MAX_SPEED_LIMIT,
                MAX_SPEED_LIMIT * (location.speed - min) / (max - min)
            )
        )
    }
}

private fun getBearingInDegrees(from: LatLng, to: LatLng): Float {
    val fromLocation = from.toLocation()
    val toLocation = to.toLocation()
    return fromLocation.bearingTo(toLocation)
}
