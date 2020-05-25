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
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import com.tomtom.online.sdk.samples.ktx.cases.search.poicategories.PoiCategoriesAdapter
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.search.data.autocomplete.AutocompleteSearchResponse
import com.tomtom.online.sdk.search.data.autocomplete.response.Segment
import com.tomtom.sdk.examples.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import kotlinx.android.synthetic.main.default_search_fragment.*
import java.util.concurrent.TimeUnit

class AutocompleteSearchFragment : SearchFragment<AutocompleteSearchViewModel>(), OnAutocompleteClickListener {

    private lateinit var autocompleteAdapter: AutocompleteAdapter
    private val disposable = SerialDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        createViewModel()
    }

    override fun onResume() {
        super.onResume()
        clearSearch()
    }

    override fun setupSearchTypeSelector() {
        searchTypeSelector.visibility = View.GONE
    }

    override fun setupSearchView() {
        val changeEvents = RxSearchView.queryTextChangeEvents(searchView)
            .debounce(DEFAULT_REQUEST_DELAY_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
            .map { it.queryText().toString() }
            .filter { it.length > DEFAULT_MIN_NUMBER_OF_CHARACTERS_FOR_REQUEST }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { query ->
                viewModel.search(query)
                hideKeyboard()
            }
        disposable.set(changeEvents)
    }

    private fun setupRecyclerView() {
        searchResultRv?.let { view ->
            autocompleteAdapter = AutocompleteAdapter(this)
            view.adapter = autocompleteAdapter
            view.layoutManager = LinearLayoutManager(context)
            view.addItemDecoration(
                DividerItemDecoration(
                    view.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun createViewModel() {
        viewModel = searchViewModel()
        viewModel.autocompleteResult.observe(
            viewLifecycleOwner, ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::showAutocompleteSuggestions,
                onError = ::showError
            )
        )
    }

    override fun searchViewModel() = ViewModelProviders.of(requireActivity())
        .get(AutocompleteSearchViewModel::class.java)

    override fun onAutocompleteClicked(segment: Segment) {
        viewModel.searchForPlaces(segment.value, segment.type)
        Navigation.findNavController(searchResultRv).navigate(
            R.id.autocompleteMarkersFragment,
            bundleOf(
                PoiCategoriesAdapter.DATA_POI_SUBCATEGORIES to segment.type,
                PoiCategoriesAdapter.DATA_POI_CATEGORY_NAME to segment.value
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

    private fun clearSearch() {
        searchView.setQuery("", false)
        autocompleteAdapter.updateData(emptyList())
    }

    private fun showAutocompleteSuggestions(autocompleteSearchResponse: AutocompleteSearchResponse) {
        autocompleteAdapter.updateData(autocompleteSearchResponse.results.flatMap { it.segments })
    }

    companion object {
        const val DEFAULT_REQUEST_DELAY_IN_MILLISECONDS = 500L
        const val DEFAULT_MIN_NUMBER_OF_CHARACTERS_FOR_REQUEST = 2
    }
}
