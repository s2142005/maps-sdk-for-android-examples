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
package com.tomtom.online.sdk.samples.ktx.cases.search.geometry

import android.app.Application
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.samples.ktx.cases.search.geometry.DefaultGeometryCreator.createDefaultCircleGeometry
import com.tomtom.online.sdk.samples.ktx.cases.search.geometry.DefaultGeometryCreator.createDefaultPolygonGeometry
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceListLiveData
import com.tomtom.online.sdk.search.data.geometry.Geometry
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQuery
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQueryBuilder
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchResult

class GeometrySearchViewModel(application: Application) : SearchViewModel(application) {

    val results = ResourceListLiveData<GeometrySearchResult>()

    override fun search(query: String) {
    }

    fun searchForParking() {
        val geometryQuery = geometrySearchPreparer(GEOMETRY_SEARCH_PARKING)
        searchRequester.geometrySearch(geometryQuery, results)
    }

    fun searchForAtm() {
        val geometryQuery = geometrySearchPreparer(GEOMETRY_SEARCH_ATM)
        searchRequester.geometrySearch(geometryQuery, results)
    }

    fun searchForGrocery() {
        val geometryQuery = geometrySearchPreparer(GEOMETRY_SEARCH_GROCERY)
        searchRequester.geometrySearch(geometryQuery, results)
    }

    private fun geometrySearchPreparer(term: String): GeometrySearchQuery {
        //tag::doc_geometries_creation[]
        val circleGeometry = createDefaultCircleGeometry()
        val polygonGeometry = createDefaultPolygonGeometry()
        val geometriesList = listOf(Geometry(circleGeometry), Geometry(polygonGeometry))

        val query = GeometrySearchQueryBuilder.create(term, geometriesList)
            .withLimit(SEARCH_RESULTS_LIMIT)
            .build()
        //end::doc_geometries_creation[]
        return query
    }

    companion object {
        private const val GEOMETRY_SEARCH_PARKING = "Parking"
        private const val GEOMETRY_SEARCH_ATM = "Atm"
        private const val GEOMETRY_SEARCH_GROCERY = "Grocery"
        private const val SEARCH_RESULTS_LIMIT = 50
    }

}
