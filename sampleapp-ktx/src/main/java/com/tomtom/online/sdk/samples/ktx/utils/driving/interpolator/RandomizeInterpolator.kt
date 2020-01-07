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
package com.tomtom.online.sdk.samples.ktx.utils.driving.interpolator

import android.location.Location

import java.util.Calendar
import java.util.Random

class RandomizeInterpolator : com.tomtom.online.sdk.samples.ktx.utils.driving.LocationInterpolator {

    override fun interpolate(location: Location): Location {
        val result = Location(location)

        val random = Random(Calendar.getInstance().timeInMillis)

        // Convert radius from meters to degrees
        val radiusInDegrees = (DEFAULT_RADIUS_IN_METERS / METERS_TO_DEGREES_FACTOR).toDouble()

        val u = random.nextDouble()
        val v = random.nextDouble()
        val w = radiusInDegrees * Math.sqrt(u)
        val t = 2.0 * Math.PI * v
        val newX = Math.abs(w * Math.cos(t))
        val newY = Math.abs(w * Math.sin(t))
        val yS = Math.sin(Math.toRadians(location.bearing.toDouble()))
        val xS = -Math.cos(Math.toRadians(location.bearing.toDouble()))

        val foundLongitude = newX * xS + location.longitude
        val foundLatitude = newY * yS + location.latitude

        result.latitude = foundLatitude
        result.longitude = foundLongitude
        return result
    }

    companion object {

        private const val DEFAULT_RADIUS_IN_METERS = 35
        private const val METERS_TO_DEGREES_FACTOR = 111000f
    }

}
