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
package com.tomtom.online.sdk.samples.cases.search;

import android.content.Context;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.api.revgeo.RevGeoSearchResultListener;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQuery;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder;

import timber.log.Timber;

class ReverseGeocodingRequester {

    private final Context context;
    private RevGeoSearchResultListener revGeoSearchCallback;

    ReverseGeocodingRequester(Context context, RevGeoSearchResultListener revGeoSearchCallback) {
        this.context = context;
        this.revGeoSearchCallback = revGeoSearchCallback;
    }

    void performReverseGeocode(LatLng latLng) {

        //tag::doc_reverse_geocoding_request[]
        SearchApi searchAPI = createSearchAPI();
        ReverseGeocoderSearchQuery reverseGeocoderQuery =
                createReverseGeocoderQuery(latLng);

        searchAPI.reverseGeocoding(reverseGeocoderQuery, revGeoSearchCallback);
        //end::doc_reverse_geocoding_request[]
        //tag::doc_reverse_geocoding_request_rx[]
        searchAPI.reverseGeocoding(reverseGeocoderQuery).subscribe(response -> {
            Timber.i("Response address " + response.getSummary());
        }, throwable -> {
            Timber.i(throwable, "reverseGeocoding error ");
        });
        //end::doc_reverse_geocoding_request_rx[]

    }

    SearchApi createSearchAPI() {
        //tag::doc_create_search_object[]
        SearchApi searchApi = OnlineSearchApi.create(context);
        //end::doc_create_search_object[]
        return searchApi;
    }

    private ReverseGeocoderSearchQuery createReverseGeocoderQuery(LatLng latLng) {
        return ReverseGeocoderSearchQueryBuilder.create(latLng.getLatitude(), latLng.getLongitude()).build();
    }
}
