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

package com.tomtom.online.sdk.samples.ktx.cases.search.revgeopolygons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.SimpleMarkerBalloon
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.search.AdpResultsDrawer
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_rev_geo_polygons.*
import kotlinx.android.synthetic.main.fragment_search_with_map.*

class RevGeoPolygonsFragment : SearchFragment<RevGeoPolygonsViewModel>() {

    override fun searchViewModel() = ViewModelProviders.of(this).get(RevGeoPolygonsViewModel::class.java)

    override fun setupSearchView() {
        // Search view is not needed here.
    }

    override fun setupSearchTypeSelector() {
        layoutInflater.inflate(R.layout.control_buttons_rev_geo_polygons, controlButtonsContainer, true)
        rev_geo_polygons_country_btn.setOnClickListener { viewModel.enableCountryEntityType() }
        rev_geo_polygons_municipality_btn.setOnClickListener { viewModel.enableMunicipalityEntityType() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_with_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        confInfoBar()
        createViewModel()
        confCompass()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.applyOnMap(MapAction { addOnMapLongClickListener(performRevGeoWithAdpWhenLongPress) })
    }

    override fun onPause() {
        mainViewModel.applyOnMap(MapAction { removeOnMapLongClickListener(performRevGeoWithAdpWhenLongPress) })
        super.onPause()
    }

    private fun confInfoBar() {
        search_info_bar.visibility = View.VISIBLE
        search_info_bar.titleTextView.text = getString(R.string.rev_geo_polygons_infobar_title)
    }

    private fun confCompass() {
        mainViewModel.adjustCompassTopMarginForInfoBar(R.dimen.compass_with_small_bar_margin_top)
    }

    override fun onExampleStarted() {
        centerOnCountryView()
    }

    private fun createViewModel() {
        viewModel = searchViewModel()

        viewModel.revGeoWithAdpResult.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::showRevGeoResult,
            onError = ::showError))

        viewModel.entityType.observe(this, Observer { entityType ->
            mainViewModel.applyOnMap(MapAction { clear() })
            when (entityType) {
                RevGeoPolygonsViewModel.ENTITY_TYPE_COUNTRY -> centerOnCountryView()
                RevGeoPolygonsViewModel.ENTITY_TYPE_MUNICIPALITY -> centerOnMunicipalityView()
            }
        })
    }

    private fun showRevGeoResult(result: RevGeoWithAdpResponse) {
        mainViewModel.applyOnMap(MapAction {

            //Remove old markers and overlays
            markerSettings.removeMarkers()
            overlaySettings.removeOverlays()

            //Draw marker with rev geo
            result.revGeoResult?.let {
                val markerBalloon = SimpleMarkerBalloon(it.address.freeformAddress)
                val markerBuilder = MarkerBuilder(it.position).markerBalloon(markerBalloon)
                val marker = addMarker(markerBuilder)
                marker.select()
            }

            //Draw ADP polygons
            result.adpResult?.let { AdpResultsDrawer(this).draw(it) }
        })
    }

    private val performRevGeoWithAdpWhenLongPress = { latLng: LatLng ->
        viewModel.revGeoWithAdp(latLng)
    }

    private fun centerOnCountryView() {
        centerOn(ZOOM_LEVEL_FOR_COUNTRY)
    }

    private fun centerOnMunicipalityView() {
        centerOn(ZOOM_LEVEL_FOR_MUNICIPALITY)
    }

    private fun centerOn(zoomLevel: Double) {
        centerOnLocation(zoomLevel = zoomLevel)
    }

    companion object {
        const val ZOOM_LEVEL_FOR_COUNTRY = 4.0
        const val ZOOM_LEVEL_FOR_MUNICIPALITY = 9.0
    }

}