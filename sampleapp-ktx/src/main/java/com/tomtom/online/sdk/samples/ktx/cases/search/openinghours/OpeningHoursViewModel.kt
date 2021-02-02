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

package com.tomtom.online.sdk.samples.ktx.cases.search.openinghours

import android.app.Application
import com.tomtom.online.sdk.common.location.LatLngBias
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.fuzzy.*
import com.tomtom.online.sdk.search.time.OpeningHoursMode
import com.tomtom.online.sdk.search.time.TimeDescriptor

class OpeningHoursViewModel(application: Application) : SearchViewModel(application) {

    val openingHoursResults = ResourceListLiveData<FuzzySearchDetails>()

    override fun search(term: String) {
        //Not applicable
    }

    fun searchForPetrolStationsWithOpeningHours() {
        //tag::doc_create_opening_hours_search_specification[]
        val timeDescriptor = TimeDescriptor(OpeningHoursMode.NEXT_SEVEN_DAYS)

        val searchEngineDescriptor = FuzzySearchEngineDescriptor.Builder()
            .language("en-GB")
            .build()

        val locationDescriptorBuilder = FuzzyLocationDescriptor.Builder()
            .positionBias(LatLngBias(Locations.AMSTERDAM))
            .build()

        val openingHoursQuery = FuzzySearchSpecification.Builder("Petrol station")
            .searchEngineDescriptor(searchEngineDescriptor)
            .locationDescriptor(locationDescriptorBuilder)
            .timeDescriptor(timeDescriptor)
            .build()
        //end::doc_create_opening_hours_search_specification[]
        searchRequester.search(openingHoursQuery, openingHoursResults)
    }
}