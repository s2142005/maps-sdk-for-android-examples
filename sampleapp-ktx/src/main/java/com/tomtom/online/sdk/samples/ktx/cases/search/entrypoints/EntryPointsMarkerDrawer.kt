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
package com.tomtom.online.sdk.samples.ktx.cases.search.entrypoints

import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.SimpleMarkerBalloon
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchDetails
import com.tomtom.online.sdk.search.information.EntryPoint

internal class EntryPointsMarkerDrawer(private val tomtomMap: TomtomMap, private val markerBalloonText: String) {

    private fun addMarker(fuzzySearchDetails: FuzzySearchDetails) {
        tomtomMap.addMarker(
            MarkerBuilder(fuzzySearchDetails.position)
                .markerBalloon(SimpleMarkerBalloon(fuzzySearchDetails.poi?.name))
        )
    }

    //tag::doc_entry_points_add_marker[]
    private fun addMarkerWithIcon(entryPoint: EntryPoint, balloon: SimpleMarkerBalloon, icon: Icon) {
        tomtomMap.addMarker(
            MarkerBuilder(entryPoint.position)
                .markerBalloon(balloon)
                .icon(icon)
        )
    }
    //end::doc_entry_points_add_marker[]

    fun handleResultsFromFuzzy(fuzzySearchDetails: FuzzySearchDetails, icon: Icon) {
        tomtomMap.markerSettings.removeMarkers()
        addMarker(fuzzySearchDetails)

        //tag::doc_entry_points_search_request[]
        fuzzySearchDetails.entryPoints.forEach { entryPoint ->
            val markerBalloon = SimpleMarkerBalloon(
                String.format(markerBalloonText, entryPoint.type.toString().toLowerCase())
            )
            addMarkerWithIcon(entryPoint, markerBalloon, icon)
        }
        //end::doc_entry_points_search_request[]
        tomtomMap.markerSettings.zoomToAllMarkers()
    }
}