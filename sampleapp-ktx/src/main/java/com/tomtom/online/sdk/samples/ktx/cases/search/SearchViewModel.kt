/*
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */

package com.tomtom.online.sdk.samples.ktx.cases.search

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.common.location.LatLngBias
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.SingleLiveEvent
import com.tomtom.online.sdk.search.SearchException
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcome
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcomeCallback
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchDetails
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification

abstract class SearchViewModel(application: Application) : AndroidViewModel(application) {

    val searchResults = ResourceListLiveData<FuzzySearchDetails>()

    protected val searchRequester = SearchRequester(application)
    private val selectedTab = SingleLiveEvent<Int>()
    private var lastKnownLocation: Location? = null

    init {
        selectedTab.value = 0
    }

    abstract fun search(term: String)

    fun search(searchSpecification: FuzzySearchSpecification) {
        searchResults.value = Resource.loading(null)
        searchRequester.search(searchSpecification, fuzzyOutcomeCallback)
    }

    fun addPosition(): LatLng? = lastKnownLocation?.let { location -> LatLng(location) }

    fun addPreciseness(): LatLngBias? =
        lastKnownLocation?.let { location -> LatLngBias(LatLng(location), SearchRequester.STANDARD_RADIUS.toDouble()) }

    fun clearResults() {
        searchResults.value = Resource.success(listOf())
    }

    fun updateLocation(location: Location?) {
        val refreshSelectedTab = lastKnownLocation == null
        lastKnownLocation = location
        if (refreshSelectedTab) {
            selectedTab.value?.let { selectedTab.value = it }
        }
    }

    fun selectTab(tabPos: Int) {
        selectedTab.value = tabPos
    }

    fun selectedTab(): LiveData<Int> = selectedTab

    fun selectedTabOrNull(): Int? = selectedTab.value

    val fuzzyOutcomeCallback = object : FuzzyOutcomeCallback {
        override fun onSuccess(fuzzyOutcome: FuzzyOutcome) {
            searchResults.value = Resource.success(fuzzyOutcome.fuzzyDetailsList)
        }

        override fun onError(error: SearchException) {
            searchResults.value = Resource.error(null, Error(error.message))
        }
    }
}