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

import androidx.lifecycle.MutableLiveData

/**
 * Used to hide the boilerplate code LiveData<Resource<T>>.
 * Then, you just use ResourceLiveData<T>.
 */
class ResourceLiveData<T> : MutableLiveData<Resource<T>>()

/**
 * Used to hide the boilerplate code LiveData<Resource<<ListT>>>.
 * Then, you just use ResourceLiveData<T>, and then use List<>.
 */
class ResourceListLiveData<T> : MutableLiveData<Resource<List<T>>>()