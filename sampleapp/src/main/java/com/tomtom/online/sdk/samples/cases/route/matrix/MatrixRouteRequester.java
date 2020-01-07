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
package com.tomtom.online.sdk.samples.cases.route.matrix;

import android.content.Context;

import androidx.annotation.NonNull;

import com.tomtom.online.sdk.common.rx.RxContext;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingQuery;
import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingResponse;

import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MatrixRouteRequester implements RxContext {

    private final static int NUMBER_OF_NETWORKING_THREADS = 4;

    private RoutingApi routePlannerApi;

    MatrixRouteRequester(Context context) {
        routePlannerApi = OnlineRoutingApi.create(context);
    }
    
    Disposable performMatrixRouting(MatrixRoutingQuery query, Consumer<MatrixRoutingResponse> responseConsumer, Consumer<Throwable> onError) {
        return  //tag::doc_execute_matrix_routing[]
                routePlannerApi.planMatrixRoutes(query)
                //end::doc_execute_matrix_routing[]
                .subscribeOn(getWorkingScheduler())
                .observeOn(getResultScheduler())
                .subscribe(responseConsumer, onError);
    }

    @NonNull
    @Override
    public Scheduler getWorkingScheduler() {
        return Schedulers.from(Executors.newFixedThreadPool(NUMBER_OF_NETWORKING_THREADS));
    }

    @NonNull
    @Override
    public Scheduler getResultScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
