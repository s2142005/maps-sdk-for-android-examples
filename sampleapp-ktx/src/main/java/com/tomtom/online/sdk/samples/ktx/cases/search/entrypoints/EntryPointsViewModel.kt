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

package com.tomtom.online.sdk.samples.ktx.cases.search.entrypoints

import android.app.Application
import com.tomtom.online.sdk.common.location.LatLngBias
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification
import com.tomtom.online.sdk.search.fuzzy.FuzzyLocationDescriptor
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchEngineDescriptor

class EntryPointsViewModel(application: Application) : SearchViewModel(application) {

    override fun search(term: String) {
        val searchEngineDescriptor = FuzzySearchEngineDescriptor.Builder()
            .idx(IDX_POI)
            .build()
        val locationDescriptor = FuzzyLocationDescriptor.Builder()
            .positionBias(LatLngBias(Locations.AMSTERDAM_CENTER))
            .build()
        val queryBuilder = FuzzySearchSpecification.Builder(term)
            .searchEngineDescriptor(searchEngineDescriptor)
            .locationDescriptor(locationDescriptor)
            .build()

        search(queryBuilder)
    }

    companion object {
        private const val IDX_POI = "POI"
    }
}