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

package com.tomtom.online.sdk.samples.ktx.cases.driving

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.tomtom.core.maps.gestures.GesturesDetectionSettings
import com.tomtom.core.maps.gestures.GesturesDetectionSettingsBuilder
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.sdk.examples.R

abstract class DrivingFragment<T : DrivingViewModel> : ExampleFragment() {

    protected lateinit var chevron: Chevron

    protected lateinit var viewModel: T

    abstract fun routingViewModel(): T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_driving_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.chevronState.observe(this@DrivingFragment, Observer { matchedResult ->
            processMatchedResult(matchedResult)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewModel()
    }

    override fun onResume() {
        super.onResume()
        confCompass()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        removeRoutes()
        removeChevrons()
        resetCompassMargins()
        resetZoomGestures()
    }

    private fun confViewModel() {
        viewModel = routingViewModel()
        viewModel.routes.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::processRoutingResults,
            onError = ::showError))
        viewModel.selectedRoute.observe(this, Observer { highlightSelectedRoute() })
    }

    private fun confCompass() {
        mainViewModel.adjustCompassTopMarginForInfoBar()
    }

    private fun processRoutingResults(routes: List<FullRoute>) {
        displayRoutingResults(routes)
    }

    open fun displayRoutingResults(routes: List<FullRoute>) {
        mainViewModel.applyOnMap(MapAction {
            clear()
            drawRoutes(this, routes)
            displayRoutesOverview()
            selectRoute()
        })
    }

    open fun selectRoute() {
        viewModel.selectedRoute.value?.let { route ->
            viewModel.selectRouteIdx(route.second)
        } ?: run {
            viewModel.selectRouteIdx(DEFAULT_ROUTE_IDX)
        }
    }

    private fun highlightSelectedRoute() {
        viewModel.selectedRoute.value?.let { route ->
            drawSelectedRouteByIdx(route.second)
        }
    }

    open fun drawRoutes(tomtomMap: TomtomMap, routes: List<FullRoute>) {
        context?.let { ctx ->
            RouteDrawer(ctx, tomtomMap).drawDefault(routes)
        }
    }

    open fun drawSelectedRouteByIdx(idx: Int) {
        context?.let { ctx ->
            mainViewModel.applyOnMap(MapAction {
                val routeDrawer = RouteDrawer(ctx, this)
                routeDrawer.setAllAsInactive()
                routeDrawer.setActiveByIdx(idx)
            })
        }
    }

    private fun removeRoutes() {
        mainViewModel.applyOnMap(MapAction { routeSettings.clearRoute() })
    }

    private fun resetCompassMargins() {
        mainViewModel.resetCompassMargins()
    }

    private fun removeChevrons() {
        mainViewModel.applyOnMap(MapAction { drivingSettings.removeChevrons() })
    }

    private fun processMatchedResult(it: ChevronMatchedStateUpdate) {
        //tag::doc_process_matcher_result[]
        val chevronPosition = ChevronPosition.Builder(it.matchedLocation).build()
        chevron.isDimmed = it.isDimmed
        chevron.position = chevronPosition
        chevron.show()
        showOriginalLocationDot(it)
        //end::doc_process_matcher_result[]
    }

    private fun showOriginalLocationDot(it: ChevronMatchedStateUpdate) {
        mainViewModel.applyOnMap(MapAction {
            overlaySettings.removeOverlays()
            overlaySettings.addOverlay(
                CircleBuilder.create()
                    .position(LatLng(it.originalLocation))
                    .fill(GPS_DOT_FILL)
                    .color(GPS_DOT_COLOR)
                    .radius(GPS_DOT_RADIUS)
                    .build())
        })
    }

    protected fun restoreOrCreateChevron() {
        mainViewModel.applyOnMap(MapAction {
            if (drivingSettings.chevrons.isNotEmpty()) {
                chevron = drivingSettings.chevrons.first()
            } else {
                createChevron()
            }
        })
    }

    protected fun createChevron() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                val activeIcon = Icon.Factory.fromResources(requireContext(), R.drawable.chevron_color, CHEVRON_ICON_SCALE)
                val inactiveIcon = Icon.Factory.fromResources(requireContext(), R.drawable.chevron_shadow, CHEVRON_ICON_SCALE)
                //tag::doc_create_chevron[]
                val chevronBuilder = ChevronBuilder.create(activeIcon, inactiveIcon)
                chevron = tomtomMap.drivingSettings.addChevron(chevronBuilder)
                //end::doc_create_chevron[]
            }
        })
    }

    protected fun enableFollowTheChevron() {
        mainViewModel.applyOnMap(MapAction {
            drivingSettings.startTracking(chevron)
            drivingSettings.enableMapBearingSmoothing(DEFAULT_MAP_BEARING_SMOOTHING)
        })
    }

    protected fun disableFollowTheChevron() {
        mainViewModel.applyOnMap(MapAction {
            drivingSettings.stopTracking()
            drivingSettings.disableMapBearingSmoothing()
        })
    }

    protected fun blockZoomGestures() {
        mainViewModel.applyOnMap(MapAction {
            updateGesturesDetectionSettings(GesturesDetectionSettingsBuilder
                .create()
                .minZoom(MIN_ZOOM_LEVEL_FOR_SIMULATION)
                .maxZoom(MAX_ZOOM_LEVEL_FOR_SIMULATION)
                .build())
        })
    }

    protected fun resetZoomGestures() {
        mainViewModel.applyOnMap(MapAction {
            updateGesturesDetectionSettings(GesturesDetectionSettings.createDefault())
        })
    }

    companion object {

        val PROBE_INIT_POSITION = LatLng(51.745516, 19.438692)
        const val DEFAULT_ZOOM_LEVEL_FOR_SIMULATION = 16.0
        const val MIN_ZOOM_LEVEL_FOR_SIMULATION = 16.5
        const val MAX_ZOOM_LEVEL_FOR_SIMULATION = 17.5
        const val DEFAULT_MAP_BEARING_SMOOTHING: Long = 1
        const val DEFAULT_ROUTE_IDX = 0

        private const val CHEVRON_ICON_SCALE = 2.5
        private const val GPS_DOT_COLOR = Color.RED
        private const val GPS_DOT_RADIUS = 3.0
        private const val GPS_DOT_FILL = true
    }

}