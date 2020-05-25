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

package com.tomtom.online.sdk.samples.ktx.cases.search.openinghours

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.data.common.OpeningHoursMode
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult

class OpeningHoursViewModel(application: Application) : SearchViewModel(application) {

    val searchResult = ResourceListLiveData<FuzzySearchResult>()

    override fun search(query: String) {
        //Not applicable
    }

    fun searchForPetrolStationsWithOpeningHours() {
        //tag::doc_create_opening_hours_search_query[]
        val openingHoursQuery = FuzzySearchQueryBuilder.create("Petrol station")
            .withOpeningHours(OpeningHoursMode.NEXT_SEVEN_DAYS)
            .withPosition(Locations.AMSTERDAM)
            .withLanguage("en-GB")
            .build()
        //end::doc_create_opening_hours_search_query[]

        searchRequester.search(openingHoursQuery, searchResult)
    }
}
