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

package com.tomtom.online.sdk.samples.ktx.cases.map.traffic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class TrafficLayersViewModel(application: Application) : AndroidViewModel(application) {

    var isFlowOn: MutableLiveData<Boolean> = MutableLiveData()
    var isIncidentOn: MutableLiveData<Boolean> = MutableLiveData()
    var isFlowOrIncidentOn: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isFlowOn.value = false
        isIncidentOn.value = false
        isFlowOrIncidentOn.value = false
    }

    fun setFlowOn(on: Boolean) {
        isFlowOn.value = on
        refreshIsFlowOrIncidentOn()
    }

    fun setIncidentOn(on: Boolean) {
        isIncidentOn.value = on
        refreshIsFlowOrIncidentOn()
    }

    private fun refreshIsFlowOrIncidentOn() {
        isFlowOrIncidentOn.value = isFlowOn.value!! || isIncidentOn.value!!
    }

}