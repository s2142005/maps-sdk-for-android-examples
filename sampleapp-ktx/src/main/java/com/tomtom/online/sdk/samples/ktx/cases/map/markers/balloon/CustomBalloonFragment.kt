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

package com.tomtom.online.sdk.samples.ktx.cases.map.markers.balloon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.map.BaseMarkerBalloon
import com.tomtom.online.sdk.map.SingleLayoutBalloonViewAdapter
import com.tomtom.online.sdk.map.TextBalloonViewAdapter
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.cases.map.markers.MarkerDrawer
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_custom_balloon.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class CustomBalloonFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation()
    }

    override fun onResume() {
        super.onResume()
        adjustBalloonAdapter()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearMarkers()
        restoreDefaultBalloonAdapter()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_custom_balloon, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        markers_simple_balloon_btn.setOnClickListener {
            clearMarkers()
            centerOnLocation()
            drawMarkerWithSimpleBalloon()
        }

        markers_custom_balloon_btn.setOnClickListener {
            clearMarkers()
            centerOnLocation()
            drawMarkerWithCustomBalloon()
        }
    }

    private fun drawMarkerWithSimpleBalloon() {
        mainViewModel.applyOnMap(MapAction {
            MarkerDrawer(this).createMarkerWithSimpleBalloon(Locations.AMSTERDAM)
        })
    }

    private fun drawMarkerWithCustomBalloon() {
        mainViewModel.applyOnMap(MapAction {
            MarkerDrawer(this).createMarkerWithCustomBalloon(Locations.AMSTERDAM, R.layout.custom_balloon)
        })
    }

    private fun clearMarkers() {
        mainViewModel.applyOnMap(MapAction { removeMarkers() })
    }

    private fun adjustBalloonAdapter() {
        mainViewModel.applyOnMap(MapAction {
            markerSettings.markerBalloonViewAdapter = if (markers_simple_balloon_btn.isChecked)
                TextBalloonViewAdapter() else SingleLayoutBalloonViewAdapter(R.layout.custom_balloon)
        })
    }

    private fun restoreDefaultBalloonAdapter() {
        mainViewModel.applyOnMap(MapAction {
            markerSettings.markerBalloonViewAdapter = TextBalloonViewAdapter()
        })
    }

    @Suppress("unused")
    private fun exampleOfCreatingBaseBalloon() {
        //tag::doc_marker_balloon_model[]
        val markerBalloon = BaseMarkerBalloon()
        markerBalloon.addProperty("key", "value")
        //end::doc_marker_balloon_model[]
    }

}