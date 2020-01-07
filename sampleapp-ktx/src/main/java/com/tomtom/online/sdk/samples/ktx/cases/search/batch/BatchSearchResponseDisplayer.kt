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
package com.tomtom.online.sdk.samples.ktx.cases.search.batch

import android.graphics.Color
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.CircleBuilder
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.SimpleMarkerBalloon
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.data.batch.BatchSearchResponse
import com.tomtom.online.sdk.search.data.batch.BatchableSearchResponseVisitorAdapter
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchResponse

class BatchSearchResponseDisplayer(private val tomtomMap: TomtomMap) : BatchableSearchResponseVisitorAdapter() {

    fun display(batchSearchResponse: BatchSearchResponse) {
        batchSearchResponse.searchResponses.forEach { response ->
            response.accept(this)
        }
        tomtomMap.zoomToAllMarkers()
    }

    override fun visit(fuzzySearchResponse: FuzzySearchResponse?) {
        fuzzySearchResponse?.results?.forEach { result ->
            addMarker(result.position, result.poi.name)
        }
    }

    override fun visit(geometrySearchResponse: GeometrySearchResponse?) {
        geometrySearchResponse?.results?.forEach { result ->
            addMarker(result.position, result.poi.name)
        }
        addHoofddropGeometry()
    }

    private fun addMarker(position: LatLng, name: String) {
        val markerBuilder = MarkerBuilder(position)
                .markerBalloon(SimpleMarkerBalloon(name))
                .shouldCluster(true)
        tomtomMap.addMarker(markerBuilder)
    }

    private fun addHoofddropGeometry() {
        tomtomMap.overlaySettings.addOverlay(
                CircleBuilder.create()
                        .position(Locations.HOOFDDORP)
                        .radius(GEOMETRY_RADIUS)
                        .color(HOOFDDORP_GEOMETRY_COLOR)
                        .fill(true)
                        .build()
        )
    }

    companion object {
        private const val GEOMETRY_RADIUS = 4000.0
        private val HOOFDDORP_GEOMETRY_COLOR = Color.argb(128, 255, 0, 0)
    }

}
