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

import com.tomtom.online.sdk.search.data.geometry.Geometry;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.api.geometry.GeometrySearchResultListener;
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQuery;
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQueryBuilder;
import com.tomtom.online.sdk.search.data.geometry.query.CircleGeometry;
import com.tomtom.online.sdk.search.data.geometry.query.PolygonGeometry;

import java.util.ArrayList;
import java.util.List;


class GeometrySearchRequester {

    private static final int SEARCH_RESULTS_LIMIT = 50;

    private Context context;

    GeometrySearchRequester(Context context) {
        this.context = context;
    }

    void performGeometrySearch(String term, GeometrySearchResultListener geometrySearchCallback) {

        //tag::doc_geometries_creation[]
        List<Geometry> geometries = new ArrayList<>();
        geometries.add(new Geometry(new PolygonGeometry(DefaultGeometries.POLYGON_POINTS)));
        geometries.add(new Geometry(new CircleGeometry(DefaultGeometries.CIRCLE_CENTER, DefaultGeometries.CIRCLE_RADIUS)));
        //end::doc_geometries_creation[]

        //tag::doc_geometry_search_request[]
        GeometrySearchQuery query = new GeometrySearchQueryBuilder(term, geometries)
                .withLimit(SEARCH_RESULTS_LIMIT).build();

        SearchApi searchAPI = OnlineSearchApi.create(context);
        searchAPI.geometrySearch(query, geometrySearchCallback);
        //end::doc_geometry_search_request[]
    }
}
