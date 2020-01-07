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
package com.tomtom.online.sdk.samples.ktx.utils.driving

import android.location.Location
import android.os.Handler

abstract class BaseSimulator(private val locationInterpolator: LocationInterpolator?) : Simulator {

    private val dispatcher: Handler = Handler()
    private var isActive = false
    private var gpsActiveLocationIdx = 0
    private var simulatorCallback: SimulatorCallback? = null

    protected abstract val locations: List<Location>

    override fun play(callback: SimulatorCallback) {
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

    override fun resume(callback: SimulatorCallback, start: Int) {
        gpsActiveLocationIdx = start
        play(callback)
    }

    override fun run() {
        // Process only when active
        if (isActive) {

            // Get location info
            val location = locations[gpsActiveLocationIdx]
            val interpolatedLocation = locationInterpolator?.interpolate(location) ?: location

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

            // Notify about new location
            simulatorCallback?.onNewRoutePointVisited(interpolatedLocation)

            //Schedule next update
            if (gpsActiveLocationIdx < locations.size - 1) {
                dispatcher.postDelayed(this, nextLocationDelay)
            }
        }
    }

    interface SimulatorCallback {
        fun onNewRoutePointVisited(location: Location)
    }

    companion object {
        private const val DEFAULT_DELAY_TIME_IN_MS = 1200L
    }
}
