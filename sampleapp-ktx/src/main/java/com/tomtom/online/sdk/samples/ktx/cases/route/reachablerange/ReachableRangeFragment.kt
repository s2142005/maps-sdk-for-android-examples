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

package com.tomtom.online.sdk.samples.ktx.cases.route.reachablerange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.data.reachablerange.ReachableRangeResponse
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.MapConstants
import com.tomtom.online.sdk.routing.ReachableRangeResultListener
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_reachable_range.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class ReachableRangeFragment : ExampleFragment() {

    lateinit var viewModel: ReachableRangeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewModel()
        confViewActions()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation(location = Locations.AMSTERDAM_CENTER, zoomLevel = MapConstants.DEFAULT_ZOOM_LEVEL)
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearExample()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_route_reachable_range, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        reachable_range_combustion_btn.setOnClickListener { viewModel.planReachableRangeForCombustion() }
        reachable_range_electric_btn.setOnClickListener { viewModel.planReachableRangeForElectric() }
        reachable_range_time_btn.setOnClickListener { viewModel.planReachableRangeForElectricWithLimitedTime() }
    }

    private fun confViewModel() {
        viewModel = ViewModelProviders.of(this).get(ReachableRangeViewModel::class.java)
        viewModel.reachableResponse.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::processReachableRangeResults,
            onError = ::showError))
    }

    private fun processReachableRangeResults(reachableRangeResponse: ReachableRangeResponse) {
        val coordinatesList = reachableRangeResponse.result.boundary
        drawReachableRange(coordinatesList)
        centerOnBoundingBox(coordinatesList)
    }

    private fun drawReachableRange(coordinatesList: Array<LatLng>) {
        mainViewModel.applyOnMap(MapAction {
            //remove old results
            clear()

            val reachableRangeDrawer = ReachableRangeDrawer(this)
            reachableRangeDrawer.drawPolygonForReachableRange(coordinatesList)
            reachableRangeDrawer.drawCenterMarker(createIcon())
        })
    }

    private fun createIcon(): Icon {
        return Icon.Factory.fromResources(requireContext(), R.drawable.ic_favourites)
    }

    private fun clearExample() {
        mainViewModel.applyOnMap(MapAction {
            overlaySettings.removeOverlays()
            markerSettings.removeMarkers()
        })
    }

    private fun centerOnBoundingBox(coordinatesList: Array<LatLng>) {
        val boundingBox = BoundingBox.fromCoordinates(coordinatesList.toList())

        mainViewModel.applyOnMap(MapAction {
            val zoomLevel = getZoomLevelForBounds(boundingBox.topLeft, boundingBox.bottomRight)
            centerOnLocation(boundingBox.center, zoomLevel)
        })
    }

    @Suppress("unused")
    //tag::doc_reachable_range_result_listener[]
    private val reachableRangeResultListener = object : ReachableRangeResultListener {
        override fun onReachableRangeResponse(reachableRangeResponse: ReachableRangeResponse) {
            processReachableRangeResults(reachableRangeResponse)
        }

        override fun onReachableRangeError(error: Throwable) {
            Resource.error(null, Error(error.message))
        }
    }
    //end::doc_reachable_range_result_listener[]

}
