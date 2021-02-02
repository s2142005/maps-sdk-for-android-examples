/*
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.search.evstations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.MapConstants
import com.tomtom.online.sdk.map.TextBalloonViewAdapter
import com.tomtom.online.sdk.routing.route.RoutePlan
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_ev_stations_search.*
import kotlinx.android.synthetic.main.fragment_search_with_map.*

class EvStationsFragment : SearchFragment<EvStationsViewModel>() {

    override fun searchViewModel(): EvStationsViewModel =
        ViewModelProviders.of(this).get(EvStationsViewModel::class.java)

    override fun setupSearchView() {
    }

    override fun setupSearchTypeSelector() {
        layoutInflater.inflate(R.layout.control_buttons_ev_stations_search, controlButtonsContainer, true)
        ev_stations_search_along_route_btn.setOnClickListener {
            clearMap()
            viewModel.searchAlongRoute()
        }
        ev_stations_search_fuzzy_btn.setOnClickListener {
            clearMap()
            viewModel.searchFuzzyInBoundingBox()
        }
        ev_stations_search_geometry_btn.setOnClickListener {
            clearMap()
            viewModel.searchGeometry()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_with_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeForEvStations()
        observeForRoute()
        observeForGeometries()
    }

    private fun observeForGeometries() {
        viewModel.geometryOnMap.observe(viewLifecycleOwner, Observer { polygon ->
            mainViewModel.applyOnMap(MapAction {
                overlaySettings.addOverlay(polygon)
            })
        })
    }

    private fun observeForRoute() {
        viewModel.routingResults.observe(
            viewLifecycleOwner, ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::displayRoute,
                onError = ::showError
            )
        )
    }

    private fun displayRoute(routePlan: RoutePlan) {
        mainViewModel.applyOnMap(MapAction {
            RouteDrawer(requireContext(), this).drawDefault(listOf(routePlan.routes.first()))
            displayRoutesOverview()
        })
    }

    private fun observeForEvStations() {
        viewModel.chargingStationsResults.observe(
            viewLifecycleOwner, ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::displayResults,
                onError = ::showError
            )
        )
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnArea(Locations.AMSTERDAM_TOP_LEFT, Locations.AMSTERDAM_BOTTOM_RIGHT, MapConstants.ORIENTATION_NORTH)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.applyOnMap(MapAction {
            markerSettings.markerBalloonViewAdapter = EvAvailabilityBalloonViewAdapter()
        })
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.applyOnMap(MapAction {
            markerSettings.markerBalloonViewAdapter = TextBalloonViewAdapter()
        })
    }

    private fun displayResults(results: List<ChargingStationDetails>) {
        drawMarkers(results)
    }

    private fun drawMarkers(evStations: List<ChargingStationDetails>) {
        mainViewModel.applyOnMap(MapAction {
            EvAvailabilityMarkerDrawer(requireContext(), this).createMarkersForChargingWaypoints(evStations)
            markerSettings.zoomToAllMarkers()
        })
    }

    private fun clearMap() {
        mainViewModel.applyOnMap(MapAction { clear() })
    }
}
