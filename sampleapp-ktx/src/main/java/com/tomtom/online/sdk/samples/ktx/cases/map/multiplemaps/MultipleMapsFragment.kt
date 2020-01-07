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

package com.tomtom.online.sdk.samples.ktx.cases.map.multiplemaps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomtom.core.maps.MapChangedListenerAdapter
import com.tomtom.core.maps.gestures.GesturesDetectionSettingsBuilder
import com.tomtom.online.sdk.map.CameraPosition
import com.tomtom.online.sdk.map.MapFragment
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R

class MultipleMapsFragment : ExampleFragment() {

    private lateinit var miniMapFragment: MapFragment
    private lateinit var viewModel: MultipleMapsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_multiple_maps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initMiniMap()
        confViewModel()
        confMiniMap()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation(location = Locations.AMSTERDAM_CENTER, zoomLevel = DEFAULT_MAP_ZOOM_LEVEL_FOR_EXAMPLE)
    }

    override fun onResume() {
        super.onResume()
        registerListener()
    }

    override fun onPause() {
        super.onPause()
        unregisterListener()
    }

    private fun confViewModel() {
        viewModel = ViewModelProviders.of(this).get(MultipleMapsViewModel::class.java)
        viewModel.miniMapAction().observe(this, Observer { action ->
            miniMapFragment.getAsyncMap { tomtomMap -> run { action.invoke(tomtomMap) } }
        })
    }

    private fun initMiniMap() {
        miniMapFragment = childFragmentManager.findFragmentById(R.id.mini_map_fragment) as MapFragment
        //Make sure that mini map is drawn on top of the main map
        //If not set, the mini map may be invisible on old devices
        //With low Android version
        miniMapFragment.setZOrderMediaOverlay(true)
    }

    private fun confMiniMap() {
        viewModel.applyOnMiniMap(MapAction {
            uiSettings.compassView.hide()
            uiSettings.currentLocationView.hide()
            uiSettings.setStyleUrl(NIGHT_STYLE_URL_PATH)
            logoSettings.applyInvertedLogo()

            updateGesturesDetectionSettings(GesturesDetectionSettingsBuilder.create()
                    .zoomEnabled(false)
                    .panningEnabled(false)
                    .rotationEnabled(false)
                    .tiltEnabled(false)
                    .build())
        })
    }

    private fun registerListener() {
        mainViewModel.applyOnMap(MapAction { addOnMapChangedListener(onMapChanged) })
    }

    private fun unregisterListener() {
        mainViewModel.applyOnMap(MapAction { removeOnMapChangedListener(onMapChanged) })
    }

    private val onMapChanged = object : MapChangedListenerAdapter() {
        override fun onCameraDidChange() {

            //This callback is not called too often, only when animation / map move is finished
            //To have more frequent updates, one can register for onMapViewPortChanged
            //However, this may cause performance issues as onMapViewPortChanged
            //Is called very often.

            mainViewModel.applyOnMap(MapAction {

                val cameraPosition = uiSettings.cameraPosition
                val miniMapZoomLevel = if (cameraPosition.zoom <= MAP_ZOOM_LEVEL_FOR_SECOND_MAP) {
                    cameraPosition.zoom
                } else {
                    cameraPosition.zoom - MAP_ZOOM_LEVEL_FOR_SECOND_MAP
                }

                val miniMapBearing = cameraPosition.bearing

                val miniMapPosition = CameraPosition.builder(centerOfMap)
                        .zoom(miniMapZoomLevel)
                        .bearing(miniMapBearing)
                        .animationDuration(SECOND_MAP_ANIMATION_TIME)
                        .build()

                viewModel.applyOnMiniMap(MapAction {
                    uiSettings.cameraPosition = miniMapPosition
                })
            })
        }
    }

    companion object {
        private const val NIGHT_STYLE_URL_PATH = "asset://styles/night.json"
        private const val SECOND_MAP_ANIMATION_TIME = 0
        private const val MAP_ZOOM_LEVEL_FOR_SECOND_MAP = 4
        private const val DEFAULT_MAP_ZOOM_LEVEL_FOR_EXAMPLE = 12.0
    }
}
