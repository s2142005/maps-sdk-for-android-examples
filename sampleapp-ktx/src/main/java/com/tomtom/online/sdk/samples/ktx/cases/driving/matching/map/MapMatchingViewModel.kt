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

package com.tomtom.online.sdk.samples.ktx.cases.driving.matching.map

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
import com.tomtom.online.sdk.samples.ktx.utils.driving.GpsCsvSimulator
import com.tomtom.online.sdk.samples.ktx.utils.driving.Simulator
import com.tomtom.online.sdk.samples.ktx.utils.driving.interpolator.BasicInterpolator

class MapMatchingViewModel(application: Application) : DrivingViewModel(application), MatcherListener, BaseSimulator.SimulatorCallback {

    private val simulator: Simulator = GpsCsvSimulator(getApplication(), BasicInterpolator())
    private var lastVisitedLocationIdx = INITIAL_VISITED_LOCATION_IDX
    private lateinit var matcher: Matcher

    fun createMatcher(matchingDataProvider: MatchingDataProvider) {
        //tag::doc_create_map_matcher[]
        matcher = MatcherFactory.createMatcher(matchingDataProvider)
        matcher.setMatcherListener(this)
        //end::doc_create_map_matcher[]
    }

    fun startOrResumeSimulation() {
        if (lastVisitedLocationIdx == INITIAL_VISITED_LOCATION_IDX) {
            simulator.play(this)
        } else {
            simulator.resume(this, lastVisitedLocationIdx)
        }
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
        //tag::doc_update_map_matcher[]
        matcher.match(location)
        //end::doc_update_map_matcher[]
    }

    fun disposeMatcher() {
        //tag::doc_dispose_map_matcher[]
        matcher.dispose()
        //end::doc_dispose_map_matcher[]
    }

    companion object {
        private const val INITIAL_VISITED_LOCATION_IDX = 0
    }

}

