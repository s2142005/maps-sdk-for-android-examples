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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.sources

import android.content.Context
import com.google.common.collect.ImmutableList
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.common.util.AssetUtils
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.map.style.layers.LayerFactory
import com.tomtom.online.sdk.map.style.sources.SourceFactory
import com.tomtom.sdk.examples.R

class DynamicSourcesDrawer(private val context: Context, private val tomtomMap: TomtomMap) {

    fun addCustomGeoJsonToStyle() {
        val geoJsonData = AssetUtils.getAssetFile(context, GEOJSON_DATA_SOURCE_PATH)
        //tag::add_geo_json_source[]
        //GEOJSON_SOURCE_ID = unique_source_id
        //GeoJsonData = JSON representing source data
        val source = SourceFactory.createGeoJsonSource(GEOJSON_SOURCE_ID)
        source.setGeoJson(geoJsonData)
        tomtomMap.styleSettings.addSource(source)
        //end::add_geo_json_source[]

        val layerJson = AssetUtils.getAssetFile(context, GEOJSON_LAYER_JSON_PATH)
        //tag::add_custom_layer[]
        val layer = LayerFactory.createLayer(layerJson)
        tomtomMap.styleSettings.addLayer(layer)
        //end::add_custom_layer[]
    }

    fun addCustomImageToStyle() {
        //tag::add_image_source[]
        //IMAGE_SOURCE_ID = unique_source_id
        //IMAGE_CORNERS = Four corners of the image (top left, top right, bottom right, bottom left)
        val source = SourceFactory.createImageSource(IMAGE_SOURCE_ID, IMAGE_CORNERS)
        source.setImage(context.resources.getDrawable(R.drawable.buckingham_palace_plan))
        tomtomMap.styleSettings.addSource(source)
        //end::add_image_source[]

        val layerJson = AssetUtils.getAssetFile(context, IMAGE_LAYER_JSON_PATH)
        val layer = LayerFactory.createLayer(layerJson)
        tomtomMap.styleSettings.addLayer(layer)
    }

    companion object {
        private const val IMAGE_SOURCE_ID = "image-source-id"
        private const val IMAGE_LAYER_JSON_PATH = "layers/raster_layer.json"
        private const val GEOJSON_SOURCE_ID = "geojson-source-id"
        private const val GEOJSON_DATA_SOURCE_PATH = "layers/geojson_data.json"
        private const val GEOJSON_LAYER_JSON_PATH = "layers/fill_layer.json"
        private val IMAGE_CORNERS = ImmutableList.of(
            LatLng(51.502088, -0.142457), //top left
            LatLng(51.500520, -0.140731), //top right
            LatLng(51.499731, -0.142518), //bottom right
            LatLng(51.501392, -0.144229) //bottom left
        )
    }

}