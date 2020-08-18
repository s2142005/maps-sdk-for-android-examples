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

import com.tomtom.online.sdk.common.location.LatLngBias;
import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.autocomplete.AutocompleteLocationDescriptor;
import com.tomtom.online.sdk.search.autocomplete.AutocompleteSearchEngineDescriptor;
import com.tomtom.online.sdk.search.autocomplete.AutocompleteSpecification;
import com.tomtom.online.sdk.search.autocomplete.AutocompleteSuggestionCallback;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
class AutocompleteRequester {
    private final Context context;
    private AutocompleteSuggestionCallback autocompleteSuggestionCallback;
    private final Set<String> countryCodes = new HashSet<>();

    AutocompleteRequester(Context context, AutocompleteSuggestionCallback autocompleteSuggestionCallback) {
        this.context = context;
        this.autocompleteSuggestionCallback = autocompleteSuggestionCallback;
        this.countryCodes.add("NL");
    }

    void performPoiCategoriesSearch(String term) {
        SearchApi searchAPI = createSearchAPI();
        //tag::doc_create_autocomplete_specification[]
        AutocompleteSearchEngineDescriptor searchEngineDescriptor = new AutocompleteSearchEngineDescriptor.Builder()
                .limit(10)
                .build();

        AutocompleteLocationDescriptor locationDescriptor = new AutocompleteLocationDescriptor.Builder()
                .countryCodes(countryCodes)
                .positionBias(new LatLngBias(Locations.AMSTERDAM_LOCATION))
                .build();

        AutocompleteSpecification autocompleteSpecification = new AutocompleteSpecification.Builder(term, "en-GB")
                .locationDescriptor(locationDescriptor)
                .searchEngineDescriptor(searchEngineDescriptor)
                .build();
        searchAPI.autocompleteSearch(autocompleteSpecification, autocompleteSuggestionCallback);
        //end::doc_create_autocomplete_specification[]
    }

    SearchApi createSearchAPI() {
        return OnlineSearchApi.create(context);
    }
}
