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
package com.tomtom.online.sdk.samples.ktx.cases.traffic

import android.content.Context
import com.tomtom.online.sdk.common.rx.RxContext
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.traffic.OnlineTrafficApi
import com.tomtom.online.sdk.traffic.incidents.query.IncidentDetailsQuery
import com.tomtom.online.sdk.traffic.incidents.response.IncidentDetailsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers

class TrafficRequester(context: Context) : RxContext {

    private val disposable = SerialDisposable()

    //tag::doc_traffic_init_api[]
    private val trafficApi = OnlineTrafficApi.create(context)
    //end::doc_traffic_init_api[]

    fun findIncidentDetails(incidentDetailsQuery: IncidentDetailsQuery, results: ResourceLiveData<IncidentDetailsResponse>) {
        results.value = Resource.loading(null)

        disposable.set(trafficApi.findIncidentDetails(incidentDetailsQuery)
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                        { response -> results.value = Resource.success(response) },
                        { error -> results.value = Resource.error(null, Error(error.message)) }
                ))
    }

    override fun getWorkingScheduler() = Schedulers.newThread()

    override fun getResultScheduler() = AndroidSchedulers.mainThread()!!

}