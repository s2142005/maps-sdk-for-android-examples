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

import android.content.Context;

import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.api.batch.BatchSearchResultListener;
import com.tomtom.online.sdk.search.data.batch.BatchSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder;
import com.tomtom.online.sdk.search.data.geometry.Geometry;
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQuery;
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQueryBuilder;
import com.tomtom.online.sdk.search.data.geometry.query.CircleGeometry;

import java.util.ArrayList;
import java.util.List;

public class BatchSearchRequester {

    private static final int GEOMETRY_RADIUS = 4000;

    private Context context;

    BatchSearchRequester(Context context) {
        this.context = context;
    }

    public void performSearch(String category, BatchSearchResultListener batchSearchResultListener) {
        //tag::doc_batch_search_request[]
        //Using batch, it is possible to execute different search types:
        //fuzzy, geometry or reverse geocoding. The order of responses
        //is the same as the order in which the queries are added.
        BatchSearchQueryBuilder batchQuery = new BatchSearchQueryBuilder();
        batchQuery.withFuzzySearchQuery(createAmsterdamQuery(category));
        batchQuery.withFuzzySearchQuery(createHaarlemQuery(category));
        batchQuery.withGeometrySearchQuery(createHoofddropQuery(category));

        final SearchApi searchApi = OnlineSearchApi.create(context);
        searchApi.batchSearch(batchQuery.build(), batchSearchResultListener);
        //end::doc_batch_search_request[]
    }

    private FuzzySearchQueryBuilder getBaseFuzzyQuery(String category) {
        return FuzzySearchQueryBuilder.create(category)
                .withCategory(true);
    }

    private FuzzySearchQuery createAmsterdamQuery(String category) {
        FuzzySearchQueryBuilder query = getBaseFuzzyQuery(category);
        query.withPosition(Locations.AMSTERDAM_CENTER_LOCATION);
        query.withLimit(10);
        return query.build();
    }

    private FuzzySearchQuery createHaarlemQuery(String category) {
        FuzzySearchQueryBuilder query = getBaseFuzzyQuery(category);
        query.withPosition(Locations.AMSTERDAM_HAARLEM);
        query.withLimit(15);
        return query.build();
    }

    private GeometrySearchQuery createHoofddropQuery(String category) {
        List<Geometry> geometries = new ArrayList<>();
        CircleGeometry circleGeometry = new CircleGeometry(Locations.HOOFDDORP_LOCATION, GEOMETRY_RADIUS);
        Geometry geometry = new Geometry(circleGeometry);
        geometries.add(geometry);
        return new GeometrySearchQueryBuilder(category, geometries).build();
    }
}
