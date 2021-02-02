/*
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
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
import com.tomtom.online.sdk.routing.OnlineRoutingApi
import com.tomtom.online.sdk.routing.batch.BatchRoutesCallback
import com.tomtom.online.sdk.routing.batch.BatchRoutesSpecification
import com.tomtom.online.sdk.routing.ev.EvRouteCallback
import com.tomtom.online.sdk.routing.ev.EvRouteSpecification
import com.tomtom.online.sdk.routing.reachablerange.ReachableAreaCallback
import com.tomtom.online.sdk.routing.reachablerange.ReachableRangeSpecification
import com.tomtom.online.sdk.routing.route.RouteCallback
import com.tomtom.online.sdk.routing.route.RoutePlan
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.routing.route.information.FullRoute
import com.tomtom.online.sdk.routing.rx.RxRoutingApi
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.sdk.examples.BuildConfig
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers

class RoutingRequester(context: Context) : RxContext {

    private val disposable = SerialDisposable()

    //tag::doc_initialise_routing[]
    private val routingApi = OnlineRoutingApi.create(context, BuildConfig.ROUTING_API_KEY)
    //end::doc_initialise_routing[]

    //tag::doc_initialise_rx_routing[]
    private val rxRoutingApi = RxRoutingApi(context, BuildConfig.ROUTING_API_KEY)
    //end::doc_initialise_rx_routing[]

    fun planBatchRoutes(batchRoutesSpecification: BatchRoutesSpecification, batchRoutesCallback: BatchRoutesCallback) {
        //tag::doc_execute_batch_routing[]
        routingApi.planRoutes(batchRoutesSpecification, batchRoutesCallback)
        //end::doc_execute_batch_routing[]
    }

    fun planRoute(routeSpecification: RouteSpecification, routeCallback: RouteCallback) {
        //tag::doc_execute_routing[]
        routingApi.planRoute(routeSpecification, routeCallback)
        //end::doc_execute_routing[]
    }

    @Suppress("unused", "UNUSED_VARIABLE")
    fun planRxRoute(routeSpecification: RouteSpecification, result: ResourceLiveData<RoutePlan>) {
        //tag::doc_execute_rx_routing[]
        val disposable = rxRoutingApi.planRoute(routeSpecification)
            .subscribeOn(workingScheduler)
            .observeOn(resultScheduler)
            .subscribe(
                { routePlan -> result.value = Resource.success(routePlan) },
                { result.value = Resource.error(Error(it)) }
            )
        //end::doc_execute_rx_routing[]
    }

    fun planRoutes(routeSpecifications: List<RouteSpecification>, result: ResourceLiveData<List<FullRoute>>) {
        val routes = ArrayList<FullRoute>()
        disposable.set(Single.concat(
            rxRoutingApi.planRoute(routeSpecifications.first()),
            rxRoutingApi.planRoute(routeSpecifications.last())
        )
            .subscribeOn(workingScheduler)
            .observeOn(resultScheduler)
            .subscribe(
                { routePlan -> routes.addAll(routePlan.routes) },
                { error -> result.value = Resource.error(null, Error(error.message)) },
                { result.value = Resource.success(routes) }
            )
        )
    }

    fun planReachableRange(
        reachableRangeSpecification: ReachableRangeSpecification,
        reachableAreaCallback: ReachableAreaCallback
    ) {
        //tag::doc_route_api_call[]
        routingApi.planReachableRange(reachableRangeSpecification, reachableAreaCallback)
        //end::doc_route_api_call[]
    }

    fun planRoute(evRouteSpecification: EvRouteSpecification, evRouteCallback: EvRouteCallback) {
        //tag::doc_ev_route_api_call[]
        return routingApi.planRoute(evRouteSpecification, evRouteCallback)
        //end::doc_ev_route_api_call[]
    }

    override fun getWorkingScheduler() = Schedulers.newThread()

    override fun getResultScheduler() = AndroidSchedulers.mainThread()!!
}