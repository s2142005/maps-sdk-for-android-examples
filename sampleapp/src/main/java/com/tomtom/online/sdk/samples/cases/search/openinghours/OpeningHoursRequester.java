/**
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.cases.search.openinghours;

import android.content.Context;

import com.tomtom.online.sdk.common.location.LatLngBias;
import com.tomtom.online.sdk.samples.BuildConfig;
import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcomeCallback;
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification;
import com.tomtom.online.sdk.search.fuzzy.FuzzyLocationDescriptor;
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchEngineDescriptor;
import com.tomtom.online.sdk.search.time.TimeDescriptor;
import com.tomtom.online.sdk.search.time.OpeningHoursMode;

@SuppressWarnings("unused")
class OpeningHoursRequester {
    private final Context context;
    private FuzzyOutcomeCallback fuzzyOutcomeCallback;

    OpeningHoursRequester(Context context, FuzzyOutcomeCallback fuzzyOutcomeCallback) {
        this.context = context;
        this.fuzzyOutcomeCallback = fuzzyOutcomeCallback;
    }

    void performPoiCategoriesSearch(String query) {
        SearchApi searchAPI = createSearchAPI();

        //tag::doc_create_opening_hours_specification[]
        TimeDescriptor timeDescriptor = new TimeDescriptor(OpeningHoursMode.NEXT_SEVEN_DAYS);

        FuzzySearchEngineDescriptor fuzzySearchEngineDescriptor = new FuzzySearchEngineDescriptor.Builder()
                .language("en-GB")
                .build();

        FuzzyLocationDescriptor fuzzyLocationDescriptor = new FuzzyLocationDescriptor.Builder()
                .positionBias(new LatLngBias(Locations.AMSTERDAM_LOCATION))
                .build();

        FuzzySearchSpecification openingHoursSpecification = new FuzzySearchSpecification.Builder("Petrol station")
                .searchEngineDescriptor(fuzzySearchEngineDescriptor)
                .locationDescriptor(fuzzyLocationDescriptor)
                .timeDescriptor(timeDescriptor)
                .build();
        searchAPI.search(openingHoursSpecification, fuzzyOutcomeCallback);
        //end::doc_create_opening_hours_specification[]
    }

    SearchApi createSearchAPI() {
        return OnlineSearchApi.create(context, BuildConfig.SEARCH_API_KEY);
    }
}
