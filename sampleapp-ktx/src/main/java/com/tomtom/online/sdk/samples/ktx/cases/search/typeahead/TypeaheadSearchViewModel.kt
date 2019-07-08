/*
 * Copyright (c) 2015-2019 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */

package com.tomtom.online.sdk.samples.ktx.cases.search.typeahead

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder

class TypeaheadSearchViewModel(application: Application) : SearchViewModel(application) {

    override fun search(query: String) {
        //tag::doc_create_typeahead_search_query[]
        val searchQuery = FuzzySearchQueryBuilder.create(query)
                .withPosition(addPosition())
                .withTypeAhead(true)
                .build()
        //end::doc_create_typeahead_search_query[]

        search(searchQuery)
    }
}
