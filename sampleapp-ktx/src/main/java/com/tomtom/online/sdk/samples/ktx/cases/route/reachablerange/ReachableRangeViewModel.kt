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

package com.tomtom.online.sdk.samples.ktx.cases.route.reachablerange

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.reachablerange.ReachableAreaCallback
import com.tomtom.online.sdk.routing.reachablerange.ReachableRangeArea
import com.tomtom.online.sdk.routing.reachablerange.ReachableRangeSpecification
import com.tomtom.online.sdk.samples.ktx.cases.route.RoutingRequester
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData

class ReachableRangeViewModel(application: Application) : AndroidViewModel(application) {
    var reachableRangeArea = ResourceLiveData<ReachableRangeArea>()

    fun planReachableRangeForCombustion() {
        val reachableRangeSpecification = ReachableRangeSpecificationFactory()
            .createReachableRangeSpecificationForCombustion()

        planReachableRange(reachableRangeSpecification)
    }

    fun planReachableRangeForElectric() {
        val reachableRangeSpecification = ReachableRangeSpecificationFactory()
            .createReachableRangeSpecificationForElectric()

        planReachableRange(reachableRangeSpecification)
    }

    fun planReachableRangeForElectricWithLimitedTime() {
        val reachableRangeSpecification = ReachableRangeSpecificationFactory()
            .createReachableRangeSpecificationForElectricLimitTo2Hours()

        planReachableRange(reachableRangeSpecification)
    }

    private fun planReachableRange(reachableRangeSpecification: ReachableRangeSpecification) {
        reachableRangeArea.value = Resource.loading(null)

        RoutingRequester(getApplication())
            .planReachableRange(reachableRangeSpecification, reachableAreaCallback)
    }

    //tag::doc_reachable_range_result_listener[]
    private val reachableAreaCallback = object : ReachableAreaCallback {
        override fun onSuccess(reachableArea: ReachableRangeArea) {
            reachableRangeArea.value = Resource.success(reachableArea)
        }

        override fun onError(error: RoutingException) {
            reachableRangeArea.value = Resource.error(null, Error(error.message))
        }
    }
    //end::doc_reachable_range_result_listener[]
}
