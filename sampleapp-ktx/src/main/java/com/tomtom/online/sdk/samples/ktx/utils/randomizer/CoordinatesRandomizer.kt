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

package com.tomtom.online.sdk.samples.ktx.utils.randomizer

import com.tomtom.online.sdk.common.location.LatLng
import java.util.*
import kotlin.random.Random

object CoordinatesRandomizer {

    private const val NUMBER_OF_POINTS = 24
    private const val CENTER_SPACE_RATION = 0.8
    private const val BASE_RATION = 1.0
    private const val BASE_RADIUS = 128.0f
    private const val NUMBER_OF_BITS_TO_SHIFT = 1

    fun randomCoordinates(centroid: LatLng, degrees: Float, zoomLevel: Int = 10): List<LatLng> {
        val radius = suitableRadiusToZoomLevel(zoomLevel)
        val degreesPerPoint = degrees / NUMBER_OF_POINTS

        val coordinates = ArrayList<LatLng>()
        for (i in 0 until NUMBER_OF_POINTS) {
            val distance = radius * CENTER_SPACE_RATION + radius * (BASE_RATION - CENTER_SPACE_RATION) * randomRation()
            val angle = i * degreesPerPoint + randomRation() * degreesPerPoint
            val y = Math.sin(Math.toRadians(angle.toDouble())) * distance
            val x = Math.cos(Math.toRadians(angle.toDouble())) * distance
            val latLng = LatLng(
                centroid.latitude + y,
                centroid.longitude + x
            )
            coordinates.add(latLng)
        }
        return coordinates
    }

    private fun suitableRadiusToZoomLevel(zoomLevel: Int): Float {
        return BASE_RADIUS / (NUMBER_OF_BITS_TO_SHIFT shl zoomLevel)
    }

    private fun randomRation(): Float {
        return Random.nextFloat()
    }

}