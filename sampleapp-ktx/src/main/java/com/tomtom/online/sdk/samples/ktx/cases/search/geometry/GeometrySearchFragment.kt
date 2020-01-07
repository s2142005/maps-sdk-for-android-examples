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
package com.tomtom.online.sdk.samples.ktx.cases.search.geometry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.SimpleMarkerBalloon
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.cases.search.geometry.DefaultGeometryCreator.createDefaultCircleOverlay
import com.tomtom.online.sdk.samples.ktx.cases.search.geometry.DefaultGeometryCreator.createDefaultPolygonOverlay
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchResult
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_geometry_search.*
import kotlinx.android.synthetic.main.fragment_search_with_map.*

class GeometrySearchFragment : SearchFragment<GeometrySearchViewModel>() {

    override fun searchViewModel(): GeometrySearchViewModel = ViewModelProviders.of(this).get(GeometrySearchViewModel::class.java)

    override fun setupSearchView() {
    }

    override fun setupSearchTypeSelector() {
        layoutInflater.inflate(R.layout.control_buttons_geometry_search, controlButtonsContainer, true)
        geometry_search_parking_btn.setOnClickListener {
            viewModel.searchForParking()
        }
        geometry_search_atm_btn.setOnClickListener {
            viewModel.searchForAtm()
        }
        geometry_search_grocery_btn.setOnClickListener {
            viewModel.searchForGrocery()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_with_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.results.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::displayResults,
            onError = ::showError))
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation(Locations.AMSTERDAM_HOOFDWEG)
        drawOverlays()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearMarkers()
        clearOverlays()
    }

    private fun displayResults(results: List<GeometrySearchResult>) {
        clearMarkers()
        results.forEach { result -> drawMarkers(result.poi.name, result.position) }
    }

    private fun drawOverlays() {
        mainViewModel.applyOnMap(MapAction {
            overlaySettings.addOverlay(createDefaultPolygonOverlay())
            overlaySettings.addOverlay(createDefaultCircleOverlay())
        })
    }

    private fun drawMarkers(name: String, position: LatLng) {
        mainViewModel.applyOnMap(MapAction {
            addMarker(createMarker(name, position))
        })
    }

    private fun createMarker(name: String, position: LatLng): MarkerBuilder {
        return MarkerBuilder(position)
                .markerBalloon(SimpleMarkerBalloon(name))
    }

    private fun clearMarkers() {
        mainViewModel.applyOnMap(MapAction { markerSettings.removeMarkers() })
    }

    private fun clearOverlays() {
        mainViewModel.applyOnMap(MapAction { overlaySettings.removeOverlays() })
    }

}
