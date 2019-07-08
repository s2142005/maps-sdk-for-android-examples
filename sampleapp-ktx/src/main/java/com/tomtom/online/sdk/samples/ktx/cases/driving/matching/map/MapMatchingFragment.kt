/*
 * Copyright (c) 2015-2019 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */

package com.tomtom.online.sdk.samples.ktx.cases.driving.matching.map

import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.driving.DrivingFragment

class MapMatchingFragment : DrivingFragment<MapMatchingViewModel>() {

    override fun routingViewModel(): MapMatchingViewModel = ViewModelProviders.of(this)
        .get(MapMatchingViewModel::class.java)

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation(PROBE_INIT_POSITION, zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_SIMULATION)
        centerOnLocation(PROBE_INIT_POSITION, zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_SIMULATION) // Workaround
        blockZoomGestures()
        createChevron()
    }

    override fun onResume() {
        super.onResume()
        restoreOrCreateChevron()
        enableFollowTheChevron()
        createMatcher()
        viewModel.startOrResumeSimulation()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopSimulation()
        viewModel.disposeMatcher()
        disableFollowTheChevron()
    }

    private fun createMatcher() {
        mainViewModel.applyOnMap(MapAction {
            viewModel.createMatcher(this.asMatchingDataProvider())
        })
    }

}
