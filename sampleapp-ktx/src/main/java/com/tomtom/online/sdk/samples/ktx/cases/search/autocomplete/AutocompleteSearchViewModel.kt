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
import com.tomtom.online.sdk.common.location.LatLngBias
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.SearchException
import com.tomtom.online.sdk.search.autocomplete.*
import com.tomtom.online.sdk.search.autocomplete.entity.SegmentType
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification
import com.tomtom.online.sdk.search.fuzzy.FuzzyLocationDescriptor
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchEngineDescriptor

class AutocompleteSearchViewModel(application: Application) : SearchViewModel(application) {

    val autocompleteResult = ResourceLiveData<AutocompleteSuggestion>()

    override fun search(term: String) {
        autocompleteResult.value = Resource.loading(null)
        //tag::doc_create_poi_categories_specification[]
        val searchEngineDescriptor = AutocompleteSearchEngineDescriptor.Builder()
            .limit(10)
            .build()

        val locationDescriptor = AutocompleteLocationDescriptor.Builder()
            .positionBias(LatLngBias(Locations.AMSTERDAM, 10000.0))
            .countryCodes(setOf("NL"))
            .build()

        val autocompleteSpecification = AutocompleteSpecification.Builder(term, "en-GB")
            .searchEngineDescriptor(searchEngineDescriptor)
            .locationDescriptor(locationDescriptor)
            .build()
        //end::doc_create_poi_categories_specification[]
        searchRequester.autocompleteSearch(autocompleteSpecification, autocompleteSuggestionCallback)
    }

    fun searchForPlaces(term: String, type: SegmentType) {
        val searchEngineDescriptor = FuzzySearchEngineDescriptor.Builder()
        val locationDescriptor = FuzzyLocationDescriptor.Builder()
            .positionBias(LatLngBias(Locations.AMSTERDAM))
            .build()
        when (type) {
            SegmentType.BRAND -> searchEngineDescriptor.brandSet(listOf(term))
            SegmentType.CATEGORY -> searchEngineDescriptor.category(true)
            else -> {
            }
        }

        val fuzzySearchSpecification = FuzzySearchSpecification.Builder(term)
            .locationDescriptor(locationDescriptor)
            .searchEngineDescriptor(searchEngineDescriptor.build())
            .build()
        searchRequester.search(fuzzySearchSpecification, fuzzyOutcomeCallback)
    }

    private val autocompleteSuggestionCallback = object : AutocompleteSuggestionCallback {
        override fun onSuccess(autocompleteSuggestion: AutocompleteSuggestion) {
            autocompleteResult.value = Resource.success(autocompleteSuggestion)
        }

        override fun onError(error: SearchException) {
            searchResults.value = Resource.error(null, Error(error.message))
        }
    }
}
