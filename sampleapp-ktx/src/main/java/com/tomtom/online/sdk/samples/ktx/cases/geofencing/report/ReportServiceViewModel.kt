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
package com.tomtom.online.sdk.samples.ktx.cases.geofencing.report

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.geofencing.report.Report
import com.tomtom.online.sdk.geofencing.report.ReportQuery
import com.tomtom.online.sdk.samples.ktx.cases.geofencing.GeofencingRequester
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import java.util.*

class ReportServiceViewModel(application: Application) : AndroidViewModel(application) {

    var reportResponse = ResourceLiveData<Report>()
    lateinit var projectId: UUID

    fun requestReport(location: LatLng) {
        val reportQuery = prepareQuery(location)

        GeofencingRequester(getApplication()).obtainReport(reportQuery, reportResponse)
    }

    private fun prepareQuery(position: LatLng): ReportQuery {
        return (
            //tag::doc_create_report_service_query[]
            ReportQuery.Builder(position.toLocation())
                .projectId(projectId)
                .range(QUERY_RANGE)
                .build()
            //end::doc_create_report_service_query[]
            )
    }

    companion object {
        private const val QUERY_RANGE = 5000f //in meters
    }

}
