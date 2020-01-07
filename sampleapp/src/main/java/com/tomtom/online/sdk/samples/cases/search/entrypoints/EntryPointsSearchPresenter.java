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
import android.widget.Toast;

import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.api.SearchError;
import com.tomtom.online.sdk.search.api.fuzzy.FuzzySearchResultListener;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse;

public class EntryPointsSearchPresenter extends BaseFunctionalExamplePresenter {

    private static final int ZOOM_LEVEL_FOR_EXAMPLE = 10;
    private static final int DEFAULT_MAP_PADDING = 0;

    protected Context context;
    protected FunctionalExampleFragment fragment;
    private EntryPointsSearchRequester searchRequester;

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        context = view.getContext();
        fragment = view;
        searchRequester = new EntryPointsSearchRequester(view.getContext(), resultListener);

        if (!view.isMapRestored()) {
            centerOnDefaultLocation();
        }
        confMapPadding();
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new EntryPointsFunctionalExample();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        tomtomMap.clear();
        tomtomMap.setPadding(
                DEFAULT_MAP_PADDING,
                DEFAULT_MAP_PADDING,
                DEFAULT_MAP_PADDING,
                DEFAULT_MAP_PADDING
        );
    }

    @Override
    protected void confMapPadding() {

        int offsetDefault = view.getContext().getResources().getDimensionPixelSize(R.dimen.offset_default);

        int actionBarHeight = view.getContext().getResources().getDimensionPixelSize(
                R.dimen.abc_action_bar_default_height_material);

        int padding = actionBarHeight + offsetDefault;

        tomtomMap.setPadding(padding, padding, padding, padding);
    }

    private void centerOnDefaultLocation() {
        tomtomMap.centerOn(CameraPosition.builder(Locations.AMSTERDAM_LOCATION)
                .bearing(MapConstants.ORIENTATION_NORTH)
                .zoom(ZOOM_LEVEL_FOR_EXAMPLE)
                .build());
    }

    public void performSearch(String term) {
        fragment.disableOptionsView();

        searchRequester.performSearch(term);
    }

    private FuzzySearchResultListener resultListener = new FuzzySearchResultListener() {
        @Override
        public void onSearchResult(FuzzySearchResponse fuzzySearchResponse) {

            view.enableOptionsView();
            tomtomMap.clear();

            if (fuzzySearchResponse.getResults().isEmpty()) {
                view.showInfoText(R.string.entry_points_no_results, Toast.LENGTH_LONG);
                return;
            }

            Icon icon = Icon.Factory.fromResources(
                    context, R.drawable.ic_marker_entry_point);

            String markerBalloonText = context.getString(R.string.entry_points_type);

            new EntryPointsMarkerDrawer(tomtomMap, markerBalloonText).handleResultsFromFuzzy(fuzzySearchResponse, icon);
        }

        @Override
        public void onSearchError(SearchError error) {
            view.showInfoText(error.getMessage(), Toast.LENGTH_LONG);
            view.enableOptionsView();
            tomtomMap.clear();
        }
    };
}
