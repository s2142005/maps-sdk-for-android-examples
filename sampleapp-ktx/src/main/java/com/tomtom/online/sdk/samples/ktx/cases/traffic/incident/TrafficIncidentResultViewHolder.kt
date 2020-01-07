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
package com.tomtom.online.sdk.samples.ktx.cases.traffic.incident

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tomtom.online.sdk.common.util.TimeLengthFormatter
import com.tomtom.online.sdk.samples.ktx.cases.traffic.incident.model.TrafficIncidentItem
import com.tomtom.online.sdk.samples.ktx.utils.formatter.DistanceFormatter
import kotlinx.android.synthetic.main.traffic_incident_list_item.view.*

class TrafficIncidentResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val incidentView = itemView.trafficIncidentView
    private val descriptionTv = itemView.trafficIncidentDescription
    private val delayTv = itemView.trafficIncidentDelay
    private val lengthTv = itemView.trafficIncidentLength

    fun bind(trafficIncident: TrafficIncidentItem) {
        trafficIncident.drawable?.let { drawable ->
            incidentView.setTrafficIncidentIcon(drawable)
            setNumberOnIcon(trafficIncident)
        }
        descriptionTv.text = trafficIncident.description
        delayTv.text = TimeLengthFormatter(itemView.context).format(trafficIncident.delay.toLong())
        lengthTv.text = DistanceFormatter.format(trafficIncident.length)
    }

    private fun setNumberOnIcon(item: TrafficIncidentItem) = if (item.isCluster) {
        incidentView.setNumberInsideTrafficIcon(item.numberOfIncidentsInCluster)
    } else {
        incidentView.disableIncidentsCounter()
    }

}