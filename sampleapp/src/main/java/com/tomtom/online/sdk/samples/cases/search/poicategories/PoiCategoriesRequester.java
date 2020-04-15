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
package com.tomtom.online.sdk.samples.cases.search.poicategories;

import android.content.Context;

import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.api.poicategories.PoiCategoriesSearchResultListener;
import com.tomtom.online.sdk.search.data.poicategories.PoiCategoriesQuery;
import com.tomtom.online.sdk.search.data.poicategories.PoiCategoriesQueryBuilder;

@SuppressWarnings("unused")
class PoiCategoriesRequester {

    private final Context context;
    private PoiCategoriesSearchResultListener poiCategoriesSearchListener;

    PoiCategoriesRequester(Context context, PoiCategoriesSearchResultListener poiCategoriesSearchListener) {
        this.context = context;
        this.poiCategoriesSearchListener = poiCategoriesSearchListener;
    }

    void performPoiCategoriesSearch() {
        SearchApi searchAPI = createSearchAPI();
        //tag::doc_create_poi_categories_query[]
        PoiCategoriesQuery poiCategoriesQuery = PoiCategoriesQueryBuilder.create()
                .withLanguage("en-GB")
                .build();
        searchAPI.poiCategoriesSearch(poiCategoriesQuery);
        //end::doc_create_poi_categories_query[]
    }

    SearchApi createSearchAPI() {
        return OnlineSearchApi.create(context);
    }
}
