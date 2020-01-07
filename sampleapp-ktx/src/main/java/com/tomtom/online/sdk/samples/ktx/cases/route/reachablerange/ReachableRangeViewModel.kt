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

package com.tomtom.online.sdk.samples.ktx.cases.route.reachablerange

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tomtom.online.sdk.data.reachablerange.ReachableRangeQuery
import com.tomtom.online.sdk.data.reachablerange.ReachableRangeResponse
import com.tomtom.online.sdk.samples.ktx.cases.route.RoutingRequester
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData

class ReachableRangeViewModel(application: Application) : AndroidViewModel(application) {

    var reachableResponse = ResourceLiveData<ReachableRangeResponse>()

    fun planReachableRangeForCombustion() {
        val reachableRangeQuery = ReachableRangeQueryFactory()
                .createReachableRangeQueryForCombustion()

        planReachableRange(reachableRangeQuery)
    }

    fun planReachableRangeForElectric() {
        val reachableRangeQuery = ReachableRangeQueryFactory()
                .createReachableRangeQueryForElectric()

        planReachableRange(reachableRangeQuery)
    }

    fun planReachableRangeForElectricWithLimitedTime() {
        val reachableRangeQuery = ReachableRangeQueryFactory()
                .createReachableRangeQueryForElectricLimitTo2Hours()

        planReachableRange(reachableRangeQuery)
    }

    private fun planReachableRange(reachableRangeQuery: ReachableRangeQuery) {
        RoutingRequester(getApplication()).planReachableRange(reachableRangeQuery, reachableResponse)
    }

}
