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
package com.tomtom.online.sdk.samples.cases.search.revgeo.polygon;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.common.geojson.FeatureCollection;
import com.tomtom.online.sdk.common.geojson.geometry.Geometry;
import com.tomtom.online.sdk.common.geojson.geometry.MultiPolygon;
import com.tomtom.online.sdk.map.Polygon;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.data.additionaldata.AdditionalDataSearchQuery;
import com.tomtom.online.sdk.search.data.additionaldata.AdditionalDataSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.additionaldata.result.AdditionalDataSearchResult;
import com.tomtom.online.sdk.search.data.additionaldata.result.GeometryResult;
import com.tomtom.online.sdk.search.data.common.additionaldata.GeometryDataSource;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQuery;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * It is a helper class to convertToMapPolygon complex queries (to rev geo and adp services) in combined way.
 */
//tag::doc_reverse_geocoding_with_polygon_requester[]
class RevGeoWithAdpRequester {

    private final static int DEFAULT_GEOMETRIES_ZOOM = 4;
    private final PolygonAdapter polygonAdapter = new PolygonAdapter();
    private final SearchApi searchApi;

    private int geometriesZoom = DEFAULT_GEOMETRIES_ZOOM;

    RevGeoWithAdpRequester(SearchApi searchApi) {
        this.searchApi = searchApi;
    }

    Observable<Polygon> toPolygonObservable(Observable<RevGeoWithAdpResponse> revGeoWithAdpResponseObservable) {
        return revGeoWithAdpResponseObservable
                .flatMap((Function<RevGeoWithAdpResponse, ObservableSource<Polygon>>) revGeoWithAdpResponse -> {
                    AdditionalDataSearchResult adpResult =
                            FuncUtils.first(revGeoWithAdpResponse.getAdpResponse().getResults()).get();

                    return createSpecificPolygonObservable(parseGeometryResult(adpResult));
                });
    }

    @NonNull
    @VisibleForTesting
    protected GeometryResult parseGeometryResult(AdditionalDataSearchResult adpResult) {
        final AtomicReference<GeometryResult> gr = new AtomicReference<>();
        adpResult.accept(result -> gr.set(result));
        return gr.get();
    }

    Observable<RevGeoWithAdpResponse> rawReverseGeocoding(ReverseGeocoderSearchQuery reverseGeocoderQuery) {
        return searchApi.reverseGeocoding(reverseGeocoderQuery)
                .toObservable()
                .filter(response -> !response.getAddresses().isEmpty()
                        && FuncUtils.first(response.getAddresses()).get()
                        .getAdditionalDataSources().getGeometryDataSource().isPresent())
                .flatMap(response -> {
                    final GeometryDataSource geometryDataSource =
                            FuncUtils.first(response.getAddresses()).get()
                                    .getAdditionalDataSources().getGeometryDataSource().get();
                    return searchApi.additionalDataSearch(createAdditionalDataSearchQuery(geometryDataSource))
                            .toObservable().flatMap(adpResponse ->
                                    Observable.just(new RevGeoWithAdpResponse(response, adpResponse)));
                })
                .filter(response -> response.isValid())
                .filter(response -> response.getRevGeoResponse().hasResults())
                .filter(response -> !response.getAdpResponse().getResults().isEmpty());
    }

    void setGeometriesZoom(int geometriesZoom) {
        this.geometriesZoom = geometriesZoom;
    }

    private AdditionalDataSearchQuery createAdditionalDataSearchQuery(GeometryDataSource geometryDataSource) {
        return AdditionalDataSearchQueryBuilder.create()
                .withGeometryDataSource(geometryDataSource)
                .withGeometriesZoom(geometriesZoom)
                .build();
    }

    private Observable<Geometry> createGeometryObservable(GeometryResult result) {
        return Observable.just(result)
                .filter(geometryResult -> geometryResult.getGeometryData().isPresent())
                .map(geometryResult -> geometryResult.getGeometryData().get())
                .filter(geoJsonObject -> geoJsonObject instanceof FeatureCollection)
                .map(geoJsonObject -> ((FeatureCollection) geoJsonObject).getFeatures())
                .filter(features -> !features.isEmpty())
                .map(features -> FuncUtils.first(features).get())
                .filter(feature -> feature.getGeometry().isPresent())
                .map(feature -> feature.getGeometry().get());
    }

    private Observable<Polygon> createPolygonsObservable(Geometry geometry) {
        return Observable.just(geometry)
                .filter(geo -> geo instanceof MultiPolygon)
                .map(geo -> (MultiPolygon) geo)
                .flatMap((Function<MultiPolygon, ObservableSource<com.tomtom.online.sdk.common.geojson.geometry.Polygon>>)
                        multiPolygon -> Observable.fromIterable(multiPolygon.getPolygons()))
                .map(polygon -> getPolygonAdapter().convertToMapPolygon(polygon));
    }

    private Observable<Polygon> createSinglePolygonObservable(Geometry geometry) {
        return Observable.just(geometry)
                .filter(geo -> geo instanceof com.tomtom.online.sdk.common.geojson.geometry.Polygon)
                .map(geo -> (com.tomtom.online.sdk.common.geojson.geometry.Polygon) geo)
                .map(polygon -> getPolygonAdapter().convertToMapPolygon(polygon));
    }

    @VisibleForTesting
    protected PolygonAdapter getPolygonAdapter() {
        return polygonAdapter;
    }

    private Observable<Polygon> createSpecificPolygonObservable(GeometryResult result) {
        Geometry geometry = createGeometryObservable(result).blockingFirst();
        return (geometry instanceof MultiPolygon) ?
                createPolygonsObservable(geometry) :
                createSinglePolygonObservable(geometry);
    }
}
//end::doc_reverse_geocoding_with_polygon_requester[]
