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
package com.tomtom.online.sdk.samples.ktx.cases.traffic.incident.utils

import android.content.Context
import com.tomtom.online.sdk.samples.ktx.cases.traffic.incident.model.TrafficIncidentItem
import com.tomtom.online.sdk.traffic.api.incident.icons.TrafficIncidentIconProvider
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncident
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncidentCluster
import com.tomtom.sdk.examples.R

class TrafficIncidentItemCreator(private val context: Context) {

    fun createSingleIncident(incident: TrafficIncident): TrafficIncidentItem {
        //tag::doc_traffic_incident_icon_provider[]
        val provider = TrafficIncidentIconProvider(incident)
        val incidentDrawable = provider.getIcon(context, TrafficIncidentIconProvider.IconSize.LARGE)
        //end::doc_traffic_incident_icon_provider[]

        return TrafficIncidentItem(
                drawable = incidentDrawable,
                description = String.format(INCIDENT_DESCRIPTION_FORMAT, incident.from, incident.to),
                delay = incident.delay.or(DEFAULT_INCIDENT_DELAY),
                length = incident.lengthMeters)
    }

    fun createClusterOfIncidents(cluster: TrafficIncidentCluster): TrafficIncidentItem {
        val provider = TrafficIncidentIconProvider(cluster)
        val incidentDrawable = provider.getIcon(context, TrafficIncidentIconProvider.IconSize.LARGE)

        return TrafficIncidentItem(
                drawable = incidentDrawable,
                description = context.getString(R.string.traffic_incident_cluster_description),
                delay = DEFAULT_INCIDENT_DELAY,
                length = cluster.lengthMeters,
                numberOfIncidentsInCluster = cluster.incidents.size,
                isCluster = true)
    }

    companion object {
        private const val INCIDENT_DESCRIPTION_FORMAT = "%s / %s"
        private const val DEFAULT_INCIDENT_DELAY = 0
    }

}