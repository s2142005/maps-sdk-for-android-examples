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
package com.tomtom.online.sdk.samples.cases.search.autocomplete;

import android.content.Context;

import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.api.autocomplete.AutocompleteSearchResultListener;
import com.tomtom.online.sdk.search.data.autocomplete.AutocompleteSearchQuery;
import com.tomtom.online.sdk.search.data.autocomplete.AutocompleteSearchQueryBuilder;

@SuppressWarnings("unused")
class AutocompleteRequester {
    private final Context context;
    private AutocompleteSearchResultListener autocompleteSearchResultListener;

    AutocompleteRequester(Context context, AutocompleteSearchResultListener autocompleteSearchResultListener) {
        this.context = context;
        this.autocompleteSearchResultListener = autocompleteSearchResultListener;
    }

    void performPoiCategoriesSearch(String query) {
        SearchApi searchAPI = createSearchAPI();
        //tag::doc_create_autocomplete_query[]
        AutocompleteSearchQuery autocompleteQuery = AutocompleteSearchQueryBuilder.create(query, "en-GB")
                .withRadius(10_000)
                .withPosition(Locations.AMSTERDAM_LOCATION)
                .withCountry("NL")
                .withLimit(10)
                .build();
        searchAPI.autocompleteSearch(autocompleteQuery);
        //end::doc_create_autocomplete_query[]
    }

    SearchApi createSearchAPI() {
        return OnlineSearchApi.create(context);
    }
}
