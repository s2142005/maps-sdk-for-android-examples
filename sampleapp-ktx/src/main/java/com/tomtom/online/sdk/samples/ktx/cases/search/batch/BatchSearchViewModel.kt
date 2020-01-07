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

package com.tomtom.online.sdk.samples.ktx.cases.search.batch

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.search.data.batch.BatchSearchResponse

class BatchSearchViewModel(application: Application) : SearchViewModel(application) {

    val results = ResourceLiveData<BatchSearchResponse>()

    override fun search(query: String) {
        // Not applicable to this example.
    }

    fun searchForParking() {
        val batchSearchQuery = BatchSearchQueriesFactory()
                .createBatchSearchQuery("Parking")
        searchRequester.batchSearch(batchSearchQuery, results)
    }

    fun searchForGas() {
        val batchSearchQuery = BatchSearchQueriesFactory()
                .createBatchSearchQuery("Gas")
        searchRequester.batchSearch(batchSearchQuery, results)
    }

    fun searchForBar() {
        val batchSearchQuery = BatchSearchQueriesFactory()
                .createBatchSearchQuery("Bar")
        searchRequester.batchSearch(batchSearchQuery, results)
    }
}


