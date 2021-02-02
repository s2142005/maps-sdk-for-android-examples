/*
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */

@file:Suppress("SimpleRedundantLet")

package com.tomtom.online.sdk.samples.ktx.cases.driving.matching

import android.app.Application
import android.location.Location
import com.tomtom.online.sdk.map.driving.MatchResult
import com.tomtom.online.sdk.map.driving.Matcher
import com.tomtom.online.sdk.map.driving.MatcherFactory
import com.tomtom.online.sdk.map.driving.MatcherListener
import com.tomtom.online.sdk.matching.MatchingDataProvider
import com.tomtom.online.sdk.samples.ktx.cases.driving.ChevronMatchedStateUpdate
import com.tomtom.online.sdk.samples.ktx.cases.driving.DrivingViewModel
import com.tomtom.online.sdk.samples.ktx.utils.driving.BaseSimulator

abstract class MatchingViewModel(application: Application) : DrivingViewModel(application), MatcherListener,
    BaseSimulator.SimulatorCallback {

    abstract var simulator: com.tomtom.online.sdk.samples.ktx.utils.driving.Simulator
    private var matcher: Matcher? = null
    private var lastVisitedLocationIdx = INITIAL_VISITED_LOCATION_IDX

    fun createMatcher(matchingDataProvider: MatchingDataProvider) {
        //tag::doc_create_matcher[]
        val matcher = MatcherFactory.createMatcher(matchingDataProvider)
        matcher.setMatcherListener(this)
        //end::doc_create_matcher[]
        this.matcher = matcher
    }

    fun stopSimulation() {
        lastVisitedLocationIdx = simulator.stop()
    }

    override fun onMatched(matchResult: MatchResult) {
        chevronState.value = ChevronMatchedStateUpdate(
            isDimmed = !matchResult.isMatched,
            matchedLocation = matchResult.matchedLocation,
            originalLocation = matchResult.originalLocation
        )
    }

    override fun onNewRoutePointVisited(location: Location) {
        matcher?.let { matcher ->
            //tag::doc_update_map_matcher[]
            matcher.match(location)
            //end::doc_update_map_matcher[]
        }
    }

    fun disposeMatcher() {
        matcher?.let { matcher ->
            //tag::doc_dispose_map_matcher[]
            matcher.dispose()
            //end::doc_dispose_map_matcher[]
        }
    }

    companion object {
        internal const val INITIAL_VISITED_LOCATION_IDX = 0
    }
}

