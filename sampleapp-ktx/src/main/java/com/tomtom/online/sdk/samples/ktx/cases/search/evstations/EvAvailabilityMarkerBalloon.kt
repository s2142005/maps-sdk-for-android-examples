/**
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.search.evstations

import com.tomtom.online.sdk.map.BaseMarkerBalloon

class EvAvailabilityMarkerBalloon(name: String, address: String, type: String, availability: String) : BaseMarkerBalloon() {

    init {
        addProperty(KEY_NAME, name)
        addProperty(KEY_ADDRESS, address)
        addProperty(KEY_TYPE, type)
        addProperty(KEY_AVAILABILITY, availability)
    }

    companion object {
        internal const val KEY_NAME = "name"
        internal const val KEY_ADDRESS = "address"
        internal const val KEY_TYPE = "type"
        internal const val KEY_AVAILABILITY = "availability"
    }
}