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
package com.tomtom.online.sdk.samples.ktx.utils.formatter

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import java.util.*

object DistanceFormatter {

    private const val METERS_IN_ONE_KM = 1000.0
    private const val METERS_IN_ONE_MILE = 1609.0

    private val isMiles: Boolean
        get() {
            val locale = Locale.getDefault()
            return locale == Locale.US || locale == Locale.UK
        }

    fun format(distanceInMeters: Int): String {
        return if (isMiles) {
            formatMiles(distanceInMeters)
        } else {
            formatKilometers(distanceInMeters)
        }
    }

    @SuppressLint("DefaultLocale")
    @VisibleForTesting
    internal fun formatKilometers(distanceInMeters: Int): String {
        val distanceInKilometers = distanceInMeters / METERS_IN_ONE_KM
        return String.format("%1$,.2f km", distanceInKilometers)
    }

    @VisibleForTesting
    internal fun formatMiles(distanceInMeters: Int): String {
        val distanceInMiles = distanceInMeters / METERS_IN_ONE_MILE
        return String.format(Locale.getDefault(), "%1$,.2f mi", distanceInMiles)
    }

}