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

package com.tomtom.online.sdk.samples.ktx.cases.driving.matching.route

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.driving.LatLngTraceMatchingDataProvider
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.driving.DrivingFragment

class RouteMatchingFragment : DrivingFragment<RouteMatchingViewModel>() {

    override fun routingViewModel(): RouteMatchingViewModel = ViewModelProviders.of(this)
        .get(RouteMatchingViewModel::class.java)

    override fun onExampleStarted() {
        super.onExampleStarted()
        observeRoutesToCenterOnFirstLocation()
        blockZoomGestures()
        planDefaultRoute()
    }

    override fun onResume() {
        super.onResume()
        observeRoutesToSimulateAndMatch()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopSimulation()
        viewModel.disposeMatcher()
        disableFollowTheChevron()
    }

    private fun planDefaultRoute() {
        viewModel.planDefaultRoute()
    }

    private fun observeRoutesToCenterOnFirstLocation() {
        viewModel.routes.observe(this, Observer { fullRoutes ->
            fullRoutes?.let {
                it.data?.first()?.let { firstFullRoute ->
                    centerOnLocation(firstFullRoute.coordinates.first(), zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_SIMULATION)
                }
            }
        })
    }

    private fun observeRoutesToSimulateAndMatch() {
        viewModel.routes.observe(this, Observer {
            it.data?.let { fullRoutes ->
                restoreOrCreateChevron()
                enableFollowTheChevron()
                createMatcher(fullRoutes)
                startSimulation(fullRoutes)
            }
        })
    }

    private fun startSimulation(fullRoutes: List<FullRoute>) {
        mainViewModel.applyOnMap(MapAction {
            viewModel.startSimulation(fullRoutes)
        })
    }

    private fun createMatcher(routes: List<FullRoute>) {
        viewModel.createMatcher(LatLngTraceMatchingDataProvider.fromPoints(routes.first().coordinates))
    }

}
