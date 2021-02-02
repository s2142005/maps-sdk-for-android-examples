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
package com.tomtom.online.sdk.samples.cases.search.entrypoints;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapPadding;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.SearchException;
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcome;
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcomeCallback;

public class EntryPointsSearchPresenter extends BaseFunctionalExamplePresenter {

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
            centerOn(Locations.AMSTERDAM_LOCATION);
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
        resetMapPadding();
    }

    @Override
    protected void confMapPadding() {

        int offsetDefault = view.getContext().getResources().getDimensionPixelSize(R.dimen.offset_default);

        int actionBarHeight = view.getContext().getResources().getDimensionPixelSize(
                R.dimen.abc_action_bar_default_height_material);

        int padding = actionBarHeight + offsetDefault;

        tomtomMap.setPadding(new MapPadding(padding, padding, padding, padding));
    }

    public void performSearch(String term) {
        fragment.disableOptionsView();

        searchRequester.performSearch(term);
    }

    private FuzzyOutcomeCallback resultListener = new FuzzyOutcomeCallback() {
        @Override
        public void onError(@NonNull SearchException error) {
            view.showInfoText(error.getMessage(), Toast.LENGTH_LONG);
            view.enableOptionsView();
            tomtomMap.clear();
        }

        @Override
        public void onSuccess(@NonNull FuzzyOutcome fuzzyOutcome) {
            view.enableOptionsView();
            tomtomMap.clear();

            if (fuzzyOutcome.getFuzzyDetailsList().isEmpty()) {
                view.showInfoText(R.string.entry_points_no_results, Toast.LENGTH_LONG);
                return;
            }

            Icon icon = Icon.Factory.fromResources(
                    context, R.drawable.ic_marker_entry_point);

            String markerBalloonText = context.getString(R.string.entry_points_type);

            new EntryPointsMarkerDrawer(tomtomMap, markerBalloonText).handleResultsFromFuzzy(fuzzyOutcome, icon);
        }
    };
}
