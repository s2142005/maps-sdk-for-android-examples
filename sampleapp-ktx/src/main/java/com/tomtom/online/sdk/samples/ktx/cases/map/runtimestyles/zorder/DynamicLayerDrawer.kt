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