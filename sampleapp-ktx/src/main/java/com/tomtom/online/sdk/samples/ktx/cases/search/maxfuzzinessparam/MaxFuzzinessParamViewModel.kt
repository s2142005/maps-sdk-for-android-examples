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

package com.tomtom.online.sdk.samples.ktx.cases.search.maxfuzzinessparam

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder

class MaxFuzzinessParamViewModel(application: Application) : SearchViewModel(application) {

    private var maxFuzzyLevel = 1

    override fun search(query: String) {
        //tag::doc_create_fuzzy_search_query[]
        val fuzzyQuery = FuzzySearchQueryBuilder.create(query)
                .withPosition(addPosition())
                .withMinFuzzyLevel(1)
                .withMaxFuzzyLevel(maxFuzzyLevel)
                .build()
        //end::doc_create_fuzzy_search_query[]
        search(fuzzyQuery)
    }

    fun switchMaxFuzzyLevelToOne() {
        maxFuzzyLevel = 1
    }

    fun switchMaxFuzzyLevelToTwo() {
        maxFuzzyLevel = 2
    }

    fun switchMaxFuzzyLevelToThree() {
        maxFuzzyLevel = 3
    }

}
