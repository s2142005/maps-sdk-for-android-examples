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
package com.tomtom.online.sdk.samples.cases.search.batch;

import android.annotation.SuppressLint;

import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.api.SearchError;
import com.tomtom.online.sdk.search.api.batch.BatchSearchResultListener;
import com.tomtom.online.sdk.search.data.batch.BatchSearchResponse;

public class BatchSearchPresenter extends BaseFunctionalExamplePresenter {

    private FunctionalExampleFragment fragment;
    private BatchSearchRequester searchRequester;

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);

        fragment = view;
        searchRequester = new BatchSearchRequester(view.getContext());
        if (!view.isMapRestored()) {
            centerOnDefaultLocation();
        }

        tomtomMap.getMarkerSettings().setMarkersClustering(true);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new BatchSearchFunctionalExample();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        tomtomMap.clear();
    }

    private void centerOnDefaultLocation() {
        tomtomMap.centerOn(CameraPosition.builder(Locations.AMSTERDAM_CENTER_LOCATION)
                .bearing(MapConstants.ORIENTATION_NORTH)
                .build());
    }

    @SuppressLint("CheckResult")
    public void performSearch(String category) {
        tomtomMap.clear();
        fragment.disableOptionsView();

        searchRequester.performSearch(category, batchSearchResultListener);
    }

    private final BatchSearchResultListener batchSearchResultListener = new BatchSearchResultListener() {
        @Override
        public void onSearchResult(BatchSearchResponse response) {
            fragment.enableOptionsView();
            new BatchSearchResponseDisplay(tomtomMap).display(response);
        }

        @Override
        public void onSearchError(SearchError error) {
            ((BatchSearchFragment) view).showErrorMsg(error.getMessage());
            view.enableOptionsView();
            tomtomMap.clear();
        }
    };

}
