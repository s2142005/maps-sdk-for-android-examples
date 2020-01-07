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

package com.tomtom.online.sdk.samples.ktx.cases.search

import android.graphics.Color
import com.tomtom.online.sdk.common.func.FuncUtils
import com.tomtom.online.sdk.common.geojson.Feature
import com.tomtom.online.sdk.common.geojson.FeatureCollection
import com.tomtom.online.sdk.common.geojson.GeoJsonObjectVisitorAdapter
import com.tomtom.online.sdk.common.geojson.geometry.Geometry
import com.tomtom.online.sdk.common.geojson.geometry.MultiPolygon
import com.tomtom.online.sdk.common.geojson.geometry.Polygon
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.PolygonBuilder
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.search.data.additionaldata.result.AdditionalDataSearchResult
import java.util.*

class AdpResultsDrawer(private val tomtomMap: TomtomMap) {

    fun draw(adpResults: List<AdditionalDataSearchResult>) {
        adpResults.forEach { result -> parseAdpResult(result) }
    }

    private fun parseAdpResult(adpResult: AdditionalDataSearchResult) {
        adpResult.accept { geometry ->
            geometry.geometryData.orNull()?.accept(object : GeoJsonObjectVisitorAdapter() {
                override fun visit(featureCollection: FeatureCollection?) {
                    this@AdpResultsDrawer.parseFeatureCollection(featureCollection!!)
                }
            })
        }
    }

    private fun parseFeatureCollection(featureCollection: FeatureCollection) {
        featureCollection.features.firstOrNull()?.let { parseFeature(it) }
    }

    private fun parseFeature(feature: Feature) {
        //tag::doc_reverse_geocoding_polygon_display[]
        FuncUtils.apply<Geometry>(feature.geometry) { input ->
            input.accept(object : GeoJsonObjectVisitorAdapter() {
                override fun visit(polygon: Polygon?) {
                    polygon?.let { item ->
                        displayPolygon(item)
                        tomtomMap.setCurrentBounds(parsePolygonLatLngs(item))
                    }
                }

                override fun visit(multiPolygon: MultiPolygon?) {
                    val coordinates = ArrayList<LatLng>()
                    multiPolygon?.polygons?.forEach { polygon ->
                        coordinates.addAll(parsePolygonLatLngs(polygon))
                        displayPolygon(polygon)
                    }
                    tomtomMap.setCurrentBounds(coordinates)
                }
            })
        }
        //end::doc_reverse_geocoding_polygon_display[]
    }

    private fun parsePolygonLatLngs(polygon: Polygon): List<LatLng> {
        return polygon.exteriorRing.coordinates.asList()
    }

    private fun displayPolygon(polygon: Polygon) {
        val coordinates = polygon.exteriorRing.coordinates.asList()
        val overlay = PolygonBuilder.create()
            .coordinates(coordinates)
            .color(Color.RED)
            .opacity(COLOR_OPACITY)
            .build()
        tomtomMap.overlaySettings.addOverlay(overlay)
    }

    companion object {
        const val COLOR_OPACITY = 0.7f
    }

}