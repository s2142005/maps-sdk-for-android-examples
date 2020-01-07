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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder

import android.content.Context
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.layer.GeoJsonLayerCreator
import com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.layer.ImageLayerCreator

class DynamicLayerDrawer(context: Context, tomtomMap: TomtomMap) {

    private val imageLayerCreator = ImageLayerCreator(context, tomtomMap)
    private val geoJsonLayerCreator = GeoJsonLayerCreator(tomtomMap)

    fun addGeoJsonLayer(route: FullRoute) {
        geoJsonLayerCreator.addGeoJson(GEOJSON_LAYER_ID, GEOJSON_SOURCE_ID, route)
    }

    fun addImageLayers() {
        imageLayerCreator.addImages(IMAGE_LAYER_ID, IMAGE_SOURCE_ID)
    }

    companion object {
        private const val IMAGE_SOURCE_ID = "image-source-id"
        private const val GEOJSON_SOURCE_ID = "geojson-source-id"
        private const val IMAGE_LAYER_ID = "layer-image-id"
        private const val GEOJSON_LAYER_ID = "layer-line-id"
    }
}