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

package com.tomtom.online.sdk.samples.ktx.cases.search.batch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.search.data.batch.BatchSearchResponse
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_batchsearch.*
import kotlinx.android.synthetic.main.fragment_search_with_map.*

class BatchSearchFragment : SearchFragment<BatchSearchViewModel>() {

    override fun searchViewModel() = ViewModelProviders.of(this).get(BatchSearchViewModel::class.java)

    override fun setupSearchView() {
        // Search view is not needed here.
    }

    override fun setupSearchTypeSelector() {
        layoutInflater.inflate(R.layout.control_buttons_batchsearch, controlButtonsContainer, true)
        search_batch_parking_btn.setOnClickListener {
            viewModel.searchForParking()
        }
        search_batch_gas_btn.setOnClickListener {
            viewModel.searchForGas()
        }
        search_batch_bar_btn.setOnClickListener {
            viewModel.searchForBar()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.results.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::displayBatchSearchResults,
            onError = ::showError))
    }

    private fun displayBatchSearchResults(response: BatchSearchResponse) {
        mainViewModel.applyOnMap(MapAction {
            clear()
            BatchSearchResponseDisplayer(this).display(response)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_with_map, container, false)
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation()
        enableMarkersClustering()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        disableMarkersClustering()
    }

    private fun enableMarkersClustering() {
        mainViewModel.applyOnMap(MapAction {
            markerSettings.setMarkersClustering(true)
        })
    }

    private fun disableMarkersClustering() {
        mainViewModel.applyOnMap(MapAction {
            markerSettings.setMarkersClustering(false)
        })
    }
}