/**
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
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

import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.api.autocomplete.AutocompleteSearchResultListener;
import com.tomtom.online.sdk.search.api.fuzzy.FuzzySearchResultListener;
import com.tomtom.online.sdk.search.data.autocomplete.AutocompleteSearchQuery;
import com.tomtom.online.sdk.search.data.autocomplete.AutocompleteSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.common.OpeningHoursMode;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder;

@SuppressWarnings("unused")
class OpeningHoursRequester {
    private final Context context;
    private FuzzySearchResultListener fuzzySearchResultListener;

    OpeningHoursRequester(Context context, FuzzySearchResultListener fuzzySearchResultListener) {
        this.context = context;
        this.fuzzySearchResultListener = fuzzySearchResultListener;
    }

    void performPoiCategoriesSearch(String query) {
        SearchApi searchAPI = createSearchAPI();
        //tag::doc_create_opening_hours_query[]
        FuzzySearchQuery openingHoursQuery = FuzzySearchQueryBuilder.create("Petrol station")
                .withOpeningHours(OpeningHoursMode.NEXT_SEVEN_DAYS)
                .withPosition(Locations.AMSTERDAM_LOCATION)
                .withLanguage("en-GB")
                .build();
        searchAPI.search(openingHoursQuery, fuzzySearchResultListener);
        //end::doc_create_opening_hours_query[]
    }

    SearchApi createSearchAPI() {
        return OnlineSearchApi.create(context);
    }
}
