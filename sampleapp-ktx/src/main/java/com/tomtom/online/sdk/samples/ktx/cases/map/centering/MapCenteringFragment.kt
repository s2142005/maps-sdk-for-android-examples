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

package com.tomtom.online.sdk.samples.ktx.cases.map.centering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.map.AnimationDuration
import com.tomtom.online.sdk.map.CameraFocusArea
import com.tomtom.online.sdk.map.MapConstants
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_centering.*
import kotlinx.android.synthetic.main.default_map_fragment.*
import java.util.concurrent.TimeUnit

class MapCenteringFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        centerOnAmsterdam()
    }

    override fun onExampleEnded() {
        mainViewModel.applyOnMap(MapAction { clear() })
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_centering, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        map_centering_amsterdam_btn.setOnClickListener { centerOnAmsterdam() }
        map_centering_berlin_btn.setOnClickListener { centerOnBerlin() }
        map_centering_area_btn.setOnClickListener { centerOnBoundingBox() }
    }

    private fun centerOnAmsterdam() {
        centerOnLocation()
    }

    private fun centerOnBerlin() {
        centerOnLocation(location = Locations.BERLIN)
    }

    private fun centerOnBoundingBox() {
        centerOnArea(topLeft = Locations.AMSTERDAM_HAARLEM, bottomRight = Locations.AMSTERDAM_CENTER)
    }

    @Suppress("unused")
    fun turnOnMyLocation() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_my_location_enabled[]
                tomtomMap.isMyLocationEnabled = true
                //end::doc_my_location_enabled[]
            }
        })
    }

    @Suppress("unused")
    fun getCurrentLocation() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_get_current_location[]
                val location = tomtomMap.userLocation
                //end::doc_get_current_location[]
            }
        })
    }

}