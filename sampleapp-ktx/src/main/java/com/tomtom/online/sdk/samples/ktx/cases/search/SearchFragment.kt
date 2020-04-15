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

package com.tomtom.online.sdk.samples.ktx.cases.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomtom.online.sdk.samples.ktx.LocationViewModel
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.extensions.hideKeyboard
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult
import com.tomtom.online.sdk.search.extensions.SearchService
import com.tomtom.online.sdk.search.extensions.SearchServiceConnectionCallback
import com.tomtom.online.sdk.search.extensions.SearchServiceManager
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.default_search_fragment.*

abstract class SearchFragment<T : SearchViewModel> : ExampleFragment() {

    protected lateinit var viewModel: T
    private lateinit var locationViewModel: LocationViewModel
    private var searchResultsAdapter: SearchResultsAdapter? = null

    abstract fun setupSearchView()

    abstract fun searchViewModel(): T

    abstract fun setupSearchTypeSelector()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Abstract methods to configure views
        setupSearchView()
        setupRecyclerView()
        setupSearchTypeSelector()

        //Internal behavior for selected components
        initLocationViewModel()
        initSearchViewModel()
        initKeyboardBehavior()
        initSearchTypeSelectorBehavior()
    }

    override fun onResume() {
        super.onResume()
        locationViewModel.startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        locationViewModel.stopLocationUpdates()
    }

    override fun onExampleEnded() {
        mainViewModel.applyOnMap(MapAction { clear() })
        hideKeyboard()
    }

    private fun initSearchViewModel() {
        viewModel = searchViewModel()
        viewModel.updateLocation(locationViewModel.lastKnownLocationOrNull())
        viewModel.searchResults.observe(viewLifecycleOwner, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::displaySearchResults,
            onError = ::showError))
    }

    private fun initLocationViewModel() {
        locationViewModel = ViewModelProviders.of(requireActivity()).get(LocationViewModel::class.java)
        locationViewModel.lastKnownLocation().observe(viewLifecycleOwner, Observer { loc ->
            viewModel.updateLocation(loc)
        })
    }

    private fun setupRecyclerView() {
        searchResultRv?.let { view ->
            searchResultsAdapter = SearchResultsAdapter()
            view.adapter = searchResultsAdapter
            view.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSearchTypeSelectorBehavior() {
        searchTypeSelector?.let { selector ->
            viewModel.selectedTabOrNull()?.let { position ->
                selector.selectTabAt(position)
            }
            selector.setupTabSelectedListener { tab ->
                viewModel.selectTab(tab.position)
            }
        }
    }

    open fun displaySearchResults(searchResults: List<FuzzySearchResult>) {
        searchResultsAdapter?.updateData(searchResults)
    }

    private fun initKeyboardBehavior() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun hideKeyboard() {
        searchView?.let { searchView ->
            hideKeyboard(requireContext(), searchView.windowToken)
            searchView.clearFocus()
        }
    }

    @Suppress("unused")
    protected fun bindAndUnbindSearchService() {
        //tag::doc_search_service_binding[]
        val searchServiceConnection = SearchServiceManager.createAndBind(context,
            searchServiceConnectionCallback)
        //end::doc_search_service_binding[]

        //tag::doc_search_service_unbinding[]
        SearchServiceManager.unbind(context, searchServiceConnection)
        //end::doc_search_service_unbinding[]
    }

    private val searchServiceConnectionCallback = object : SearchServiceConnectionCallback {
        //tag::doc_search_service_connection_callback[]
        override fun onBindSearchService(service: SearchService) {
            val searchService = service
        }
        //end::doc_search_service_connection_callback[]
    }

}