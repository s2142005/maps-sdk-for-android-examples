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

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.data.autocomplete.AutocompleteSearchQueryBuilder
import com.tomtom.online.sdk.search.data.autocomplete.AutocompleteSearchResponse
import com.tomtom.online.sdk.search.data.autocomplete.response.SegmentType
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult

class AutocompleteSearchViewModel(application: Application) : SearchViewModel(application) {

    val autocompleteResult = ResourceLiveData<AutocompleteSearchResponse>()
    val searchResult = ResourceListLiveData<FuzzySearchResult>()

    override fun search(query: String) {
        //tag::doc_create_poi_categories_query[]
        val autocompleteQuery = AutocompleteSearchQueryBuilder.create(query, "en-GB")
            .withRadius(10_000)
            .withPosition(Locations.AMSTERDAM)
            .withCountry("NL")
            .withLimit(10)
            .build()
        //end::doc_create_poi_categories_query[]

        searchRequester.autocompleteSearch(autocompleteQuery, autocompleteResult)
    }

    fun searchForPlaces(query: String, type: SegmentType) {
        val builder = FuzzySearchQueryBuilder.create(query)
            .withPosition(Locations.AMSTERDAM)
        when (type) {
            SegmentType.BRAND -> builder.withBrandSet(query)
            SegmentType.CATEGORY -> builder.withCategory(true)
            else -> {
            }
        }
        val searchQuery = builder.build()
        searchRequester.search(searchQuery, searchResult)
    }
}
