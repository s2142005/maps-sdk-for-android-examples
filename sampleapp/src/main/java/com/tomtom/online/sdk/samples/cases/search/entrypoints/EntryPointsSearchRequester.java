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
package com.tomtom.online.sdk.samples.cases.search.entrypoints;

import android.content.Context;

import com.tomtom.online.sdk.samples.BuildConfig;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcomeCallback;
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification;
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchEngineDescriptor;

public class EntryPointsSearchRequester {

    private Context context;
    private FuzzyOutcomeCallback fuzzyOutcomeCallback;
    private final static String IDX_POI = "POI";

    EntryPointsSearchRequester(Context context, FuzzyOutcomeCallback fuzzyOutcomeCallback) {
        this.context = context;
        this.fuzzyOutcomeCallback = fuzzyOutcomeCallback;
    }

    public void performSearch(String term) {
        FuzzySearchEngineDescriptor fuzzySearchEngineDescriptor = new FuzzySearchEngineDescriptor.Builder()
                .idx(IDX_POI)
                .build();
        FuzzySearchSpecification searchSpecification = new FuzzySearchSpecification.Builder(term)
                .searchEngineDescriptor(fuzzySearchEngineDescriptor)
                .build();

        SearchApi searchAPI = OnlineSearchApi.create(context, BuildConfig.SEARCH_API_KEY);
        searchAPI.search(searchSpecification, fuzzyOutcomeCallback);
    }
}