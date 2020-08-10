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

import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.routing.route.information.FullRoute
import com.tomtom.online.sdk.samples.ktx.utils.geometry.DefaultGeometryCreator
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQuery
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQueryBuilder
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder
import com.tomtom.online.sdk.search.data.geometry.Geometry
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQuery
import com.tomtom.online.sdk.search.data.geometry.GeometrySearchQueryBuilder

class EvStationsQueriesFactory {

    fun createFuzzyInBoundingBoxQuery(): FuzzySearchQuery {
        return FuzzySearchQueryBuilder.create(EV_STATION_QUERY)
            .withBoundingBox(BoundingBox(Locations.AMSTERDAM_TOP_LEFT, Locations.AMSTERDAM_BOTTOM_RIGHT))
            .withCategory(true)
            .withCategorySet(listOf(CATEGORY_EV_STATION))
            .build()
    }

    fun createAlongRouteQuery(fullRoute: FullRoute): AlongRouteSearchQuery {
        return AlongRouteSearchQueryBuilder.create(
            EV_STATION_QUERY,
            fullRoute.getCoordinates(),
            MAX_DETOUR_10_MINUTES
        )
            .withCategorySet(listOf(CATEGORY_EV_STATION))
            .build()
    }

    fun createGeometryQuery(): GeometrySearchQuery {
        return GeometrySearchQueryBuilder.create(
            EV_STATION_QUERY,
            POLYGON_GEOMETRY
        ).build()
    }

    companion object {
        private const val EV_STATION_QUERY = "electric vehicle station"
        private const val CATEGORY_EV_STATION = 7309L
        private const val MAX_DETOUR_10_MINUTES = 600
        private val POLYGON_GEOMETRY: List<Geometry> = listOf(
            Geometry(DefaultGeometryCreator.createDefaultPolygonGeometry())
        )
    }
}