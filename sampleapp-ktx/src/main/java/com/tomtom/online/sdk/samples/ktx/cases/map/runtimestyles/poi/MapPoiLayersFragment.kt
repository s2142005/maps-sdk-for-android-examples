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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.poi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.map.CameraFocusArea
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_poi_layers_visibility.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class MapPoiLayersFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_poi_layers_visibility, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        map_poi_layers_visibility_all_btn.setOnClickListener { mainViewModel.applyOnMap(MapAction { showAllPois(this) }) }
        map_poi_layers_visibility_none_btn.setOnClickListener { mainViewModel.applyOnMap(MapAction { hideAllPois(this) }) }
        map_poi_layers_visibility_parks_btn.setOnClickListener { mainViewModel.applyOnMap(MapAction { showParks(this) }) }
    }

    private fun showAllPois(tomtomMap: TomtomMap) {
        //tag::doc_poi_layer_show[]
        tomtomMap.poiSettings.show()
        //end::doc_poi_layer_show[]
    }

    private fun hideAllPois(tomtomMap: TomtomMap) {
        //tag::doc_poi_layer_hide[]
        tomtomMap.poiSettings.hide()
        //end::doc_poi_layer_hide[]
    }

    private fun showParks(tomtomMap: TomtomMap) {
        tomtomMap.poiSettings.hide()
        //tag::doc_poi_layer_show_parks[]
        // PARK_CATEGORY = "Park & Recreation Area"
        tomtomMap.poiSettings.showCategories(listOf(PARK_CATEGORY))
        //end::doc_poi_layer_show_parks[]
    }

    override fun onExampleStarted() {
        mainViewModel.applyOnMap(MapAction {
            centerOn(
                CameraFocusArea.Builder(
                    BoundingBox(Locations.BERLIN_BOUNDING_BOX_TL, Locations.BERLIN_BOUNDING_BOX_BR)
                ).build()
            )
            poiSettings.turnOnRichPoiLayer()
        })
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        mainViewModel.applyOnMap(MapAction {
            poiSettings.turnOffRichPoiLayer()
            poiSettings.show()
        })
    }

    companion object {
        private const val PARK_CATEGORY = "Park & Recreation Area"
    }
}