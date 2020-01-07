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

package com.tomtom.online.sdk.samples.ktx.cases.search.revgeopolygons

import android.app.Application
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.samples.ktx.cases.search.AdpSearchRequester
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.SingleLiveEvent
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder

class RevGeoPolygonsViewModel(application: Application) : SearchViewModel(application) {

    val revGeoWithAdpResult = ResourceLiveData<RevGeoWithAdpResponse>()
    val entityType: SingleLiveEvent<String> = SingleLiveEvent()

    init {
        entityType.value = ENTITY_TYPE_COUNTRY
    }

    override fun search(query: String) {
        // Not needed for rev geo
    }

    fun enableCountryEntityType() {
        entityType.value = ENTITY_TYPE_COUNTRY
    }

    fun enableMunicipalityEntityType() {
        entityType.value = ENTITY_TYPE_MUNICIPALITY
    }

    fun revGeoWithAdp(latLng: LatLng) {
        //tag::doc_reverse_geocoding_with_polygon_query[]
        val queryBuilder = ReverseGeocoderSearchQueryBuilder(latLng.latitude, latLng.longitude)
        entityType.value?.let {
            queryBuilder.withEntityType(it)
        }

        val adpSearchRequester = AdpSearchRequester(getApplication(), getGeometriesZoomForEntityType())
        adpSearchRequester.revGeoWithAdp(queryBuilder.build(), revGeoWithAdpResult)
        //end::doc_reverse_geocoding_with_polygon_query[]
    }

    private fun getGeometriesZoomForEntityType(): Int = when (entityType.value) {
        ENTITY_TYPE_COUNTRY -> GEOMETRIES_ZOOM_FOR_ENTITY_TYPE_COUNTRY
        ENTITY_TYPE_MUNICIPALITY -> GEOMETRIES_ZOOM_FOR_ENTITY_TYPE_MUNICIPALITY
        else -> 0
    }

    companion object {
        const val ENTITY_TYPE_COUNTRY = "Country"
        const val GEOMETRIES_ZOOM_FOR_ENTITY_TYPE_COUNTRY = 5
        const val ENTITY_TYPE_MUNICIPALITY = "Municipality"
        const val GEOMETRIES_ZOOM_FOR_ENTITY_TYPE_MUNICIPALITY = 15

    }

}
