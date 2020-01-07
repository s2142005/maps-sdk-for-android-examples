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

package com.tomtom.online.sdk.samples.ktx.utils.arch

import androidx.lifecycle.Observer
import timber.log.Timber

class ResourceObserver<T>(private val hideLoading: () -> Unit,
                          private val showLoading: () -> Unit,
                          private val onSuccess: (data: T) -> Unit,
                          private val onError: (message: String?) -> Unit) : Observer<Resource<T>> {

    override fun onChanged(resource: Resource<T>?) {
        when (resource?.status) {
            Resource.Status.SUCCESS -> {
                hideLoading()
                if (resource.data != null) {
                    Timber.d("observer -> SUCCESS, ${resource.data} items")
                    onSuccess(resource.data)
                }
            }
            Resource.Status.ERROR -> {
                hideLoading()
                if (resource.error != null) {
                    Timber.d("observer -> ERROR, ${resource.error}")
                    onError(resource.error.message)
                }
            }
            Resource.Status.LOADING -> {
                showLoading()
                Timber.d("observer -> LOADING")
            }
        }
    }
}