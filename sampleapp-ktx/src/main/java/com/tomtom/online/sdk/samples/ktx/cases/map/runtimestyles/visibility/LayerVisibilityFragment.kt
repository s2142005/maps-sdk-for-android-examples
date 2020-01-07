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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.visibility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.map.style.layers.Visibility
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_map_layers.*

class LayerVisibilityFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_layers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewActions()
    }

    override fun onExampleStarted() {
        applyVisibilityToSelectedLayers(EXAMPLE_LAYERS_REGEX, Visibility.NONE)
        centerOnLocation(location = Locations.BERLIN)
    }

    override fun onExampleEnded() {
        applyVisibilityToSelectedLayers(EXAMPLE_LAYERS_REGEX, Visibility.VISIBLE)
    }

    private fun confViewActions() {
        map_layers_road_network_btn.setOnCheckedChangeListener { _, isChecked ->
            applyVisibilityToSelectedLayers(ROADS_NETWORK_LAYER_REGEX, determineVisibility(isChecked))
        }

        map_layers_woodland_btn.setOnCheckedChangeListener { _, isChecked ->
            applyVisibilityToSelectedLayers(WOODLAND_LAYER_REGEX, determineVisibility(isChecked))
        }

        map_layers_build_up_btn.setOnCheckedChangeListener { _, isChecked ->
            applyVisibilityToSelectedLayers(BUILT_UP_AREA_LAYER_REGEX, determineVisibility(isChecked))
        }
    }

    private fun determineVisibility(isChecked: Boolean): Visibility {
        return if (isChecked) Visibility.VISIBLE else Visibility.NONE
    }

    private fun applyVisibilityToSelectedLayers(regex: String, visibility: Visibility) {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::find_layers_by_source_layer_id[]
                //regex = e.g. "[mM]otorway.*|.*[rR]oad.*" or "[wW]oodland" or "Built-up area"
                val layers = tomtomMap.styleSettings.findLayersBySourceLayerId(String.format(regex))
                //end::find_layers_by_source_layer_id[]

                //tag::modify_layers_visibility[]
                layers.forEach { layer -> layer.visibility = visibility }
                //end::modify_layers_visibility[]
            }
        })
    }

    companion object {
        private const val ROADS_NETWORK_LAYER_REGEX = "[mM]otorway.*|.*[rR]oad.*"
        private const val WOODLAND_LAYER_REGEX = "[wW]oodland"
        private const val BUILT_UP_AREA_LAYER_REGEX = "Built-up area"
        private const val EXAMPLE_LAYERS_REGEX = "[mM]otorway.*|.*[rR]oad.*|[wW]oodland|Built-up area"
    }
}