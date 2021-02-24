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
package com.tomtom.online.sdk.samples.ktx.utils.driving

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.atan2
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Implementation of geographic coordinates.
 *
 * @param latitude Latitude value, should be between -90.0 and 90.0.
 * @param longitude Longitude value, should be between -180.0 and 180.0.
 */
data class GeoCoordinate(val latitude: Double, val longitude: Double) {

    init {
        require(latitude in MIN_LATITUDE..MAX_LATITUDE) {
            "Latitude value $latitude should be in the range [$MIN_LATITUDE, $MAX_LATITUDE]"
        }
        require(longitude in MIN_LONGITUDE..MAX_LONGITUDE) {
            "Longitude value $longitude should be in the range [$MIN_LONGITUDE, $MAX_LONGITUDE]"
        }
    }

    /**
     * Calculates the angle (bearing) between this [GeoCoordinate] and [that].
     */
    fun angleTo(that: GeoCoordinate): Double {
        val long2 = Math.toRadians(that.longitude)
        val long1 = Math.toRadians(this.longitude)
        val lat2 = Math.toRadians(that.latitude)
        val lat1 = Math.toRadians(this.latitude)
        val dLon: Double = long2 - long1

        val y = sin(dLon) * cos(lat2)
        val x = cos(lat1) * sin(lat2) -
                (sin(lat1) * cos(lat2) * cos(dLon))

        return (Math.toDegrees(atan2(y, x)) + DEGREES_360) % DEGREES_360
    }

    /**
     * Calculates the geographic distance from this [GeoCoordinate] and [that] in meters.
     */
    fun distanceTo(that: GeoCoordinate): Double {
        val dLat = Math.toRadians(that.latitude - this.latitude)
        val dLng = Math.toRadians(that.longitude - this.longitude)

        val bLat = Math.toRadians(this.latitude)
        val eLat = Math.toRadians(that.latitude)

        val a = sin(dLat / INT_2).pow(INT_2.toDouble()) +
                sin(dLng / INT_2).pow(INT_2.toDouble()) *
                cos(bLat) * cos(eLat)
        val c = INT_2 * asin(sqrt(a))

        return c * EARTH_RADIUS
    }

    /**
     * Calculates the fraction between [GeoCoordinate]s in [distance] specified in meters.
     */
    fun fractionTo(that: GeoCoordinate, distance: Double): Double {
        val lineSegmentDistance = this.distanceTo(that)
        if (lineSegmentDistance >= DOUBLE_0) {
            val fraction = distance / lineSegmentDistance
            return if (fraction > DOUBLE_1) DOUBLE_1 else fraction
        }
        return DOUBLE_0
    }

    /**
     * Calculates [GeoCoordinate] between this [GeoCoordinate] and [that] at a specified distance in meters.
     */
    fun intermediatePointTo(that: GeoCoordinate, distance: Double): GeoCoordinate {
        val fraction = this.fractionTo(that, distance)

        val lat1 = Math.toRadians(this.latitude)
        val lng1 = Math.toRadians(this.longitude)
        val lat2 = Math.toRadians(that.latitude)
        val lng2 = Math.toRadians(that.longitude)
        val d = 2 * asin(
            sqrt(
                (sin((lat1 - lat2) / INT_2)).pow(INT_2) +
                        cos(lat1) * cos(lat2) *
                        sin((lng1 - lng2) / INT_2).pow(INT_2)
            )
        )
        val iA = sin((1 - fraction) * d) / sin(d)
        val iB = sin(fraction * d) / sin(d)
        val x = iA * cos(lat1) * cos(lng1) + iB * cos(lat2) * cos(lng2)
        val y = iA * cos(lat1) * sin(lng1) + iB * cos(lat2) * sin(lng2)
        val z = iA * sin(lat1) + iB * sin(lat2)
        val lat = atan2(z, sqrt(x.pow(INT_2) + y.pow(INT_2)))
        val lng = atan2(y, x)

        return GeoCoordinate(Math.toDegrees(lat), Math.toDegrees(lng))
    }

    companion object {
        private const val MAX_LATITUDE = 90.0
        private const val MIN_LATITUDE = -90.0
        private const val MAX_LONGITUDE = 180.0
        private const val MIN_LONGITUDE = -180.0

        private const val DEGREES_360 = 360
        private const val INT_2 = 2
        private const val DOUBLE_0 = 0.0
        private const val DOUBLE_1 = 1.0

        private const val EARTH_RADIUS = 6371e3
    }
}
