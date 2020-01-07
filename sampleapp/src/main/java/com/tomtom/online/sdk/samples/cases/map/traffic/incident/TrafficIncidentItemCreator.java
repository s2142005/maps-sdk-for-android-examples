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
package com.tomtom.online.sdk.samples.cases.map.traffic.incident;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.map.traffic.incident.model.TrafficIncidentItem;
import com.tomtom.online.sdk.traffic.api.incident.icons.TrafficIncidentIconProvider;
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncident;
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncidentCluster;

class TrafficIncidentItemCreator {

    private static final String INCIDENT_DESCRIPTION_FORMAT = "%s / %s";

    private Context context;

    TrafficIncidentItemCreator(Context context) {
        this.context = context;
    }

    TrafficIncidentItem createSingleIncident(TrafficIncident incident) {

        //tag::doc_traffic_incident_icon_provider[]
        TrafficIncidentIconProvider provider = new TrafficIncidentIconProvider(incident);
        Drawable incidentDrawable = provider.getIcon(context, TrafficIncidentIconProvider.IconSize.LARGE);
        //end::doc_traffic_incident_icon_provider[]

        return new TrafficIncidentItem(incidentDrawable,
                String.format(INCIDENT_DESCRIPTION_FORMAT, incident.getFrom(), incident.getTo()),
                incident.getDelay().or(0),
                incident.getLengthMeters());
    }

    TrafficIncidentItem createClusterOfIncidents(TrafficIncidentCluster cluster) {
        TrafficIncidentIconProvider provider = new TrafficIncidentIconProvider(cluster);

        return new TrafficIncidentItem(provider.getIcon(context, TrafficIncidentIconProvider.IconSize.LARGE),
                context.getString(R.string.traffic_incident_cluster_description),
                0,
                cluster.getLengthMeters(), cluster.getIncidents().size(), true);
    }
}
