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

package com.tomtom.online.sdk.samples.ktx.cases.search.adp

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.AdpSearchRequester
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.search.data.additionaldata.result.AdditionalDataSearchResult
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder

class AdpSearchViewModel(application: Application) : SearchViewModel(application) {

    val adpResult = ResourceLiveData<AdditionalDataSearchResult>()

    override fun search(term: String) {

    }

    fun searchWithAdp(term: String, geometriesZoom: Int) {
        //tag::doc_adp_search_request[]
        val searchQuery = FuzzySearchQueryBuilder.create(term).build()
        val requester = AdpSearchRequester(getApplication(), geometriesZoom)

        requester.fuzzyWithAdp(searchQuery, adpResult)
        //end::doc_adp_search_request[]
    }
}
