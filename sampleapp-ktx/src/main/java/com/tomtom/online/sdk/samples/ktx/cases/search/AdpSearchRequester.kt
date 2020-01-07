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
import com.google.common.base.Optional
import com.tomtom.online.sdk.common.rx.RxContext
import com.tomtom.online.sdk.samples.ktx.cases.search.revgeopolygons.RevGeoWithAdpResponse
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.search.OnlineSearchApi
import com.tomtom.online.sdk.search.data.additionaldata.AdditionalDataSearchQueryBuilder
import com.tomtom.online.sdk.search.data.additionaldata.AdditionalDataSearchResponse
import com.tomtom.online.sdk.search.data.additionaldata.result.AdditionalDataSearchResult
import com.tomtom.online.sdk.search.data.common.additionaldata.GeometryDataSource
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderFullAddress
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQuery
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers

//RxJava and Kotlin null handling not working well together
//Therefore, there is a need to use optionals in the below pipelines
//tag::doc_adp_search_requester[]
class AdpSearchRequester(context: Context, private val geometriesZoom: Int) : RxContext {

    private val disposable = SerialDisposable()
    private val searchApi = OnlineSearchApi.create(context)!!

    fun fuzzyWithAdp(searchQuery: FuzzySearchQuery, resource: ResourceLiveData<AdditionalDataSearchResult>) {
        resource.value = Resource.loading(null)
        disposable.set(searchApi.search(searchQuery)
            .subscribeOn(workingScheduler)
            .filter { nonEmptySearchResults(it) }
            .map { firstSearchResultWithGeometryDataSource(it) }
            .filter { presentSearchResult(it) }
            .map { firstDataSourceForSearchResult(it) }
            .flatMap { performAdp(it) }
            .observeOn(resultScheduler)
            .subscribe(
                { response -> resource.value = Resource.success(response.results.first()) },
                { error -> resource.value = Resource.error(null, Error(error.message)) }
            ))
    }

    private fun firstDataSourceForSearchResult(searchResult: Optional<FuzzySearchResult>) =
        searchResult.get().additionalDataSources.geometryDataSource.get()

    private fun presentSearchResult(searchResult: Optional<FuzzySearchResult>) =
        searchResult.isPresent

    private fun nonEmptySearchResults(searchResponse: FuzzySearchResponse) =
        searchResponse.results.isEmpty().not()

    private fun firstSearchResultWithGeometryDataSource(searchResponse: FuzzySearchResponse): Optional<FuzzySearchResult> {
        return Optional.fromNullable(searchResponse.results.find { item ->
            item.additionalDataSources?.geometryDataSource!!.isPresent
        })
    }

    fun revGeoWithAdp(revGeoQuery: ReverseGeocoderSearchQuery, resource: ResourceLiveData<RevGeoWithAdpResponse>) {
        val result = RevGeoWithAdpResponse()
        resource.value = Resource.loading(null)

        disposable.set(searchApi.reverseGeocoding(revGeoQuery)
            .subscribeOn(workingScheduler)
            .filter { nonEmptyRevGeoResults(it) }
            .map { firstRevGeoResultWithGeometryDataSource(it) }
            .map {
                result.revGeoResult = it.orNull()
                firstDataSourceForRevGeoResult(it)
            }
            .filter { nonEmptyGeometryDataSource(it) }
            .map { it.get() }
            .flatMap { performAdp(it) }
            .observeOn(resultScheduler)
            .subscribe(
                { response ->
                    result.adpResult = response.results
                    resource.value = Resource.success(result)
                },
                { error -> resource.value = Resource.error(null, Error(error.message)) }
            )
        )
    }

    private fun nonEmptyGeometryDataSource(dataSource: Optional<GeometryDataSource>) =
        dataSource.isPresent

    private fun nonEmptyRevGeoResults(response: ReverseGeocoderSearchResponse) =
        response.hasResults()

    private fun firstRevGeoResultWithGeometryDataSource(revGeoResponse: ReverseGeocoderSearchResponse): Optional<ReverseGeocoderFullAddress> =
        Optional.fromNullable(revGeoResponse.addresses.find { item ->
            item.additionalDataSources?.geometryDataSource!!.isPresent
        })

    private fun firstDataSourceForRevGeoResult(it: Optional<ReverseGeocoderFullAddress>): Optional<GeometryDataSource> {
        it.orNull()?.let {
            return it.additionalDataSources!!.geometryDataSource!!
        } ?: run {
            return Optional.absent<GeometryDataSource>()
        }
    }

    private fun performAdp(dataSource: GeometryDataSource): Maybe<AdditionalDataSearchResponse> {
        val adpQuery = AdditionalDataSearchQueryBuilder.create()
            .withGeometryDataSource(dataSource)
            .withGeometriesZoom(geometriesZoom)
        val request = searchApi.additionalDataSearch(adpQuery.build())
        return request.toMaybe()
    }

    override fun getWorkingScheduler() = Schedulers.newThread()

    override fun getResultScheduler() = AndroidSchedulers.mainThread()!!

}
//end::doc_adp_search_requester[]