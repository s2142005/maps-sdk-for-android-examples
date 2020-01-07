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

package com.tomtom.online.sdk.samples.ktx.cases.search.category

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.extensions.ifNotNullNorEmpty
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.default_search_fragment.*

class CategorySearchFragment : SearchFragment<CategorySearchViewModel>() {

    override fun setupSearchTypeSelector() {
        searchTypeSelector.initSearchTabs(tabTitlesResId = intArrayOf(
                R.string.label_category_search_parking_btn,
                R.string.label_category_search_gas_btn,
                R.string.label_category_search_atm_btn)
        )
    }

    override fun setupSearchView() {
        searchView.visibility = View.GONE
    }


    override fun searchViewModel(): CategorySearchViewModel {
        val viewModel = ViewModelProviders.of(this).get(CategorySearchViewModel::class.java)
        viewModel.selectedTab().observe(this, Observer { position ->
            when (position) {
                0 -> viewModel.search("Parking")
                1 -> viewModel.search("Gas")
                2 -> viewModel.search("Atm")
            }
            searchView.query.ifNotNullNorEmpty { query -> viewModel.search(query) }
        })
        return viewModel
    }

}
