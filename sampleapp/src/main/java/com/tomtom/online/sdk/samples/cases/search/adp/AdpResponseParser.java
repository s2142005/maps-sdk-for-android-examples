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

import androidx.annotation.NonNull;

import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.common.geojson.Feature;
import com.tomtom.online.sdk.common.geojson.FeatureCollection;
import com.tomtom.online.sdk.common.geojson.GeoJsonObjectVisitorAdapter;
import com.tomtom.online.sdk.search.data.additionaldata.AdditionalDataSearchResponse;
import com.tomtom.online.sdk.search.data.additionaldata.result.AdditionalDataSearchResult;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import timber.log.Timber;

class AdpResponseParser {

    private GeoDataConsumer consumer;

    public AdpResponseParser(GeoDataConsumer consumer) {
        this.consumer = consumer;
    }

    @SuppressLint("CheckResult")
    public void parseAdpResponse(AdditionalDataSearchResponse additionalDataResponse) {
        Single.just(additionalDataResponse)
                .filter(nonEmptyResults())
                .map(firstAdpResult())
                .subscribe(adpResult -> parseAdpResult(adpResult),
                        throwable -> Timber.e(throwable, "Error when adp processed"));
    }

    public void parseAdpResult(AdditionalDataSearchResult adpResult) {
        adpResult.accept(result -> FuncUtils.apply(result.getGeometryData(), input -> {
            input.accept(new GeoJsonObjectVisitorAdapter() {
                @Override
                public void visit(FeatureCollection featureCollection) {
                    parseFeatureCollection(featureCollection);
                }
            });
        }));
    }

    @SuppressLint("CheckResult")
    public void parseFeatureCollection(FeatureCollection featureCollection) {
        Single.just(featureCollection)
                .filter(nonEmptyFeatures())
                .map(firstAdpFeature())
                .subscribe(consumer);
    }

    @NonNull
    public Predicate<AdditionalDataSearchResponse> nonEmptyResults() {
        return adpResults -> !adpResults.hasError() && !adpResults.getResults().isEmpty();
    }

    @NonNull
    private Function<AdditionalDataSearchResponse, AdditionalDataSearchResult> firstAdpResult() {
        return adpResults -> adpResults.getResults().asList().get(0);
    }

    @NonNull
    public Predicate<FeatureCollection> nonEmptyFeatures() {
        return featureSet -> !featureSet.getFeatures().isEmpty();
    }

    @NonNull
    public Function<FeatureCollection, Feature> firstAdpFeature() {
        return featureSet -> featureSet.getFeatures().get(0);
    }

}
