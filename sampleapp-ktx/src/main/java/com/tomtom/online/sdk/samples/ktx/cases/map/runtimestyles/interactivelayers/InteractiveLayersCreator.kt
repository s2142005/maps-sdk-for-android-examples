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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.interactivelayers

import android.content.Context
import com.tomtom.online.sdk.common.util.AssetUtils
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.map.style.layers.LayerFactory
import com.tomtom.online.sdk.map.style.sources.SourceFactory

class InteractiveLayersCreator(private val context: Context, private val tomtomMap: TomtomMap) {

    fun addFeaturesToStyle() {
        addSourceToStyle()
        addLayersToStyle()
    }

    private fun addSourceToStyle() {
        val geoJsonData = AssetUtils.getAssetFile(context, SOURCE_PATH)

        val source = SourceFactory.createGeoJsonSource(SOURCE_ID)
        source.setGeoJson(geoJsonData)
        tomtomMap.styleSettings.addSource(source)
    }

    private fun addLayersToStyle() {
        val outlineLayerJson = AssetUtils.getAssetFile(context, OUTLINE_LAYER_PATH)
        val outlineLayer = LayerFactory.createLayer(outlineLayerJson)
        tomtomMap.styleSettings.addLayer(outlineLayer)

        val fillLayerJson = AssetUtils.getAssetFile(context, FILL_LAYER_PATH)
        val fillLayer = LayerFactory.createLayer(fillLayerJson)
        tomtomMap.styleSettings.addLayer(fillLayer)
    }

    companion object {
        private const val SOURCE_ID = "IL-test-source"
        private const val SOURCE_PATH = "layers/interactive_layers_data.json"
        private const val OUTLINE_LAYER_PATH = "layers/interactive_layers_outline_layer.json"
        private const val FILL_LAYER_PATH = "layers/interactive_layers_fill_layer.json"
    }
}