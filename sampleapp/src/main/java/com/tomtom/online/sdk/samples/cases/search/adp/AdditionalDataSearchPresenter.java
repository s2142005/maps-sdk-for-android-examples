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
package com.tomtom.online.sdk.samples.cases.search.adp;

import android.annotation.SuppressLint;
import android.content.Context;

import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.data.additionaldata.AdditionalDataSearchResponse;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder;

import io.reactivex.functions.Consumer;

public class AdditionalDataSearchPresenter extends BaseFunctionalExamplePresenter {

    private static final int ZOOM_LEVEL_FOR_EXAMPLE = 10;

    protected Context context;
    protected FunctionalExampleFragment fragment;
    private AdditionalDataSearchRequester searchRequester;

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);

        fragment = view;
        context = view.getContext();
        searchRequester = new AdditionalDataSearchRequester(context);

        if (!view.isMapRestored()) {
            centerOnDefaultLocation();
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new AdditionalDataSearchFunctionalExample();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        tomtomMap.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        searchRequester.disposeObservable();
    }

    public void centerOnDefaultLocation() {
        tomtomMap.centerOn(CameraPosition.builder(Locations.AMSTERDAM_CENTER_LOCATION)
                .bearing(MapConstants.ORIENTATION_NORTH)
                .zoom(ZOOM_LEVEL_FOR_EXAMPLE)
                .build());
    }

    @SuppressLint("CheckResult")
    public void performSearch(String term) {

        tomtomMap.clear();
        fragment.disableOptionsView();

        //tag::doc_adp_search_request[]
        FuzzySearchQuery fuzzySearchQuery = FuzzySearchQueryBuilder.create(term).build();

        searchRequester.performAdpSearch(fuzzySearchQuery, responseConsumer);
        //end::doc_adp_search_request[]
    }

    private Consumer<AdditionalDataSearchResponse> responseConsumer = new Consumer<AdditionalDataSearchResponse>() {
        @Override
        public void accept(AdditionalDataSearchResponse adpResponse) {
            fragment.enableOptionsView();
            new AdpResponseParser(new GeoDataConsumer(tomtomMap)).parseAdpResponse(adpResponse);
        }
    };
}
