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

/**
 * Implementation of a line segment - a line between two geographic coordinates.
 *
 * @param begin The begin coordinate of the line segment.
 * @param end The end coordinate of the line segment.
 */
data class GeoLineSegment(val begin: GeoCoordinate, val end: GeoCoordinate) {

    /**
     * Calculates the angle (bearing) of the line segment.
     */
    fun angle(): Double = begin.angleTo(end)

    /**
     * Returns the geographic distance the segment covers.
     */
    fun distance(): Double = begin.distanceTo(end)

    /**
     * Returns [GeoCoordinate] between [begin] and [end] in the specified [distance] in meters from [begin].
     */
    fun intermediatePoint(distance: Double): GeoCoordinate =
        begin.intermediatePointTo(end, distance)

    /**
     * Returns the fraction of the line segment based on the specified distance in meters.
     */
    fun fraction(distance: Double): Double = begin.fractionTo(end, distance)
}
