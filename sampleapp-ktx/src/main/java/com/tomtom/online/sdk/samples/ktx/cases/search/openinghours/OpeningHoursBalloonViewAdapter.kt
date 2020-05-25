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

import android.view.View
import android.widget.TextView
import com.tomtom.online.sdk.map.BaseMarkerBalloon
import com.tomtom.online.sdk.map.Marker
import com.tomtom.online.sdk.map.R
import com.tomtom.online.sdk.map.SingleLayoutBalloonViewAdapter
import com.tomtom.online.sdk.samples.ktx.cases.search.openinghours.OpeningHoursMarkerBalloon.Companion.KEY_TEXT_HOURS
import com.tomtom.online.sdk.samples.ktx.cases.search.openinghours.OpeningHoursMarkerBalloon.Companion.KEY_TEXT_WEEKDAYS
import com.tomtom.online.sdk.samples.ktx.cases.search.openinghours.OpeningHoursMarkerBalloon.Companion.KEY_TITLE

class OpeningHoursBalloonViewAdapter : SingleLayoutBalloonViewAdapter(R.layout.opening_hours_balloon_layout) {

    override fun onBindView(view: View, marker: Marker, markerBalloon: BaseMarkerBalloon) {
        (view.findViewById(R.id.title) as TextView).text = markerBalloon.getStringProperty(KEY_TITLE)
        (view.findViewById(R.id.text_weekdays) as TextView).text = markerBalloon.getStringProperty(KEY_TEXT_WEEKDAYS)
        (view.findViewById(R.id.text_hours) as TextView).text = markerBalloon.getStringProperty(KEY_TEXT_HOURS)
    }
}