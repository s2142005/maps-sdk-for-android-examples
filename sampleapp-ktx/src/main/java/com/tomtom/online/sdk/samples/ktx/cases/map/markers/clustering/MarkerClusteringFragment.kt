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

package com.tomtom.online.sdk.samples.ktx.cases.map.markers.clustering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.cases.map.markers.MarkerDrawer
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R

class MarkerClusteringFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        drawMarkers()
        zoomToMarkers()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearMarkers()
    }

    private fun drawMarkers() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_enable_markers_clustering[]
                tomtomMap.markerSettings.setMarkersClustering(true)
                //end::doc_enable_markers_clustering[]
            }

            val markerDrawer = MarkerDrawer(this)
            markerDrawer.createMarkersWithClustering(
                    Locations.AMSTERDAM,
                    NUMBER_OF_MARKERS_AMSTERDAM,
                    MARKER_OFFSET_MULTIPLIER_AMSTERDAM)
            markerDrawer.createMarkersWithClustering(
                    Locations.ROTTERDAM,
                    NUMBER_OF_MARKERS_ROTTERDAM,
                    MARKER_OFFSET_MULTIPLIER_ROTTERDAM)
        })
    }

    private fun clearMarkers() {
        mainViewModel.applyOnMap(MapAction {
            markerSettings.setMarkersClustering(false)
            markerSettings.removeMarkers()
        })
    }

    private fun zoomToMarkers() {
        mainViewModel.applyOnMap(MapAction { zoomToAllMarkers() })
    }

    companion object {
        const val NUMBER_OF_MARKERS_AMSTERDAM = 90
        const val NUMBER_OF_MARKERS_ROTTERDAM = 150
        const val MARKER_OFFSET_MULTIPLIER_AMSTERDAM = 0.5f
        const val MARKER_OFFSET_MULTIPLIER_ROTTERDAM = 0.1f
    }
}