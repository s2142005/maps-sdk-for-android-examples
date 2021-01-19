/**
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.driving.zoom

import android.location.Location
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.CameraPosition
import com.tomtom.online.sdk.map.Chevron
import com.tomtom.online.sdk.map.ChevronBuilder
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.map.camera.OnMapCenteredCallback
import com.tomtom.online.sdk.routing.route.information.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.driving.DrivingFragment
import com.tomtom.online.sdk.samples.ktx.cases.driving.tracking.DEFAULT_ROUTE
import com.tomtom.online.sdk.samples.ktx.utils.driving.ChevronSimulatorUpdater
import com.tomtom.sdk.examples.R

class DrivingZoomLevelFragment : DrivingFragment<DrivingZoomLevelViewModel>() {

    private lateinit var chevronSimulatorUpdater: ChevronSimulatorUpdater
    private var isZooming: Boolean = false

    override fun routingViewModel(): DrivingZoomLevelViewModel =
        ViewModelProviders.of(this).get(DrivingZoomLevelViewModel::class.java)

    override fun onExampleStarted() {
        super.onExampleStarted()
        viewModel.planDefaultRoute()
        centerOnLocation(DEFAULT_ROUTE.origin, zoomLevel = EXAMPLE_START_ZOOM_LEVEL)
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        mainViewModel.applyOnMap(MapAction { drivingSettings.stopTracking() })
    }

    override fun displayRoutingResults(routes: List<FullRoute>) {
        super.displayRoutingResults(routes)
        viewModel.createSimulator(routes)
        setupChevronAndSimulation()
        viewModel.startSimulation(chevronSimulatorUpdater)
    }

    private fun setupChevronAndSimulation() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                createChevron(tomtomMap)
                setupSimulator()
                tomtomMap.drivingSettings.startTracking(chevron)
            }
        })
    }

    private fun setupSimulator() {
        chevronSimulatorUpdater = ChevronUpdater(chevron)
        viewModel.startSimulation(chevronSimulatorUpdater)
    }

    private fun createChevron(tomtomMap: TomtomMap) {
        val activeIcon = Icon.Factory.fromResources(requireContext(), R.drawable.chevron_color)
        val inactiveIcon = Icon.Factory.fromResources(requireContext(), R.drawable.chevron_shadow)
        val chevronBuilder = ChevronBuilder.create(activeIcon, inactiveIcon)
        chevron = tomtomMap.drivingSettings.addChevron(chevronBuilder)
    }

    @Suppress("UNUSED")
    //tag::doc_update_zoom_level[]
    fun setupLocationUpdateListener(tomtomMap: TomtomMap) {
        tomtomMap.addLocationUpdateListener { location ->
            updateZoomLevelBaseOnNewLocation(tomtomMap, location)
        }
    }

    private fun updateZoomLevelBaseOnNewLocation(tomtomMap: TomtomMap, location: Location) {
        val zoomLevel = when (location.speed) {
            in SMALL_SPEED_RANGE_IN_KMH -> SMALL_SPEED_ZOOM_LEVEL
            in MEDIUM_SPEED_RANGE_IN_KMH -> MEDIUM_SPEED_ZOOM_LEVEL
            in GREATER_SPEED_RANGE_IN_KMH -> GREATER_SPEED_ZOOM_LEVEL
            in BIG_SPEED_RANGE_IN_KMH -> BIG_SPEED_ZOOM_LEVEL
            in HUGE_SPEED_RANGE_IN_KMH -> HUGE_SPEED_ZOOM_LEVEL
            else -> tomtomMap.zoomLevel
        }
        if (tomtomMap.zoomLevel != zoomLevel) {
            setCameraPosition(tomtomMap, zoomLevel = zoomLevel)
        }
    }

    private fun setCameraPosition(tomtomMap: TomtomMap, zoomLevel: Double) {
        if (!isZooming) {
            isZooming = true
            tomtomMap.centerOn(
                CameraPosition.builder()
                    .zoom(zoomLevel)
                    .animationDuration(ZOOM_CHANGE_ANIMATION_MILLIS)
                    .build(),
                onMapCenteredCallback
            )
        }
    }

    private val onMapCenteredCallback = object : OnMapCenteredCallback {
        override fun onFinish() {
            isZooming = false
        }

        override fun onCancel() {
            isZooming = false
        }
    }
    //end::doc_update_zoom_level[]

    inner class ChevronUpdater(chevron: Chevron) : ChevronSimulatorUpdater(chevron) {
        override fun onNewRoutePointVisited(location: Location) {
            super.onNewRoutePointVisited(location)
            mainViewModel.applyOnMap(MapAction {
                updateZoomLevelBaseOnNewLocation(this, location)
            })
        }
    }

    companion object {
        //tag::doc_used_constants[]
        private const val EXAMPLE_START_ZOOM_LEVEL = 19.0
        private const val ZOOM_CHANGE_ANIMATION_MILLIS = 300
        private const val SMALL_SPEED_ZOOM_LEVEL = 19.0
        private const val MEDIUM_SPEED_ZOOM_LEVEL = 18.0
        private const val GREATER_SPEED_ZOOM_LEVEL = 17.0
        private const val BIG_SPEED_ZOOM_LEVEL = 16.0
        private const val HUGE_SPEED_ZOOM_LEVEL = 14.0
        private val SMALL_SPEED_RANGE_IN_KMH = 0.0..10.0
        private val MEDIUM_SPEED_RANGE_IN_KMH = 10.0..20.0
        private val GREATER_SPEED_RANGE_IN_KMH = 20.0..40.0
        private val BIG_SPEED_RANGE_IN_KMH = 40.0..70.0
        private val HUGE_SPEED_RANGE_IN_KMH = 70.0..120.0
        //end::doc_used_constants[]
    }
}