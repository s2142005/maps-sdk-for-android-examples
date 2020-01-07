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

package com.tomtom.online.sdk.samples.ktx.cases.map.buildingheights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.map.style.layers.Visibility
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_building_heights.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class MapBuildingHeightsFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        centerOnLocation(zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE, location = Locations.LONDON)
        mainViewModel.applyOnMap(MapAction { set3DMode() })
    }

    override fun onExampleEnded() {
        mainViewModel.applyOnMap(MapAction { set2DMode() })
        show3DBuildings()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_building_heights, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        map_disable_building_heights.setOnClickListener {
            mainViewModel.applyOnMap(MapAction { set2DMode() })
            hide3Dbuildings()
        }

        map_enable_building_heights.setOnClickListener {
            mainViewModel.applyOnMap(MapAction { set3DMode() })
            show3DBuildings()
        }
    }

    private fun hide3Dbuildings() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_disable_building_heights[]
                val layers = tomtomMap.styleSettings.findLayersById(LAYERS_IN_3D_REGEX)
                layers.forEach { layer ->
                    layer.visibility = Visibility.NONE
                }
                //end::doc_disable_building_heights[]
            }
        })
    }

    private fun show3DBuildings() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_enable_building_heights[]
                val layers = tomtomMap.styleSettings.findLayersById(LAYERS_IN_3D_REGEX)
                layers.forEach {
                    it.visibility = Visibility.VISIBLE
                }
                //end::doc_enable_building_heights[]
            }
        })
    }

    companion object {
        const val DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 17.0
        //tag::doc_map_building_3d_layers[]
        val LAYERS_IN_3D_REGEX = listOf(
            "Subway Station 3D",
            "Place of worship 3D",
            "Railway Station 3D",
            "Government Administration Office 3D",
            "Other building 3D",
            "School building 3D",
            "Other town block 3D",
            "Factory building 3D",
            "Hospital building 3D",
            "Hotel building 3D",
            "Cultural Facility 3D")
            .joinToString(separator = "|")
        //end::doc_map_building_3d_layers[]
    }

}