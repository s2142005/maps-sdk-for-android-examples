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

package com.tomtom.online.sdk.samples.ktx.cases.search.typeahead

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import kotlinx.android.synthetic.main.default_search_fragment.*
import java.util.concurrent.TimeUnit

class TypeaheadSearchFragment : SearchFragment<TypeaheadSearchViewModel>() {

    private val disposable = SerialDisposable()

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

    override fun showLoading() {
        // Do not show loading
    }

    override fun hideLoading() {
        // Progress is not shown so there is no need to hide it
    }

    override fun searchViewModel() = ViewModelProviders.of(this)
            .get(TypeaheadSearchViewModel::class.java)

    companion object {
        const val DEFAULT_REQUEST_DELAY_IN_MILLISECONDS = 500L
        const val DEFAULT_MIN_NUMBER_OF_CHARACTERS_FOR_REQUEST = 3
    }
}
