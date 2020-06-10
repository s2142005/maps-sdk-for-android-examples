/**
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.documentation

import android.graphics.Color
import com.tomtom.online.sdk.map.OnMapReadyCallback

@Suppress("unused")
internal class GpsIndicatorRadiusColor {

    @Suppress("unused")
    private fun changeGPSIndicatorRadiusColor() {
        OnMapReadyCallback { tomtomMap ->
            val COLOR_RGBA = Color.argb(128, 128, 128, 128)
            //tag::doc_obtain_gps_indicator[]
            val gpsIndicator = tomtomMap.gpsPositionIndicator.orNull()
            //end::doc_obtain_gps_indicator[]

            //tag::doc_set_gps_indicator_active_radius[]
            gpsIndicator?.setInaccuracyAreaColor(COLOR_RGBA)
            //end::doc_set_gps_indicator_active_radius[]

            //tag::doc_set_gps_indicator_inactive_radius[]
            gpsIndicator?.setDimmedInaccuracyAreaColor(COLOR_RGBA)
            //end::doc_set_gps_indicator_inactive_radius[]
        }
    }
}