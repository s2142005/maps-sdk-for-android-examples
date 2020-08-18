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

package com.tomtom.online.sdk.samples.ktx.cases.search.poicategories

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.search.SearchException
import com.tomtom.online.sdk.search.poicategories.PoiCategoriesCallback
import com.tomtom.online.sdk.search.poicategories.PoiCategoriesSpecification
import com.tomtom.online.sdk.search.poicategories.PoiCategory

class PoiCategoriesSearchViewModel(application: Application) : SearchViewModel(application) {

    val poiCategoriesResult = ResourceLiveData<List<PoiCategory>>()

    override fun search(term: String) {
        //Not applicable
    }

    fun searchPoiCategories() {
        if (poiCategoriesResult.value?.status != Resource.Status.SUCCESS) {
            poiCategoriesResult.value = Resource.loading(null)
            //tag::doc_create_poi_categories_specification[]
            val poiCategoriesSpecification = PoiCategoriesSpecification("en-GB")
            //end::doc_create_poi_categories_specification[]
            searchRequester.poiCategoriesSearch(poiCategoriesSpecification, poiCategoriesCallback)
        }
    }

    private val poiCategoriesCallback = object : PoiCategoriesCallback {
        override fun onSuccess(poiCategories: List<PoiCategory>) {
            poiCategoriesResult.value = Resource.success(poiCategories)
        }

        override fun onError(error: SearchException) {
            poiCategoriesResult.value = Resource.error(null, Error(error.message))
        }
    }
}