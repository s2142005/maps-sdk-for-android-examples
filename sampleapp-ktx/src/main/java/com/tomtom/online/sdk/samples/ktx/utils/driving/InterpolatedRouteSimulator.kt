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

import android.location.Location
import android.os.Handler

class InterpolatedRouteSimulator(val locations: List<Location>) : Simulator {

    private val dispatcher: Handler = Handler()
    private var isActive = false
    private var gpsActiveLocationIdx = 0
    private var simulatorCallback: BaseSimulator.SimulatorCallback? = null

    val geometry by lazy { locations.map { GeoCoordinate(it.latitude, it.longitude) } }
    private var currentTime = 0L
    private var distanceTravelledInMeters = 0.0
    private var currentLineSegmentNr = 0

    override fun play(callback: BaseSimulator.SimulatorCallback) {
        if (!isActive) {
            isActive = true
            simulatorCallback = callback
            dispatcher.post(this)
        }
    }

    override fun stop(): Int {
        if (isActive) {
            isActive = false
            simulatorCallback = null
        }
        return gpsActiveLocationIdx
    }

    override fun resume(callback: BaseSimulator.SimulatorCallback, start: Int) {
        gpsActiveLocationIdx = start
        play(callback)
    }

    override fun run() {
        // Process only when active
        if (isActive) {

            // Get location info
            val location = locations[gpsActiveLocationIdx]
            val interpolatedLocation = calculateLocationWhenInterpolating()

            //Next location calculated based on route points
            //If we are visiting last location, wait 1.2 second
            //Before starting this simulation again
            var nextLocationDelay = DEFAULT_DELAY_TIME_IN_MS
            if (gpsActiveLocationIdx < locations.size - 1) {
                //Get next update time
                val nextLocation = locations[gpsActiveLocationIdx + 1]
                nextLocationDelay = nextLocation.time - location.time
                // Increase active location idx
                gpsActiveLocationIdx++
            } else {
                //Reset for new loop
                gpsActiveLocationIdx = 0
            }

            simulatorCallback?.onNewRoutePointVisited(interpolatedLocation)

            //Schedule next update
            if (gpsActiveLocationIdx < locations.size - 1) {
                dispatcher.postDelayed(this, nextLocationDelay)
            }
        }
    }

    private fun calculateLocationWhenInterpolating(): Location {
        val distances = calculateRoutePointDistances()

        currentLineSegmentNr = getLineSegmentNr(
            distances,
            currentLineSegmentNr,
            distanceTravelledInMeters
        )

        val geoLineSegment = GeoLineSegment(
            geometry[currentLineSegmentNr],
            geometry[currentLineSegmentNr + 1]
        )

        val distanceAlongSegment =
            distanceTravelledInMeters - distances[currentLineSegmentNr]

        val position = geoLineSegment.intermediatePoint(distanceAlongSegment)

        // needs to be a angle based on current location (it changes the more you progress)
        val positionVector = GeoLineSegment(geoLineSegment.begin, geoLineSegment.end)

        val interpolatedLocation = Location("dummy")
        interpolatedLocation.latitude = position.latitude
        interpolatedLocation.longitude = position.longitude
        interpolatedLocation.bearing = positionVector.angle().toFloat()
        interpolatedLocation.speed = 11f
        interpolatedLocation.time = currentTime

        currentTime += 1500

        distanceTravelledInMeters += DISTANCE_INCREMENT_METERS

        return interpolatedLocation
    }

    private fun calculateRoutePointDistances(): List<Double> {
        val segmentDistances = mutableListOf<Double>()
        segmentDistances.add(0.0)
        var cumulativeDistance = 0.0
        geometry
            .dropLast(1)
            .forEachIndexed { index, coordinate ->
                val segment = GeoLineSegment(coordinate, geometry[index + 1])
                cumulativeDistance += segment.distance()
                segmentDistances.add(cumulativeDistance)
            }
        return segmentDistances
    }

    private fun getLineSegmentNr(
        distances: List<Double>,
        preIndex: Int,
        distanceAlongRoute: Double
    ): Int {
        val nrIndexes = distances.size
        // need at least 2 nodes to do this
        return if (nrIndexes > 1) {
            for (i in preIndex until nrIndexes - 1) {
                if (distanceAlongRoute >= distances[i] && distanceAlongRoute <= distances[i + 1]) {
                    return i
                }
            }
            nrIndexes - 2
        } else -1
    }

    companion object {
        private const val DEFAULT_DELAY_TIME_IN_MS = 1200L
        private const val SPEED_MPH = 15
        private const val BROADCAST_DELAY_MILLIS = 1000L
        private const val DISTANCE_INCREMENT_METERS =
            2 * SPEED_MPH * (BROADCAST_DELAY_MILLIS / 1000)
    }
}
