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

package com.tomtom.online.sdk.samples.ktx.cases.map.markers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.MapConstants
import com.tomtom.online.sdk.map.TomtomMapCallback
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_markers.*
import kotlinx.android.synthetic.main.default_map_fragment.*
import timber.log.Timber

class MarkersFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation(bearing = MapConstants.ORIENTATION_NORTH)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_register_marker_listener[]
                tomtomMap.addOnMarkerClickListener(markerListener)
                //end::doc_register_marker_listener[]
            }
        })
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.applyOnMap(MapAction { removeOnMarkerClickListeners() })
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearMarkers()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_markers, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        markers_simple_btn.setOnClickListener {
            clearMarkers()
            centerOnLocation(bearing = MapConstants.ORIENTATION_NORTH)
            drawSimpleMarkers()
        }

        markers_decal_btn.setOnClickListener {
            clearMarkers()
            centerOnLocation(bearing = MapConstants.ORIENTATION_SOUTH)
            drawDecalMarkers()
        }
    }

    private fun drawSimpleMarkers() {
        mainViewModel.applyOnMap(MapAction {
            MarkerDrawer(this).createSimpleMarkers(Locations.AMSTERDAM, NUMBER_OF_MARKERS, DISTANCE_BETWEEN_MARKERS)
        })
    }

    private fun drawDecalMarkers() {
        context?.let {
            val icon: Icon = Icon.Factory.fromResources(it, R.drawable.ic_favourites)

            mainViewModel.applyOnMap(MapAction {
                MarkerDrawer(this).createDecalMarkers(Locations.AMSTERDAM, NUMBER_OF_MARKERS, DISTANCE_BETWEEN_MARKERS, icon)
            })
        }
    }

    private fun clearMarkers() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_remove_all_markers[]
                tomtomMap.removeMarkers()
                //end::doc_remove_all_markers[]

                //tag::doc_remove_tag_markers[]
                tomtomMap.removeMarkerByTag("tag")
                //end::doc_remove_tag_markers[]

                //tag::doc_remove_id_markers[]
                tomtomMap.removeMarkerByID(1)
                //end::doc_remove_id_markers[]
            }
        })
    }

    private val markerListener = TomtomMapCallback.OnMarkerClickListener { m ->
        Timber.d("marker selected %s", m.tag)
    }

    companion object {
        private const val NUMBER_OF_MARKERS = 5
        private const val DISTANCE_BETWEEN_MARKERS = 0.2f
    }
}