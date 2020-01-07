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

package com.tomtom.online.sdk.samples.ktx.cases.search.address

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder

class AddressSearchViewModel(application: Application) : SearchViewModel(application) {

    private var globalSearch = true

    override fun search(query: String) {
        val searchQuery = if (globalSearch) createGlobalQuery(query) else createLocalQuery(query)
        search(searchQuery)
    }

    private fun createLocalQuery(query: String): FuzzySearchQuery {
        return (
                //tag::doc_create_query_with_position[]
                FuzzySearchQueryBuilder.create(query)
                        .withPreciseness(addPreciseness())
                        .build()
                //end::doc_create_query_with_position[]
                )
    }

    private fun createGlobalQuery(query: String): FuzzySearchQuery {
        return FuzzySearchQueryBuilder.create(query)
                .withPosition(addPosition())
                .build()
    }

    fun enableGlobalSearch() {
        globalSearch = true
    }

    fun enableLocalSearch() {
        globalSearch = false
    }

    @Suppress("unused")
    private fun createBasicQuery(text: String): FuzzySearchQuery {
        return (
                //tag::doc_create_basic_query[]
                FuzzySearchQueryBuilder.create(text).build()
                //end::doc_create_basic_query[]
                )
    }
}
