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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomtom.online.sdk.samples.ktx.cases.traffic.incident.model.TrafficIncidentItem
import com.tomtom.sdk.examples.R

class TrafficIncidentResultsAdapter : RecyclerView.Adapter<TrafficIncidentResultViewHolder>() {

    private var trafficIncidentResults: MutableList<TrafficIncidentItem> = mutableListOf()

    fun updateData(searchResults: MutableList<TrafficIncidentItem>) {
        trafficIncidentResults = searchResults
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafficIncidentResultViewHolder =
            TrafficIncidentResultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.traffic_incident_list_item, parent, false))

    override fun getItemCount(): Int = trafficIncidentResults.size

    override fun onBindViewHolder(holder: TrafficIncidentResultViewHolder, position: Int) {
        holder.bind(trafficIncidentResults[position])
    }
}
