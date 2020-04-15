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

package com.tomtom.online.sdk.samples.ktx.cases.search.poicategories

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.cases.search.poicategories.PoiCategoriesAdapter.Companion.DATA_POI_CATEGORY_NAME
import com.tomtom.online.sdk.samples.ktx.cases.search.poicategories.PoiCategoriesAdapter.Companion.DATA_POI_SUBCATEGORIES
import com.tomtom.online.sdk.search.data.poicategories.PoiCategory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_search_fragment.*


class PoiSubcategoriesFragment : SearchFragment<PoiCategoriesSearchViewModel>() {

    private lateinit var poiCategoriesAdapter: PoiCategoriesAdapter

    override fun setupSearchTypeSelector() {
        searchTypeSelector.visibility = View.GONE
    }

    override fun searchViewModel(): PoiCategoriesSearchViewModel {
        return ViewModelProviders.of(this).get(PoiCategoriesSearchViewModel::class.java)
    }

    override fun setupSearchView() {
        searchView.visibility = View.GONE
    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        (arguments?.getSerializable(DATA_POI_SUBCATEGORIES) as? ArrayList<PoiCategory>)?.let {
            showPoiCategories(it)
        }
        arguments?.getString(DATA_POI_CATEGORY_NAME)?.let {
            requireActivity().toolbar.title = it
        }
    }

    private fun setupRecyclerView() {
        searchResultRv?.let { view ->
            poiCategoriesAdapter = PoiCategoriesAdapter()
            view.adapter = poiCategoriesAdapter
            view.layoutManager = LinearLayoutManager(context)
            view.addItemDecoration(
                DividerItemDecoration(
                    view.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun showPoiCategories(poiCategories: List<PoiCategory>) {
        poiCategoriesAdapter.updateData(poiCategories)
    }

}
