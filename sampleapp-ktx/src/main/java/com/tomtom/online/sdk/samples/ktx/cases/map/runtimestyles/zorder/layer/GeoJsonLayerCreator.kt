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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.layer

import android.graphics.Color
import com.google.common.collect.ImmutableList
import com.tomtom.online.sdk.common.geojson.Feature
import com.tomtom.online.sdk.common.geojson.FeatureCollection
import com.tomtom.online.sdk.common.geojson.geometry.LineString
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.map.style.layers.LayerFactory
import com.tomtom.online.sdk.map.style.sources.SourceFactory
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.DynamicLayerType
import com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.json.JsonLayerCreator
import com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.json.JsonLayerDescriptor

class GeoJsonLayerCreator(private val tomtomMap: TomtomMap) {

    fun addGeoJson(layerId: String, sourceId: String, route: FullRoute) {
        addGeoJsonSource(route, sourceId)
        addGeoJsonLayer(layerId, sourceId)
    }

    private fun addGeoJsonLayer(layerId: String, sourceId: String) {
        val geoJsonLayerDescriptor = JsonLayerDescriptor(
            layerId = layerId,
            sourceId = sourceId,
            type = DynamicLayerType.Line,
            lineColor = DEFAULT_LINE_COLOR,
            lineWidth = DEFAULT_LINE_WIDTH)

        val jsonLayer = JsonLayerCreator.fromDescriptor(geoJsonLayerDescriptor)
        val layer = LayerFactory.createLayer(jsonLayer)
        tomtomMap.styleSettings.addLayer(layer)
    }

    private fun addGeoJsonSource(route: FullRoute, sourceId: String) {
        val lineString = LineString.builder()
            .coordinates(route.coordinates)
            .build()

        val feature = Feature.builder()
            .geometry(lineString)
            .build()

        val featureCollection = FeatureCollection.builder()
            .features(ImmutableList.of(feature))
            .build()

        val source = SourceFactory.createGeoJsonSource(sourceId)
        source.setGeoJson(featureCollection)

        tomtomMap.styleSettings.addSource(source)
    }

    companion object {
        private const val DEFAULT_LINE_WIDTH = 6
        private val DEFAULT_LINE_COLOR = Color.argb(1, 26, 203, 207)
    }
}