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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder

import com.tomtom.online.sdk.common.func.FuncUtils
import com.tomtom.online.sdk.map.TomtomMap

class DynamicLayerDisplayer(private val tomtomMap: TomtomMap) {

    private fun moveLayersToFront(regex: String) {
        val layers = tomtomMap.styleSettings.findLayersById(regex)
        FuncUtils.forEach(layers) { layer ->
            //tag::doc_map_move_layer_to_front[]
            //REFERENCE_LAYER = ""
            tomtomMap.styleSettings.moveLayerBehind(layer.id, REFERENCE_LAYER)
            //end::doc_map_move_layer_to_front[]
        }
    }

    fun moveImageLayerToFront() = moveLayersToFront(IMAGE_LAYER_REGEX)

    fun moveGeoJsonLayerToFront() = moveLayersToFront(GEOJSON_LAYER_ID)

    fun moveRoadsLayerToFront() = moveLayersToFront(ROADS_LAYER_REGEX)

    companion object {
        private const val IMAGE_LAYER_REGEX = "layer-image-id[0-9]"
        private const val GEOJSON_LAYER_ID = "layer-line-id"
        private const val ROADS_LAYER_REGEX = ".*[rR]oad.*|.*[mM]otorway.*"
        private const val REFERENCE_LAYER = ""
    }
}