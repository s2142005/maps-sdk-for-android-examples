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

package com.tomtom.online.sdk.samples.ktx.cases.search.address

import android.app.Application
import com.tomtom.online.sdk.common.location.LatLngBias
import com.tomtom.online.sdk.samples.ktx.cases.search.SearchViewModel
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification
import com.tomtom.online.sdk.search.fuzzy.FuzzyLocationDescriptor

class AddressSearchViewModel(application: Application) : SearchViewModel(application) {

    private var globalSearch = true

    override fun search(term: String) {
        val searchSpecification = if (globalSearch) createGlobalSpecification(term) else createLocalSpecification(term)
        search(searchSpecification)
    }

    private fun createLocalSpecification(term: String): FuzzySearchSpecification {
        return addPreciseness()?.let { preciseness ->
            //tag::doc_create_specification_with_position[]
            val locationDescriptor = FuzzyLocationDescriptor.Builder()
                .positionBias(preciseness)
                .build()
            return FuzzySearchSpecification.Builder(term)
                .locationDescriptor(locationDescriptor)
                .build()
            //end::doc_create_specification_with_position[]
        } ?: FuzzySearchSpecification.Builder(term).build()
    }

    private fun createGlobalSpecification(term: String): FuzzySearchSpecification {
        return addPosition()?.let { position ->
            val locationDescriptor = FuzzyLocationDescriptor.Builder()
                .positionBias(LatLngBias(position))
                .build()
            return FuzzySearchSpecification.Builder(term)
                .locationDescriptor(locationDescriptor)
                .build()
        } ?: FuzzySearchSpecification.Builder(term).build()
    }

    fun enableGlobalSearch() {
        globalSearch = true
    }

    fun enableLocalSearch() {
        globalSearch = false
    }

    @Suppress("unused")
    private fun createBasicSpecification(text: String): FuzzySearchSpecification {
        return (
            //tag::doc_create_basic_specification[]
            FuzzySearchSpecification.Builder(text).build()
            //end::doc_create_basic_specification[]
            )
    }
}
