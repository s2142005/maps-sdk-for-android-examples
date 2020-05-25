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
package com.tomtom.online.sdk.samples.ktx.cases.search

import android.content.Context
import com.tomtom.online.sdk.common.rx.RxContext
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.search.OnlineSearchApi
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQuery
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResult
import com.tomtom.online.sdk.search.data.autocomplete.AutocompleteSearchQuery
import com.tomtom.online.sdk.search.data.autocomplete.AutocompleteSearchResponse
import com.tomtom.online.sdk.search.data.batch.BatchSearchQuery
import com.tomtom.online.sdk.search.data.batch.BatchSearchResponse
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQuery
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchResult
import com.tomtom.online.sdk.search.data.poicategories.PoiCategoriesQuery
import com.tomtom.online.sdk.search.data.poicategories.PoiCategoriesResponse
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderFullAddress
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQuery
import com.tomtom.online.sdk.search.extensions.SearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SearchRequester(context: Context) : RxContext {

    private val disposable = SerialDisposable()
    //tag::doc_create_search_object[]
    private val searchApi = OnlineSearchApi.create(context)!!
    //end::doc_create_search_object[]

    fun search(searchQuery: FuzzySearchQuery, results: ResourceListLiveData<FuzzySearchResult>) {
        results.value = Resource.loading(null)
        disposable.set(searchApi.search(searchQuery)
            .subscribeOn(workingScheduler)
            .observeOn(resultScheduler)
            .subscribe(
                { response -> results.value = Resource.success(response.results) },
                { error -> results.value = Resource.error(Error(error.message)) }
            )
        )
    }

    fun revGeo(revGeoQuery: ReverseGeocoderSearchQuery, results: ResourceListLiveData<ReverseGeocoderFullAddress>) {
        results.value = Resource.loading(null)
        disposable.set(
            //tag::doc_reverse_geocoding_request[]
            searchApi.reverseGeocoding(revGeoQuery)
                //end::doc_reverse_geocoding_request[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                    { response -> results.value = Resource.success(response.addresses) },
                    { error -> results.value = Resource.error(Error(error.message)) }
                ))
    }

    fun alongRoute(query: AlongRouteSearchQuery, results: ResourceListLiveData<AlongRouteSearchResult>) {
        results.value = Resource.loading(null)
        disposable.set(
            //tag::doc_search_along_route_request[]
            searchApi.alongRouteSearch(query)
                //end::doc_search_along_route_request[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                    { response -> results.value = Resource.success(response.results) },
                    { error -> results.value = Resource.error(Error(error.message)) }
                ))
    }

    fun geometrySearch(geometryQuery: GeometrySearchQuery, results: ResourceListLiveData<GeometrySearchResult>) {
        results.value = Resource.loading(null)
        disposable.set(
            //tag::doc_geometry_search_request[]
            searchApi.geometrySearch(geometryQuery)
                //end::doc_geometry_search_request[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                    { response -> results.value = Resource.success(response.results) },
                    { error -> results.value = Resource.error(Error(error.message)) }
                ))
    }

    fun batchSearch(batchSearchQuery: BatchSearchQuery, results: ResourceLiveData<BatchSearchResponse>) {
        results.value = Resource.loading(null)
        disposable.set(
            //tag::doc_batch_search_request[]
            searchApi.batchSearch(batchSearchQuery)
                //end::doc_batch_search_request[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                    { response -> results.value = Resource.success(response) },
                    { error -> results.value = Resource.error(Error(error.message)) }
                )
        )
    }

    fun poiCategoriesSearch(poiCategoriesQuery: PoiCategoriesQuery, results: ResourceLiveData<PoiCategoriesResponse>) {
        results.value = Resource.loading(null)
        disposable.set(
            //tag::doc_poi_categories_search_request[]
            searchApi.poiCategoriesSearch(poiCategoriesQuery)
                //end::doc_poi_categories_search_request[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                    { response -> results.value = Resource.success(response) },
                    { error -> results.value = Resource.error(Error(error.message)) }
                )
        )
    }

    fun autocompleteSearch(autocompleteQuery: AutocompleteSearchQuery, results: ResourceLiveData<AutocompleteSearchResponse>) {
        results.value = Resource.loading(null)
        disposable.set(
            //tag::doc_autocomplete_search_request[]
            searchApi.autocompleteSearch(autocompleteQuery)
                //end::doc_autocomplete_search_request[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                    { response -> results.value = Resource.success(response) },
                    { error -> results.value = Resource.error(Error(error.message)) }
                )
        )
    }

    @Suppress("unused")
    private fun exampleOfUsingSearchService(query: FuzzySearchQuery, searchService: SearchService) {
        //tag::doc_create_search_callback[]
        val observer = object : DisposableSingleObserver<FuzzySearchResponse>() {
            override fun onSuccess(fuzzySearchResponse: FuzzySearchResponse) {
                Resource.success(fuzzySearchResponse.results)
            }

            override fun onError(throwable: Throwable) {
                Resource.error(null, Error(throwable.message))
            }
        }
        //end::doc_create_search_callback[]

        //tag::doc_perform_search[]
        searchService.search(query)
            .subscribeOn(workingScheduler)
            .observeOn(resultScheduler)
            .subscribe(observer)
        //end::doc_perform_search[]
    }

    override fun getWorkingScheduler() = Schedulers.newThread()

    override fun getResultScheduler() = AndroidSchedulers.mainThread()!!

    companion object {
        const val STANDARD_RADIUS = 30000F
    }

}