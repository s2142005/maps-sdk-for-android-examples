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

package com.tomtom.online.sdk.samples.ktx.cases.search.alongroute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.SimpleMarkerBalloon
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResult
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_alongroutesearch.*
import kotlinx.android.synthetic.main.fragment_search_with_map.*

class AlongRouteFragment : SearchFragment<AlongRouteViewModel>() {

    override fun searchViewModel() = ViewModelProviders.of(this).get(AlongRouteViewModel::class.java)

    override fun setupSearchView() {
        // Search view is not needed here.
    }

    override fun setupSearchTypeSelector() {
        layoutInflater.inflate(R.layout.control_buttons_alongroutesearch, controlButtonsContainer, true)
        search_along_route_car_repair_btn.setOnClickListener {
            viewModel.searchForCarRepairs()
        }
        search_along_route_gas_station_btn.setOnClickListener {
            viewModel.searchForGasStations()
        }
        search_along_route_ev_stations_btn.setOnClickListener {
            viewModel.searchForEvStations()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_with_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.routingResult.observe(viewLifecycleOwner, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::displayRoute,
            onError = ::showError))

        viewModel.results.observe(viewLifecycleOwner, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::displayResults,
            onError = ::showError))
    }

    private fun displayResults(searchResults: List<AlongRouteSearchResult>) {
        mainViewModel.applyOnMap(MapAction {
            markerSettings.removeMarkers()
            searchResults.forEach { searchResult ->
                markerSettings.addMarker(
                        MarkerBuilder(searchResult.position)
                                .markerBalloon(SimpleMarkerBalloon(searchResult.poi.name))
                )
            }
            displayRoutesOverview()
        })

    }

    private fun displayRoute(route: FullRoute) {
        mainViewModel.applyOnMap(MapAction {
            RouteDrawer(requireContext(), this).drawDefault(listOf(route))
            displayRoutesOverview()
        })
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation()
    }

    override fun onResume() {
        super.onResume()
        viewModel.planDefaultRoute()
    }

}