/**
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.search.openinghours

import com.tomtom.online.sdk.map.BaseMarkerBalloon

class OpeningHoursMarkerBalloon(title: String, weekdays: String, hours: String) : BaseMarkerBalloon() {

    init {
        addProperty(KEY_TITLE, title)
        addProperty(KEY_TEXT_WEEKDAYS, weekdays)
        addProperty(KEY_TEXT_HOURS, hours)
    }

    companion object {
        private const val serialVersionUID = -3794201955202222698L
        internal const val KEY_TITLE = "title"
        internal const val KEY_TEXT_WEEKDAYS = "weekdays"
        internal const val KEY_TEXT_HOURS = "hours"
    }

}