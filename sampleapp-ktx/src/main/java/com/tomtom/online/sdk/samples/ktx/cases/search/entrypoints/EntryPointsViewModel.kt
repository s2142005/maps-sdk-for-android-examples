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

package com.tomtom.online.sdk.samples.ktx.cases.search.entrypoints

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder

class EntryPointsViewModel(application: Application) : SearchViewModel(application) {

    override fun search(query: String) {
        val queryBuilder = FuzzySearchQueryBuilder
                .create(query)
                .withIdx(IDX_POI)
                .withPosition(Locations.AMSTERDAM_CENTER)
                .build()

        search(queryBuilder)
    }

    companion object {
        private const val IDX_POI = "POI"
    }

}
