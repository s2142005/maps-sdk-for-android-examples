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

package com.tomtom.online.sdk.samples.ktx.cases.search.adp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.search.AdpResultsDrawer
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.search.data.additionaldata.result.AdditionalDataSearchResult
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_adp_search.*
import kotlinx.android.synthetic.main.fragment_search_with_map.*

class AdpSearchFragment : SearchFragment<AdpSearchViewModel>() {

    override fun searchViewModel() = ViewModelProviders.of(this).get(AdpSearchViewModel::class.java)

    override fun setupSearchView() {
        // Search view is not needed here.
    }

    override fun setupSearchTypeSelector() {
        layoutInflater.inflate(R.layout.control_buttons_adp_search, controlButtonsContainer, true)
        adp_search_poland_btn.setOnClickListener { viewModel.searchWithAdp("Poland", 5) }
        adp_search_amsterdam_btn.setOnClickListener { viewModel.searchWithAdp("Amsterdam", 12) }
        adp_search_airport_btn.setOnClickListener { viewModel.searchWithAdp("Amsterdam Airport Schiphol", 15) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_with_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
    }

    override fun onExampleStarted() {
        centerOnLocation()
    }

    private fun createViewModel() {
        viewModel = searchViewModel()
        viewModel.adpResult.observe(viewLifecycleOwner, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::showAdpResults,
            onError = ::showError))
    }

    private fun showAdpResults(result: AdditionalDataSearchResult) {
        mainViewModel.applyOnMap(MapAction {
            overlaySettings.removeOverlays()
            AdpResultsDrawer(this).draw(listOf(result))
        })
    }
}