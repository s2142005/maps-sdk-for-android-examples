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

package com.tomtom.online.sdk.samples.ktx.cases.map.markers.advanced

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.Marker
import com.tomtom.online.sdk.map.TomtomMapCallback
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.cases.map.markers.MarkerDrawer
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_map_advanced_markers.*
import java.util.*

class AdvancedMarkersFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_advanced_markers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewActions()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearMarkers()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_register_draggable_marker_listener[]
                tomtomMap.markerSettings.addOnMarkerDragListener(onMarkerDragListener)
                //end::doc_register_draggable_marker_listener[]
            }
        })
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.applyOnMap(MapAction { markerSettings.removeOnMarkerDragListener(onMarkerDragListener) })
    }

    private fun confViewActions() {
        advanced_markers_animated_btn.setOnClickListener {
            clearMarkers()
            centerOnLocation()
            drawAnimatedMarkers()
        }

        advanced_markers_draggable_btn.setOnClickListener {
            clearMarkers()
            centerOnLocation()
            drawDraggableMarkers()
        }
    }

    private fun drawDraggableMarkers() {
        mainViewModel.applyOnMap(MapAction {
            MarkerDrawer(this).createDraggableMarkers(Locations.AMSTERDAM, NUMBER_OF_MARKERS, DISTANCE_BETWEEN_MARKERS)
        })
    }

    private fun drawAnimatedMarkers() {
        context?.let {
            val icon: Icon = Icon.Factory.fromAssets(context, SAMPLE_GIF_ASSET_PATH)

            mainViewModel.applyOnMap(MapAction {
                MarkerDrawer(this).createAnimatedMarkers(Locations.AMSTERDAM, NUMBER_OF_MARKERS, DISTANCE_BETWEEN_MARKERS, icon)
            })
        }
    }

    //tag::doc_create_draggable_marker_listener[]
    private var onMarkerDragListener: TomtomMapCallback.OnMarkerDragListener = object : TomtomMapCallback.OnMarkerDragListener {
        override fun onStartDragging(marker: Marker) {
            displayMessage(R.string.marker_dragging_start_message, marker.position.latitude, marker.position.longitude)
        }

        override fun onStopDragging(marker: Marker) {
            displayMessage(R.string.marker_dragging_end_message, marker.position.latitude, marker.position.longitude)
        }

        override fun onDragging(marker: Marker) {
        }
    }
    //end::doc_create_draggable_marker_listener[]

    private fun displayMessage(titleId: Int, lat: Double, lon: Double) {
        val title = getString(titleId)
        val message = String.format(Locale.getDefault(), TOAST_MESSAGE, title, lat, lon)
        infoTextView.displayAsToast(message)
    }

    private fun clearMarkers() {
        mainViewModel.applyOnMap(MapAction { removeMarkers() })
    }

    companion object {
        private const val NUMBER_OF_MARKERS = 5
        private const val DISTANCE_BETWEEN_MARKERS = 0.2f
        private const val SAMPLE_GIF_ASSET_PATH = "images/racing_car.gif"
        private const val TOAST_MESSAGE = "%s : %f, %f"
    }
}