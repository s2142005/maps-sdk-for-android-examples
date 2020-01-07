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

import com.tomtom.online.sdk.common.location.LatLng

import java.math.BigDecimal
import java.math.RoundingMode

object LatLngFormatter {

    private const val SCALE = 6

    fun toSimpleString(latLng: LatLng): String {
        val str = StringBuilder()

        val lat = BigDecimal.valueOf(latLng.latitude).setScale(SCALE, RoundingMode.CEILING).stripTrailingZeros()
        val lon = BigDecimal.valueOf(latLng.longitude).setScale(SCALE, RoundingMode.CEILING).stripTrailingZeros()
        str.append(lat.toPlainString()).append(',').append(lon.toPlainString())

        return str.toString()
    }
}
