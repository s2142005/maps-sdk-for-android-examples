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

package com.tomtom.online.sdk.samples.ktx.cases.search.entrypoints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_entrypoints.*
import kotlinx.android.synthetic.main.fragment_search_with_map.*

class EntryPointsFragment : SearchFragment<EntryPointsViewModel>() {

    override fun searchViewModel() = ViewModelProviders.of(this).get(EntryPointsViewModel::class.java)

    override fun setupSearchView() {
        // Search view is not needed here.
    }

    override fun setupSearchTypeSelector() {
        layoutInflater.inflate(R.layout.control_buttons_entrypoints, controlButtonsContainer, true)
        search_entry_airport_btn.setOnClickListener {
            viewModel.search("Amsterdam Airport Schiphol")
        }
        search_entry_shopping_mall_btn.setOnClickListener {
            viewModel.search("Kalvertoren Singel")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_with_map, container, false)
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation()
    }

    override fun displaySearchResults(searchResults: List<FuzzySearchResult>) {
        context?.apply {
            val icon = Icon.Factory.fromResources(this, R.drawable.ic_marker_entry_point)
            val markerBalloonText = getString(R.string.entry_points_type)

            mainViewModel.applyOnMap(MapAction {
                EntryPointsMarkerDrawer(this, markerBalloonText).handleResultsFromFuzzy(searchResults.first(), icon)
            })
        }
    }

}