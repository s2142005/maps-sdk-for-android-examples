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
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.search.data.poicategories.PoiCategoriesResponse
import com.tomtom.online.sdk.search.data.poicategories.PoiCategory
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.default_search_fragment.*


class PoiCategoriesSearchFragment : SearchFragment<PoiCategoriesSearchViewModel>(), OnPoiCategoryClickListener {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        createViewModel()
    }

    private fun createViewModel() {
        viewModel = searchViewModel()
        viewModel.poiCategoriesResult.observe(
            viewLifecycleOwner, ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::showPoiCategories,
                onError = ::showError
            )
        )
        viewModel.searchPoiCategories()
    }

    private fun setupRecyclerView() {
        searchResultRv?.let { view ->
            poiCategoriesAdapter = PoiCategoriesAdapter(this)
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

    private fun showPoiCategories(poiCategoriesResponse: PoiCategoriesResponse) {
        poiCategoriesAdapter.updateData(poiCategoriesResponse.poiCategories)
    }

    override fun onPoiCategoryClicked(poiCategory: PoiCategory) {
        Navigation.findNavController(searchResultRv).navigate(
            R.id.poiSubcategoriesFragment,
            bundleOf(
                PoiCategoriesAdapter.DATA_POI_SUBCATEGORIES to poiCategory.children,
                PoiCategoriesAdapter.DATA_POI_CATEGORY_NAME to poiCategory.name
            ),
            navOptions {
                anim {
                    enter = R.anim.slide_in_left
                    exit = R.anim.slide_out_left
                    popEnter = R.anim.slide_in_right
                    popExit = R.anim.slide_out_right
                }
            }
        )
    }

}
