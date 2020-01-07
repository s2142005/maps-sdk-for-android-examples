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

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.common.base.Optional;
import com.tomtom.online.sdk.common.rx.RxContext;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.data.additionaldata.AdditionalDataSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.additionaldata.AdditionalDataSearchResponse;
import com.tomtom.online.sdk.search.data.common.additionaldata.GeometryDataSource;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class AdditionalDataSearchRequester implements RxContext {

    private Context context;
    private Disposable observableWork;

    AdditionalDataSearchRequester(Context context) {
        this.context = context;
    }

    void performAdpSearch(FuzzySearchQuery query, Consumer<AdditionalDataSearchResponse> consumer) {
        final SearchApi searchApi = OnlineSearchApi.create(context);
        observableWork = searchApi.search(query)
                .observeOn(getWorkingScheduler())
                .filter(nonEmptyResponse())
                .map(firstResultWithAdditionalData())
                .filter(nonEmptyResult())
                .map(whenResultPresent())
                .filter(whenGeometrySourcePresent())
                .map(firstAvailableGeometryData())
                .flatMap(geometryDataSource -> {
                    AdditionalDataSearchQueryBuilder adpQuery = AdditionalDataSearchQueryBuilder.create();
                    adpQuery.withGeometryDataSource(geometryDataSource);
                    return searchApi.additionalDataSearch(adpQuery.build()).toMaybe();
                })
                .subscribeOn(getResultScheduler())
                .subscribe(consumer);
    }

    void disposeObservable() {
        if (observableWork != null) {
            observableWork.dispose();
        }
    }

    @NonNull
    private Function<FuzzySearchResult, GeometryDataSource> firstAvailableGeometryData() {
        return fuzzySearchResult -> fuzzySearchResult.getAdditionalDataSources().getGeometryDataSource().get();
    }

    @NonNull
    private Predicate<FuzzySearchResult> whenGeometrySourcePresent() {
        return fuzzySearchResult -> fuzzySearchResult.getAdditionalDataSources() != null &&
                fuzzySearchResult.getAdditionalDataSources().getGeometryDataSource().isPresent();
    }

    @NonNull
    private Function<FuzzySearchResponse, Optional<FuzzySearchResult>> firstResultWithAdditionalData() {
        return fuzzySearchResponse -> {
            for (FuzzySearchResult result : fuzzySearchResponse.getResults()) {
                if (result.getAdditionalDataSources() == null) {
                    continue;
                }
                return Optional.of(result);
            }
            return Optional.absent();
        };
    }

    @NonNull
    private Predicate<FuzzySearchResponse> nonEmptyResponse() {
        return fuzzySearchResponse -> !fuzzySearchResponse.getResults().isEmpty();
    }

    @NonNull
    private Predicate<Optional<FuzzySearchResult>> nonEmptyResult() {
        return fuzzySearchResult -> fuzzySearchResult.isPresent();
    }

    private Function<Optional<FuzzySearchResult>, FuzzySearchResult> whenResultPresent() {
        return fuzzySearchResultOptional -> fuzzySearchResultOptional.get();
    }

    @NonNull
    @Override
    public Scheduler getWorkingScheduler() {
        return Schedulers.newThread();
    }

    @NonNull
    @Override
    public Scheduler getResultScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
