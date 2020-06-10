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

package com.tomtom.online.sdk.samples.ktx

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomtom.online.sdk.samples.ktx.utils.arch.SingleLiveEvent
import com.tomtom.sdk.examples.R

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mapAction: SingleLiveEvent<MapAction> = SingleLiveEvent()
    private var isAboutButtonVisible: MutableLiveData<Boolean> = MutableLiveData()
    private var isMapFragmentVisible: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isAboutButtonVisible.value = true
        isMapFragmentVisible.value = true
    }

    fun applyOnMap(action: MapAction) {
        mapAction.value = action
    }

    fun mapAction(): LiveData<MapAction> {
        return mapAction
    }

    fun applyAboutButtonVisibility(visible: Boolean) {
        isAboutButtonVisible.value = visible
    }

    fun aboutButtonVisibility(): LiveData<Boolean> {
        return isAboutButtonVisible
    }


    fun applyMapFragmentVisibility(visible: Boolean) {
        isMapFragmentVisible.value = visible
    }

    fun mapFragmentVisibility(): LiveData<Boolean> {
        return isMapFragmentVisible
    }

    fun adjustCompassTopMarginForInfoBar(topMargin: Int = R.dimen.compass_with_bar_margin_top) {
        setCompassMargins(topMargin = topMargin)
    }

    fun resetCompassMargins() {
        setCompassMargins()
    }

    fun adjustCurrentLocationViewMargins() {
        setCurrentLocationMargins(bottomMargin = R.dimen.current_location_large_margin_bottom)
    }

    fun resetCurrentLocationViewMargins() {
        setCurrentLocationMargins()
    }

    private fun setCompassMargins(leftMargin: Int = R.dimen.compass_default_margin_start,
                                  topMargin: Int = R.dimen.compass_default_margin_top,
                                  rightMargin: Int = R.dimen.compass_no_margin,
                                  bottomMargin: Int = R.dimen.compass_no_margin) {

        val resources = getApplication<Application>().resources

        applyOnMap(MapAction {
            uiSettings.compassView.setMargins(
                resources.getDimensionPixelSize(leftMargin),
                resources.getDimensionPixelSize(topMargin),
                resources.getDimensionPixelSize(rightMargin),
                resources.getDimensionPixelSize(bottomMargin))
        })
    }

    private fun setCurrentLocationMargins(leftMargin: Int = R.dimen.current_location_default_margin_start,
                                          topMargin: Int = R.dimen.current_location_no_margin,
                                          rightMargin: Int = R.dimen.current_location_no_margin,
                                          bottomMargin: Int = R.dimen.current_location_default_margin_bottom) {

        val resources = getApplication<Application>().resources

        applyOnMap(MapAction {
            uiSettings.currentLocationView.setMargins(
                resources.getDimensionPixelSize(leftMargin),
                resources.getDimensionPixelSize(topMargin),
                resources.getDimensionPixelSize(rightMargin),
                resources.getDimensionPixelSize(bottomMargin))
        })
    }
}