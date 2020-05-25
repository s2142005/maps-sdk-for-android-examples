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
package com.tomtom.online.sdk.samples.ktx.cases.geofencing

import android.content.Context
import com.tomtom.online.sdk.common.rx.RxContext
import com.tomtom.online.sdk.geofencing.GeofencingApi
import com.tomtom.online.sdk.geofencing.RxGeofencingApi
import com.tomtom.online.sdk.geofencing.report.Report
import com.tomtom.online.sdk.geofencing.report.ReportQuery
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers

class GeofencingRequester(context: Context) : RxContext {

    private val disposable = SerialDisposable()

    //tag::doc_initialise_geofencing[]
    private val geofencingApi = GeofencingApi(context)
    //end::doc_initialise_geofencing[]
    //tag::doc_initialise_rx_geofencing[]
    private val rxGeofencingApi = RxGeofencingApi(context)
    //end::doc_initialise_rx_geofencing[]

    fun obtainReport(reportQuery: ReportQuery, result: ResourceLiveData<Report>) {
        result.value = Resource.loading(null)

        //tag::doc_obtain_report_rx[]
        rxGeofencingApi.obtainReport(reportQuery)
            .subscribeOn(workingScheduler)
            .observeOn(resultScheduler)
            .subscribe(
                { response -> result.value = Resource.success(response) },
                { error -> result.value = Resource.error(null, Error(error.message)) }
            )
            .let { disposable.set(it) }
        //end::doc_obtain_report_rx[]
    }

    override fun getWorkingScheduler() = Schedulers.newThread()

    override fun getResultScheduler() = AndroidSchedulers.mainThread()!!

}