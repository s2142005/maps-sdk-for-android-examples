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

import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.api.fuzzy.FuzzySearchResultListener;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder;

public class EntryPointsSearchRequester {

    private Context context;
    private FuzzySearchResultListener searchResultCallback;
    private final static String IDX_POI = "POI";

    EntryPointsSearchRequester(Context context, FuzzySearchResultListener searchResultCallback) {
        this.context = context;
        this.searchResultCallback = searchResultCallback;
    }

    public void performSearch(String term) {
        SearchApi searchAPI = OnlineSearchApi.create(context);
        searchAPI.search(FuzzySearchQueryBuilder.create(term).withIdx(IDX_POI).build(), searchResultCallback);
    }
}
