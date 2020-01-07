/*
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */

package com.tomtom.online.sdk.samples.ktx.cases.route

import android.content.Context
import com.tomtom.online.sdk.common.rx.RxContext
import com.tomtom.online.sdk.data.reachablerange.ReachableRangeQuery
import com.tomtom.online.sdk.data.reachablerange.ReachableRangeResponse
import com.tomtom.online.sdk.routing.OnlineRoutingApi
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.routing.data.RouteQuery
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder
import com.tomtom.online.sdk.routing.data.batch.BatchRoutingQuery
import com.tomtom.online.sdk.routing.data.RouteResponse
import com.tomtom.online.sdk.routing.data.batch.BatchRoutingResponse
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers

class RoutingRequester(context: Context) : RxContext {

    private val disposable = SerialDisposable()

    //tag::doc_initialise_routing[]
    private val routingApi = OnlineRoutingApi.create(context)!!
    //end::doc_initialise_routing[]

    fun planBatchRoutes(routeQuery: BatchRoutingQuery, result: ResourceLiveData<BatchRoutingResponse>) {
        result.value = Resource.loading(null)

        disposable.set( //tag::doc_execute_batch_routing[]
                routingApi.planBatchRoute(routeQuery)
                //end::doc_execute_batch_routing[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                        { response -> result.value = Resource.success(response) },
                        { error -> result.value = Resource.error(null, Error(error.message)) }
                )
        )
    }

    fun planRoutes(routeQuery: RouteQuery, result: ResourceLiveData<List<FullRoute>>) {
        result.value = Resource.loading(null)

        //tag::doc_execute_routing[]
        disposable.set(routingApi.planRoute(routeQuery)
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                        { response -> result.value = Resource.success(response.routes) },
                        { error -> result.value = Resource.error(null, Error(error.message)) }
                )
        )
        //end::doc_execute_routing[]
    }

    fun planRoutes(routeQueries: List<RouteQuery>, result: ResourceLiveData<List<FullRoute>>) {
        result.value = Resource.loading(null)

        val routes = ArrayList<FullRoute>()

        disposable.set(Single.concat(createSources(routeQueries))
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                        { response -> routes.addAll(response.routes) },
                        { error -> result.value = Resource.error(null, Error(error.message)) },
                        { result.value = Resource.success(routes) }
                )
        )
    }

    fun planRoute(routeQueryBuilder: RouteQueryBuilder, result: ResourceLiveData<FullRoute>) {
        result.value = Resource.loading(null)
        val routeQuery = routeQueryBuilder.build()

        disposable.set(routingApi.planRoute(routeQuery)
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                        { response -> result.value = Resource.success(response.routes.firstOrNull()) },
                        { error -> result.value = Resource.error(null, Error(error.message)) }
                )
        )
    }

    fun planReachableRange(reachableRangeQuery: ReachableRangeQuery, result: ResourceLiveData<ReachableRangeResponse>) {
        result.value = Resource.loading(null)

        //tag::doc_route_api_call[]
        disposable.set(routingApi.findReachableRange(reachableRangeQuery)
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                        { response -> result.value = Resource.success(response) },
                        { error -> result.value = Resource.error(null, Error(error.message)) }
                )
        )
        //end::doc_route_api_call[]
    }

    private fun createSources(routeQueries: List<RouteQuery>): Iterable<Single<RouteResponse>> {
        val sources = ArrayList<Single<RouteResponse>>()

        routeQueries.forEach { routeQuery ->
            sources.add(routingApi.planRoute(routeQuery))
        }
        return sources
    }

    override fun getWorkingScheduler() = Schedulers.newThread()

    override fun getResultScheduler() = AndroidSchedulers.mainThread()!!
}