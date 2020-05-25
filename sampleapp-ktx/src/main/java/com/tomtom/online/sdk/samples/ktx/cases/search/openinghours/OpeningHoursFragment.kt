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
package com.tomtom.online.sdk.samples.ktx.cases.search.openinghours

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.samples.ktx.utils.formatter.OpeningHoursFormatter
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult
import com.tomtom.sdk.examples.R

class OpeningHoursFragment : SearchFragment<OpeningHoursViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchResult.observe(
            viewLifecycleOwner, ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::showMarkers,
                onError = ::showError
            )
        )
        searchViewModel().searchForPetrolStationsWithOpeningHours()
    }

    override fun setupSearchView() {
        //Not applicable
    }

    override fun searchViewModel(): OpeningHoursViewModel {
        return ViewModelProviders.of(this).get(OpeningHoursViewModel::class.java)
    }

    override fun setupSearchTypeSelector() {
        //Not applicable
    }

    private fun showMarkers(list: List<FuzzySearchResult>) {
        mainViewModel.applyOnMap(MapAction {
            list.filter {
                it.poi.openingHours.isPresent
            }.forEach {
                val weekdays = OpeningHoursFormatter.format(it.poi.openingHours.get())
                val balloon =
                    OpeningHoursMarkerBalloon(it.poi.name, weekdays.first, weekdays.second)
                markerSettings.markerBalloonViewAdapter = OpeningHoursBalloonViewAdapter()
                markerSettings.addMarker(MarkerBuilder(it.position).tag(it.id).markerBalloon(balloon))
            }
            zoomToAllMarkers()
        })
    }

}