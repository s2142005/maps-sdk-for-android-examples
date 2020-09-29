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
package com.tomtom.online.sdk.samples.ktx.cases.search.poidetailsphotos

import android.app.Application
import android.graphics.Bitmap
import com.tomtom.online.sdk.common.location.LatLngBias
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.fuzzy.FuzzyLocationDescriptor
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchDetails
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchEngineDescriptor
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification
import com.tomtom.online.sdk.search.poi.details.Photo
import com.tomtom.online.sdk.search.poi.details.PoiDetails
import com.tomtom.online.sdk.search.poi.details.PoiDetailsSpecification
import com.tomtom.online.sdk.search.poi.photos.PoiPhotoSpecification
import com.tomtom.online.sdk.search.time.OpeningHoursMode
import com.tomtom.online.sdk.search.time.TimeDescriptor

class PoiDetailsPhotosViewModel(application: Application) : SearchViewModel(application) {

    var poiPhotos: ResourceListLiveData<Bitmap> = ResourceListLiveData()
    var poiDetails: ResourceLiveData<PoiDetails> = ResourceLiveData()

    override fun search(term: String) {
        //tag::doc_poi_details_photo_service_search[]
        val fuzzyLocationDescriptor = FuzzyLocationDescriptor.Builder()
            .positionBias(LatLngBias(Locations.AMSTERDAM))
            .build()
        val fuzzyTimeDescriptor = TimeDescriptor(OpeningHoursMode.NEXT_SEVEN_DAYS)
        val fuzzySearchEngineDescriptor = FuzzySearchEngineDescriptor.Builder()
            .limit(50)
            .build()
        val fuzzySearchSpecification = FuzzySearchSpecification.Builder(term)
            .locationDescriptor(fuzzyLocationDescriptor)
            .searchEngineDescriptor(fuzzySearchEngineDescriptor)
            .timeDescriptor(fuzzyTimeDescriptor)
            .build()
        search(fuzzySearchSpecification)
        //end::doc_poi_details_photo_service_search[]
    }

    fun searchPoiDetails(fuzzyDetails: FuzzySearchDetails) {
        //tag::doc_poi_details_photo_service_additional_data_sources[]
        val poiId = fuzzyDetails.additionalDataSources?.poiDetailsDataSources?.first()?.id
        //end::doc_poi_details_photo_service_additional_data_sources[]

        poiId?.let { id ->
            //tag::doc_poi_details_photo_service_fetch_additional_data_sources[]
            val poiDetailsSpecification = PoiDetailsSpecification(id)
            //end::doc_poi_details_photo_service_fetch_additional_data_sources[]
            searchRequester.poiDetailsSearch(poiDetailsSpecification, poiDetails)
        }
    }

    fun downloadPoiPhoto(listOfIds: List<Photo>) {
        //tag::doc_poi_details_photo_service_additional_data_sources_id[]
        val poiSpecifications = listOfIds.map { photo -> PoiPhotoSpecification.Builder(photo.id).build() }
        //end::doc_poi_details_photo_service_additional_data_sources_id[]
        searchRequester.poiPhotosDownload(poiSpecifications, poiPhotos)
    }

    fun recycleBitmaps() {
        poiPhotos.value?.data?.forEach { bitmap -> bitmap.recycle() }
    }
}