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
package com.tomtom.online.sdk.samples.ktx.cases.route.ev

import android.view.View
import android.widget.TextView
import com.tomtom.online.sdk.map.BaseMarkerBalloon
import com.tomtom.online.sdk.map.Marker
import com.tomtom.online.sdk.map.SingleLayoutBalloonViewAdapter
import com.tomtom.online.sdk.samples.ktx.cases.route.ev.LongEvMarkerBalloon.Companion.KEY_TEXT_CHARGING_TIME
import com.tomtom.sdk.examples.R

class LongEvBalloonViewAdapter : SingleLayoutBalloonViewAdapter(R.layout.ev_charging_station_balloon_layout) {

    override fun onBindView(view: View, marker: Marker, markerBalloon: BaseMarkerBalloon) {
        (view.findViewById(R.id.ev_waiting_time) as TextView).text =
            markerBalloon.getStringProperty(KEY_TEXT_CHARGING_TIME)
    }
}