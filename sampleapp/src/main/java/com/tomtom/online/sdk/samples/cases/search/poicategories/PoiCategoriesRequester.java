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
package com.tomtom.online.sdk.samples.cases.search.poicategories;

import android.content.Context;

import com.tomtom.online.sdk.samples.BuildConfig;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.poicategories.PoiCategoriesCallback;
import com.tomtom.online.sdk.search.poicategories.PoiCategoriesSpecification;

@SuppressWarnings("unused")
class PoiCategoriesRequester {

    private final Context context;
    private PoiCategoriesCallback poiCategoriesCallback;

    PoiCategoriesRequester(Context context, PoiCategoriesCallback poiCategoriesCallback) {
        this.context = context;
        this.poiCategoriesCallback = poiCategoriesCallback;
    }

    void performPoiCategoriesSearch() {
        SearchApi searchAPI = createSearchAPI();
        //tag::doc_create_poi_categories_specification[]
        PoiCategoriesSpecification poiCategoriesSpecification = new PoiCategoriesSpecification("en-GB");
        searchAPI.poiCategoriesSearch(poiCategoriesSpecification, poiCategoriesCallback);
        //end::doc_create_poi_categories_specification[]
    }

    SearchApi createSearchAPI() {
        return OnlineSearchApi.create(context, BuildConfig.SEARCH_API_KEY);
    }
}