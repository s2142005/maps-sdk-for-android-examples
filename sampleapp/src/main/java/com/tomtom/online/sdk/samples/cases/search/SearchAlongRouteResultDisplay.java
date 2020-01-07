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

import android.widget.Toast;

import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.search.api.SearchError;
import com.tomtom.online.sdk.search.api.alongroute.AlongRouteSearchResultListener;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResponse;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResult;

/**
 * Class to display search result.
 */
class SearchAlongRouteResultDisplay implements AlongRouteSearchResultListener {
    private final TomtomMap tomtomMap;
    private final FunctionalExampleFragment view;

    public SearchAlongRouteResultDisplay(TomtomMap tomtomMap, FunctionalExampleFragment view) {

        this.tomtomMap = tomtomMap;
        this.view = view;
    }

    @Override
    public void onSearchResult(AlongRouteSearchResponse alongRouteSearchResponse) {
        tomtomMap.getMarkerSettings().removeMarkers();
        for (AlongRouteSearchResult result : alongRouteSearchResponse.getResults()) {
            MarkerBuilder markerBuilder = new MarkerBuilder(result.getPosition())
                    .markerBalloon(new SimpleMarkerBalloon(result.getPoi().getName()));
            tomtomMap.addMarker(markerBuilder);
        }
        view.enableOptionsView();
    }

    @Override
    public void onSearchError(SearchError error) {
        view.showInfoText(error.getMessage(), Toast.LENGTH_LONG);
        tomtomMap.getMarkerSettings().removeMarkers();
        view.enableOptionsView();
    }
}
