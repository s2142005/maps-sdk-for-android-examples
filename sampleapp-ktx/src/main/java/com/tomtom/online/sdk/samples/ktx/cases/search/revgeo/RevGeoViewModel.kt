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

package com.tomtom.online.sdk.samples.ktx.cases.search.revgeo

import android.app.Application
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderFullAddress
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder

class RevGeoViewModel(application: Application) : SearchViewModel(application) {

    val revGeoSearchResults = ResourceListLiveData<ReverseGeocoderFullAddress>()

    override fun search(query: String) {
        // Not needed for rev geo
    }

    fun revGeo(latLng: LatLng) {
        //tag::doc_reverse_geocoding_query[]
        val revGeoQuery = ReverseGeocoderSearchQueryBuilder(latLng.latitude, latLng.longitude).build()
        //end::doc_reverse_geocoding_query[]
        searchRequester.revGeo(revGeoQuery, revGeoSearchResults)
    }
}
