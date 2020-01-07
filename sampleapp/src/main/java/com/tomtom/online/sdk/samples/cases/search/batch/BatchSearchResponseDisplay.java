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

import android.graphics.Color;

import com.google.common.collect.ImmutableList;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.CircleBuilder;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.BatchableSearchResponse;
import com.tomtom.online.sdk.search.data.batch.BatchSearchResponse;
import com.tomtom.online.sdk.search.data.batch.BatchableSearchResponseVisitorAdapter;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult;
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchResponse;
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchResult;

public class BatchSearchResponseDisplay extends BatchableSearchResponseVisitorAdapter {

    private static final int GEOMETRY_RADIUS = 4000;

    private TomtomMap tomtomMap;

    BatchSearchResponseDisplay(TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
    }

    public void display(BatchSearchResponse batchSearchResponse){
        ImmutableList<BatchableSearchResponse> responses = batchSearchResponse.getSearchResponses().asList();
        for (BatchableSearchResponse response : responses) {
            response.accept(new BatchSearchResponseDisplay(tomtomMap));
        }
        tomtomMap.zoomToAllMarkers();
    }

    @Override
    public void visit(FuzzySearchResponse fuzzySearchResponse) {
        for (FuzzySearchResult result : fuzzySearchResponse.getResults()) {
            LatLng position = result.getPosition();
            String name = result.getPoi().getName();
            addMarker(position, name);
        }
    }

    @Override
    public void visit(GeometrySearchResponse geometrySearchResponse) {
        for (GeometrySearchResult result : geometrySearchResponse.getResults()) {
            LatLng position = result.getPosition();
            String name = result.getPoi().getName();
            addMarker(position, name);
        }
        addHoofddropGeometry();
    }

    private void addMarker(LatLng position, String name) {
        tomtomMap.addMarker(new MarkerBuilder(position)
                .markerBalloon(new SimpleMarkerBalloon(name))
                .shouldCluster(true)
        );
    }

    private void addHoofddropGeometry() {
        tomtomMap.getOverlaySettings().addOverlay(
                CircleBuilder.create()
                        .position(Locations.HOOFDDORP_LOCATION)
                        .radius(GEOMETRY_RADIUS)
                        .color(Color.argb(128, 255, 0, 0))
                        .fill(true)
                        .build()
        );
    }
}
