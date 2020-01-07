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

package com.tomtom.online.sdk.samples.ktx.cases.search.batch

import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.data.batch.BatchSearchQuery
import com.tomtom.online.sdk.search.data.batch.BatchSearchQueryBuilder
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder
import com.tomtom.online.sdk.search.data.geometry.Geometry
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQuery
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQueryBuilder
import com.tomtom.online.sdk.search.data.geometry.query.CircleGeometry
import java.util.*

class BatchSearchQueriesFactory {

    fun createBatchSearchQuery(category: String): BatchSearchQuery {
        //tag::doc_batch_search_query[]
        //Using batch, it is possible to execute different search types:
        //fuzzy, geometry or reverse geocoding. The order of responses
        //is the same as the order in which the queries are added.
        val batchQuery = BatchSearchQueryBuilder()
                .withFuzzySearchQuery(createAmsterdamQuery(category))
                .withFuzzySearchQuery(createHaarlemQuery(category))
                .withGeometrySearchQuery(createHoofddropQuery(category))
                .build()
        //end::doc_batch_search_query[]
        return batchQuery
    }

    private fun getBaseFuzzyQuery(category: String): FuzzySearchQueryBuilder {
        return FuzzySearchQueryBuilder.create(category)
                .withCategory(true)
    }

    private fun createAmsterdamQuery(category: String): FuzzySearchQuery {
        val query = getBaseFuzzyQuery(category)
        query.withPosition(Locations.AMSTERDAM_CENTER)
        query.withLimit(10)
        return query.build()
    }

    private fun createHaarlemQuery(category: String): FuzzySearchQuery {
        val query = getBaseFuzzyQuery(category)
        query.withPosition(Locations.AMSTERDAM_HAARLEM)
        query.withLimit(15)
        return query.build()
    }

    private fun createHoofddropQuery(category: String): GeometrySearchQuery {
        val geometries = ArrayList<Geometry>()
        val circleGeometry = CircleGeometry(Locations.HOOFDDORP, GEOMETRY_RADIUS)
        val geometry = Geometry(circleGeometry)
        geometries.add(geometry)
        return GeometrySearchQueryBuilder(category, geometries).build()
    }

    companion object {
        private const val GEOMETRY_RADIUS = 4000
    }
}