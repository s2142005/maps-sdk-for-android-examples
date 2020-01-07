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

import android.content.Context
import android.location.Location
import com.tomtom.online.sdk.samples.ktx.utils.csv.parseFromAssets

class GpsCsvSimulator(context: Context, locationInterpolator: LocationInterpolator) : BaseSimulator(locationInterpolator) {

    override val locations: List<Location> =
        parseFromAssets(context, DRIVING_PROBE_CSV_FILE, DEFAULT_HEADER_LINES)

    companion object {
        private const val DRIVING_PROBE_CSV_FILE = "probes/simple_route.csv"
        private const val DEFAULT_HEADER_LINES = 0
    }
}
