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

package com.tomtom.online.sdk.samples.ktx.cases.search.evstations

import android.content.Context
import android.graphics.drawable.LayerDrawable
import androidx.core.content.ContextCompat
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.sdk.examples.R

class EvAvailabilityMarkerDrawer(private val context: Context, private val tomtomMap: TomtomMap) {

    fun createMarkersForChargingWaypoints(chargingStations: List<ChargingStationDetails>) {
        chargingStations.forEach {
            val available = it.connectors.first().availability.available
            val balloon =
                EvAvailabilityMarkerBalloon(
                    it.name,
                    it.address,
                    it.connectors.first().type,
                    "$available/${it.connectors.first().total} available"
                )
            tomtomMap.markerSettings.addMarker(
                MarkerBuilder(it.coordinates)
                    .markerBalloon(balloon)
                    .icon(getEvAvailabilityIcon(available))
            )
        }
    }

    private fun getEvAvailabilityIcon(availableConnectors: Int) =
        if (availableConnectors == 0) {
            evStationNotAvailable()
        } else evStationAvailable()

    private fun evStationNotAvailable(): Icon {
        return availabilityIcon(
            ICON_NAME_UNAVAILABLE,
            R.drawable.ic_map_poi_square_pin_availability_badge_red
        )
    }

    private fun evStationAvailable(): Icon {
        return availabilityIcon(
            ICON_NAME_AVAILABLE,
            R.drawable.ic_map_poi_square_pin_availability_badge_green
        )
    }

    private fun availabilityIcon(name: String, badgeResId: Int): Icon {
        return Icon.Factory.fromLayerDrawable(
            name,
            LayerDrawable(
                arrayOf(
                    ContextCompat.getDrawable(context, R.drawable.ic_pin_ev_station),
                    ContextCompat.getDrawable(context, badgeResId)
                )
            )
        )
    }

    companion object {
        private const val ICON_NAME_AVAILABLE = "icon_available"
        private const val ICON_NAME_UNAVAILABLE = "icon_unavailable"
    }
}