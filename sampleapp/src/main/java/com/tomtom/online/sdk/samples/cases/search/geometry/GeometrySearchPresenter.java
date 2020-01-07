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
package com.tomtom.online.sdk.samples.cases.search.geometry;

import android.content.Context;
import android.widget.Toast;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.search.api.SearchError;
import com.tomtom.online.sdk.search.api.geometry.GeometrySearchResultListener;
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchResponse;
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchResult;

public class GeometrySearchPresenter extends BaseFunctionalExamplePresenter{

    private static final LatLng MAP_CENTER_FOR_EXAMPLE = new LatLng(52.3691851, 4.8505632);
    private static final int ZOOM_LEVEL_FOR_EXAMPLE = 10;

    protected Context context;
    protected FunctionalExampleFragment fragment;
    private GeometrySearchRequester searchRequester;

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        context = view.getContext();
        fragment = view;
        searchRequester = new GeometrySearchRequester(context);

        if (!view.isMapRestored()) {
            centerOnDefaultLocation();
            showShapesOnMap();
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new GeometrySearchFunctionalExample();
    }

    @Override
    public void cleanup() {
        tomtomMap.clear();
    }

    public void centerOnDefaultLocation() {
        tomtomMap.centerOn(CameraPosition.builder(MAP_CENTER_FOR_EXAMPLE)
                .bearing(MapConstants.ORIENTATION_NORTH)
                .zoom(ZOOM_LEVEL_FOR_EXAMPLE)
                .build());
    }

    private void showShapesOnMap() {
        tomtomMap.getOverlaySettings().addOverlay(DefaultGeometries.createDefaultCircleOverlay());
        tomtomMap.getOverlaySettings().addOverlay(DefaultGeometries.createDefaultPolygonOverlay());
    }

    public void performSearch(String term) {
        fragment.disableOptionsView();

        searchRequester.performGeometrySearch(term, searchResultListener);
    }

    private GeometrySearchResultListener searchResultListener = new GeometrySearchResultListener() {
        @Override
        public void onSearchResult(GeometrySearchResponse geometrySearchResponse) {
            if (isResultEmpty(geometrySearchResponse)) {
                onSearchError(new SearchError("No results"));
                return;
            }
            fragment.enableOptionsView();
            tomtomMap.getMarkerSettings().removeMarkers();
            for (GeometrySearchResult result : geometrySearchResponse.getResults()) {
                createMarker(result.getPoi().getName(), result.getPosition());
            }
        }

        boolean isResultEmpty(GeometrySearchResponse geometrySearchResponse) {
            return geometrySearchResponse.getResults() == null || geometrySearchResponse.getResults().isEmpty();
        }

        @Override
        public void onSearchError(SearchError error) {
            view.showInfoText(error.getMessage(), Toast.LENGTH_LONG);
            tomtomMap.getMarkerSettings().removeMarkers();
            fragment.enableOptionsView();
        }
    };

    private void createMarker(String name, LatLng position) {
        MarkerBuilder markerBuilder = new MarkerBuilder(position)
                .markerBalloon(new SimpleMarkerBalloon(name));
        tomtomMap.addMarker(markerBuilder);
    }
}
