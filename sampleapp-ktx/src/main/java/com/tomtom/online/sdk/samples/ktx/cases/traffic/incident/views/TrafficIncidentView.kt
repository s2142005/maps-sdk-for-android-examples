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
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.traffic_incident_view.view.*

class TrafficIncidentView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        FrameLayout(context, attrs, defStyleAttr) {

    private var trafficIconNumber: TextView
    private var trafficIcon: ImageView

    init {
        inflate(context, R.layout.traffic_incident_view, this)
        trafficIcon = trafficIncidentIcon
        trafficIconNumber = trafficIncidentIconNumber
    }

    internal fun setNumberInsideTrafficIcon(numberOfIncidentsInCluster: Int?) {
        trafficIconNumber.text = numberOfIncidentsInCluster?.toString()
    }

    fun setTrafficIncidentIcon(icon: Drawable) {
        trafficIcon.setImageDrawable(icon)
    }

    internal fun disableIncidentsCounter() {
        trafficIconNumber.text = context.getString(R.string.empty_string)
    }

}
