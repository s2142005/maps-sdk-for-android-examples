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
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomtom.online.sdk.location.LocationSource
import com.tomtom.online.sdk.location.LocationSourceFactory
import com.tomtom.online.sdk.location.LocationUpdateListener

class LocationViewModel(application: Application) : AndroidViewModel(application), LocationUpdateListener {

    private val locationProvider: LocationSource = LocationSourceFactory().createDefaultLocationSource(application, this)
    private var lastKnownLocation = MutableLiveData<Location>()

    override fun onLocationChanged(location: Location?) {
        lastKnownLocation.value = location
    }

    fun startLocationUpdates() = locationProvider.activate()

    fun stopLocationUpdates() = locationProvider.deactivate()

    fun lastKnownLocation(): LiveData<Location> = lastKnownLocation

    fun lastKnownLocationOrNull(): Location? = lastKnownLocation.value

}