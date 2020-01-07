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
package com.tomtom.online.sdk.samples.ktx.cases.traffic.incident.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.traffic_incident_list_header.view.*

class TrafficIncidentsListHeader @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private var trafficIconDesc: TextView
    private var trafficDescription: TextView
    private var trafficDelay: TextView
    private var trafficLength: TextView

    init {
        inflate(context, R.layout.traffic_incident_list_header, this)
        trafficIconDesc = trafficIncidentIconDesc
        trafficDescription = trafficIncidentDescription
        trafficDelay = trafficIncidentDelay
        trafficLength = trafficIncidentLength
        setupHeaderLabels()
    }

    private fun setupHeaderLabels() {
        trafficIconDesc.setText(R.string.traffic_incident_list_header_category)
        trafficDescription.setText(R.string.traffic_incident_list_header_desc)
        trafficDelay.setText(R.string.traffic_incident_list_header_delay)
        trafficLength.setText(R.string.traffic_incident_list_header_length)
    }

}
