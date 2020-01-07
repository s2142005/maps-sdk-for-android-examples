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

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.common.location.LatLngAcc
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.SingleLiveEvent
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult

abstract class SearchViewModel(application: Application) : AndroidViewModel(application) {

    val searchResults = ResourceListLiveData<FuzzySearchResult>()

    protected val searchRequester = SearchRequester(application)
    private val selectedTab = SingleLiveEvent<Int>()
    private var lastKnownLocation: Location? = null

    init {
        selectedTab.value = 0
    }

    abstract fun search(query: String)

    fun search(searchQuery: FuzzySearchQuery) {
        searchRequester.search(searchQuery, searchResults)
    }

    fun addPosition(): LatLng? = lastKnownLocation?.let { location -> LatLng(location) }

    fun addPreciseness(): LatLngAcc? = lastKnownLocation?.let { location -> LatLngAcc(LatLng(location), SearchRequester.STANDARD_RADIUS) }

    fun clearResults() {
        searchResults.value = Resource.success(listOf())
    }

    fun updateLocation(location: Location?) {
        lastKnownLocation = location
    }

    fun selectTab(tabPos: Int) {
        selectedTab.value = tabPos
    }

    fun selectedTab(): LiveData<Int> = selectedTab

    fun selectedTabOrNull(): Int? = selectedTab.value

}