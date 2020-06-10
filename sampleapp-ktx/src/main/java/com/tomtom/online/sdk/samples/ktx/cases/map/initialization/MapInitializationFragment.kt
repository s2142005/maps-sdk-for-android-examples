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
package com.tomtom.online.sdk.samples.ktx.cases.map.initialization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_initialization.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class MapInitializationFragment : ExampleFragment() {
    private lateinit var mapFragment: MapFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_initialization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
        initMapWithBoundingBox()
    }

    override fun onExampleStarted() {
        mainViewModel.applyMapFragmentVisibility(false)
    }

    override fun onExampleEnded() {
        mainViewModel.applyMapFragmentVisibility(true)
        mainViewModel.applyOnMap(MapAction { clear() })
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_initialization, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        map_initialization_bounding_box_btn.setOnClickListener {
            initMapWithBoundingBox()
        }

        map_initialization_starting_point_btn.setOnClickListener {
            initMapWithStartingPoint()
        }
    }

    private fun initMapWithStartingPoint() {
        //tag::doc_map_initialization_point[]
        val tomtomOfficeLodz = LatLng(51.759434, 19.449011)
        val cameraPosition = CameraPosition.builder()
            .focusPosition(tomtomOfficeLodz)
            .zoom(MapConstants.DEFAULT_ZOOM_LEVEL)
            .pitch(5.0)
            .bearing(MapConstants.ORIENTATION_NORTH.toDouble())
            .build()
        val mapProperties = MapProperties.Builder()
            .cameraPosition(cameraPosition)
            .build()
        mapFragment = MapFragment.newInstance(mapProperties)
        //end::doc_map_initialization_point[]
        replaceMapFragment(mapFragment)
    }

    private fun replaceMapFragment(mapFragment: MapFragment) {
        requireFragmentManager()
            .beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()
    }

    private fun initMapWithBoundingBox() {
        //tag::doc_map_initialization_bounding_box[]
        val amsterdamTopLeft = LatLng(52.499782, 4.749553)
        val amsterdamBottomRight = LatLng(52.246161, 5.031764)
        val boundingBox = BoundingBox(amsterdamTopLeft, amsterdamBottomRight)
        val focusArea: CameraFocusArea = CameraFocusArea.Builder(boundingBox)
            .pitch(5.0)
            .bearing(MapConstants.ORIENTATION_NORTH.toDouble())
            .build()
        val mapProperties = MapProperties.Builder()
            .cameraFocusArea(focusArea)
            .build()
        mapFragment = MapFragment.newInstance(mapProperties)
        //end::doc_map_initialization_bounding_box[]
        replaceMapFragment(mapFragment)
    }
}