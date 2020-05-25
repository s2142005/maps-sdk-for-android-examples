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

package com.tomtom.online.sdk.samples.ktx.cases.search.autocomplete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult
import com.tomtom.sdk.examples.R

class AutocompleteMarkersFragment : SearchFragment<AutocompleteSearchViewModel>() {

    override fun searchViewModel() = ViewModelProviders.of(requireActivity()).get(AutocompleteSearchViewModel::class.java)

    override fun setupSearchView() {
        //Not applicable
    }

    override fun setupSearchTypeSelector() {
        //Not applicable
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_with_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = searchViewModel()
        viewModel.searchResult.observe(
            viewLifecycleOwner, ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::displayResults,
                onError = ::showError
            )
        )
    }

    private fun displayResults(searchResults: List<FuzzySearchResult>) {
        mainViewModel.applyOnMap(MapAction {
            markerSettings.removeMarkers()
            searchResults.forEach { searchResult ->
                markerSettings.addMarker(
                    MarkerBuilder(searchResult.position)
                )
            }
            markerSettings.zoomToAllMarkers()
        })
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation()
    }
}