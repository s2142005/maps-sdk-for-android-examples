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

package com.tomtom.online.sdk.samples.ktx.cases.map.geopoliticalview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_geopolitical_view.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class GeopoliticalViewFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        mainViewModel.applyOnMap(MapAction { setLanguage("en-GB") })
        centerOnIsraelLocation()
    }

    override fun onExampleEnded() {
        mainViewModel.applyOnMap(MapAction {
            setGeopoliticalView(DEFAULT_VIEW)
            setLanguage("NGT")
        })
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_geopolitical_view, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        map_geopolitical_view_unified_btn.setOnClickListener {
            centerOnIsraelLocation()
            mainViewModel.applyOnMap(MapAction { setGeopoliticalView(INTERNATIONAL_VIEW) })
        }
        map_geopolitical_view_local_btn.setOnClickListener {
            centerOnIsraelLocation()
            mainViewModel.applyOnMap(MapAction {
                let { tomtomMap ->
                    //tag::doc_map_geopolitical_view[]
                    tomtomMap.setGeopoliticalView("IL")
                    //end::doc_map_geopolitical_view[]
                }
            })
        }
    }

    private fun centerOnIsraelLocation() {
        centerOnLocation(location = Locations.ISRAEL, zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE)
    }

    companion object {
        private const val DEFAULT_VIEW = ""
        private const val INTERNATIONAL_VIEW = "Unified"
        private const val DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 7.0
    }

}