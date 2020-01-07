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
package com.tomtom.online.sdk.samples.ktx.cases.traffic.incident

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.samples.ktx.cases.traffic.TrafficRequester
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.traffic.incidents.query.IncidentDetailsQuery
import com.tomtom.online.sdk.traffic.incidents.query.IncidentDetailsQueryBuilder
import com.tomtom.online.sdk.traffic.incidents.query.IncidentStyle
import com.tomtom.online.sdk.traffic.incidents.response.IncidentDetailsResponse

class TrafficIncidentListViewModel(application: Application) : AndroidViewModel(application) {

    var trafficResponse = ResourceLiveData<IncidentDetailsResponse>()

    fun findIncidentDetails() {
        val incidentQuery = prepareIncidentQuery()

        TrafficRequester(getApplication()).findIncidentDetails(incidentQuery, trafficResponse)
    }

    private fun prepareIncidentQuery(): IncidentDetailsQuery {
        return (
                //tag::doc_traffic_query[]
                IncidentDetailsQueryBuilder.create(IncidentStyle.S1, LONDON_BOUNDING_BOX, DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE, TRAFFIC_MODEL_ID)
                        .withExpandCluster(true)
                        .build()
                //end::doc_traffic_query[]
                )
    }

    companion object {
        private val LONDON_BOUNDING_BOX = BoundingBox(LatLng(51.544300, -0.176267), LatLng(51.465582, -0.071777))
        private const val DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 12
        private const val TRAFFIC_MODEL_ID = "-1"
    }
}
