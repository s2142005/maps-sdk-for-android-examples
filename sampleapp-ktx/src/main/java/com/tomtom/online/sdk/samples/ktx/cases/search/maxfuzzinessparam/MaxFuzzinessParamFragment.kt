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

package com.tomtom.online.sdk.samples.ktx.cases.search.maxfuzzinessparam

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.extensions.ifNotNullNorEmpty
import com.tomtom.online.sdk.samples.ktx.extensions.ifNullNorEmpty
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.default_search_fragment.*

class MaxFuzzinessParamFragment : SearchFragment<MaxFuzzinessParamViewModel>() {

    override fun setupSearchTypeSelector() {
        searchTypeSelector.initSearchTabs(tabTitlesResId = intArrayOf(
            R.string.label_max_fuzzy_level_one_btn,
            R.string.label_max_fuzzy_level_two_btn,
            R.string.label_max_fuzzy_level_three_btn)
        )
    }

    override fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText.ifNullNorEmpty { viewModel.clearResults() }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query.ifNotNullNorEmpty { viewModel.search(it) }
                hideKeyboard()
                return true
            }
        })
    }

    override fun searchViewModel(): MaxFuzzinessParamViewModel {
        val viewModel = ViewModelProviders.of(this).get(MaxFuzzinessParamViewModel::class.java)
        viewModel.selectedTab().observe(this, Observer { position ->
            when (position) {
                0 -> viewModel.switchMaxFuzzyLevelToOne()
                1 -> viewModel.switchMaxFuzzyLevelToTwo()
                2 -> viewModel.switchMaxFuzzyLevelToThree()
            }
            searchView.query.ifNotNullNorEmpty { query -> viewModel.search(query) }
        })
        return viewModel
    }

}
