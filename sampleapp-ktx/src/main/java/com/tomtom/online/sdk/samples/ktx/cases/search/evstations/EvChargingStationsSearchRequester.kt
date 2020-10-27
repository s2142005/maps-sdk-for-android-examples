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
package com.tomtom.online.sdk.samples.ktx.cases.search.evstations

import com.google.common.collect.ImmutableList
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.search.SearchApi
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQuery
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResult
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQuery
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchResult
import com.tomtom.online.sdk.search.ev.ChargingStationsSpecification
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.SerialDisposable
import java.util.*

class EvChargingStationsSearchRequester(
    private val searchApi: SearchApi,
    private val disposable: SerialDisposable,
    private val workingScheduler: Scheduler,
    private val resultScheduler: Scheduler
) {

    fun search(
        searchQuery: AlongRouteSearchQuery,
        results: ResourceListLiveData<ChargingStationDetails>
    ) {
        results.value = Resource.loading()
        disposable.set(
            //tag::doc_ev_along_route_search[]
            searchApi.alongRouteSearch(searchQuery) // Single<AlongRouteSearchResponse>
                .flatMap { mapFromAlongRouteResultsToChargingStationsDetails(it.results) }
                //end::doc_ev_along_route_search[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                    { response -> results.value = Resource.success(response) },
                    { error -> results.value = Resource.error(Error(error.message)) }
                )
        )
    }

    fun search(
        searchQuery: GeometrySearchQuery,
        results: ResourceListLiveData<ChargingStationDetails>
    ) {
        results.value = Resource.loading()
        disposable.set(
            //tag::doc_ev_geometry_search[]
            searchApi.geometrySearch(searchQuery) // Single<GeometrySearchResponse>
                .flatMap { mapFromGeometryResultsToChargingStationsDetails(it.results) }
                //end::doc_ev_geometry_search[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                    { response -> results.value = Resource.success(response) },
                    { error -> results.value = Resource.error(Error(error.message)) }
                )
        )
    }

    fun search(
        searchQuery: FuzzySearchQuery,
        results: ResourceListLiveData<ChargingStationDetails>
    ) {
        results.value = Resource.loading()
        disposable.set(
            //tag::doc_ev_fuzzy_search[]
            searchApi.search(searchQuery) // Single<FuzzySearchResponse>
                .flatMap { mapFromFuzzyResultsToChargingStationsDetails(it.results) }
                //end::doc_ev_fuzzy_search[]
                .subscribeOn(workingScheduler)
                .observeOn(resultScheduler)
                .subscribe(
                    { response -> results.value = Resource.success(response) },
                    { error -> results.value = Resource.error(Error(error.message)) }
                )
        )
    }

    private fun mapFromFuzzyResultsToChargingStationsDetails(results: ImmutableList<FuzzySearchResult>): Single<List<ChargingStationDetails>> {
        val evSearchResults = results
            .filter { it.additionalDataSources.chargingAvailabilityDataSource.isPresent }
            .map { EvSearchResult(it.additionalDataSources, it.poi.name, it.address.freeformAddress, it.position) }
        return toChargingStationDetails(evSearchResults)
    }

    private fun mapFromAlongRouteResultsToChargingStationsDetails(results: ImmutableList<AlongRouteSearchResult>): Single<List<ChargingStationDetails>> {
        val evSearchResults = results
            .filter { it.additionalDataSources.chargingAvailabilityDataSource.isPresent }
            .map { EvSearchResult(it.additionalDataSources, it.poi.name, it.address.freeformAddress, it.position) }
        return toChargingStationDetails(evSearchResults)
    }

    private fun mapFromGeometryResultsToChargingStationsDetails(results: ImmutableList<GeometrySearchResult>): Single<List<ChargingStationDetails>> {
        val evSearchResults = results
            .filter { it.additionalDataSources.chargingAvailabilityDataSource.isPresent }
            .map { EvSearchResult(it.additionalDataSources, it.poi.name, it.address.freeformAddress, it.position) }
        return toChargingStationDetails(evSearchResults)
    }

    private fun toChargingStationDetails(evSearchResults: List<EvSearchResult>): Single<List<ChargingStationDetails>> {
        //tag::doc_ev_charging_stations_mapping[]
        return Observable.fromIterable(evSearchResults)
            .map { it.additionalDataSources.chargingAvailabilityDataSource.get().id }
            .map { searchApi.chargingStationsSearch(ChargingStationsSpecification(UUID.fromString(it))) }
            .zipWith(evSearchResults) { stations, result ->
                ChargingStationDetails(
                    result.poiName,
                    result.freeformAddress,
                    result.position,
                    stations.value().connectors
                )
            }
            .toList()
        //end::doc_ev_charging_stations_mapping[]
    }
}