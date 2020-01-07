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

package com.tomtom.online.sdk.samples.ktx.cases.search.revgeo

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
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderFullAddress
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_search_with_map.*

class RevGeoFragment : SearchFragment<RevGeoViewModel>() {

    override fun searchViewModel() = ViewModelProviders.of(this).get(RevGeoViewModel::class.java)

    override fun setupSearchView() {
    }

    override fun setupSearchTypeSelector() {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_with_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        confInfoBar()
        confViewModel()
        confCompass()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.applyOnMap(MapAction { addOnMapLongClickListener(showMessageWhenOnMapLongClickListener) })
    }

    override fun onPause() {
        mainViewModel.applyOnMap(MapAction { removeOnMapLongClickListener(showMessageWhenOnMapLongClickListener) })
        super.onPause()
    }

    override fun onExampleStarted() {
        centerOnLocation()
    }

    private fun confInfoBar() {
        search_info_bar.visibility = View.VISIBLE
        search_info_bar.titleTextView.text = getString(R.string.rev_geo_infobar_title)
    }

    private fun confViewModel() {
        viewModel = searchViewModel()
        viewModel.revGeoSearchResults.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::showRevGeoResult,
            onError = ::showError))
    }

    private fun confCompass() {
        mainViewModel.adjustCompassTopMarginForInfoBar(R.dimen.compass_with_small_bar_margin_top)
    }

    private fun showRevGeoResult(list: List<ReverseGeocoderFullAddress>) {
        val markerBalloon = SimpleMarkerBalloon.ofText(list.first().address.freeformAddress)
        val markerBuilder = MarkerBuilder(list.first().position).markerBalloon(markerBalloon)

        mainViewModel.applyOnMap(MapAction {
            removeMarkers()
            addMarker(markerBuilder).select()
        })
    }

    private val showMessageWhenOnMapLongClickListener = { latLng: LatLng ->
        viewModel.revGeo(latLng)
    }

}