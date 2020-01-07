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

import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.tomtom.online.sdk.samples.cases.map.traffic.incident.model.TrafficIncidentItem;
import com.tomtom.online.sdk.traffic.api.incident.details.IncidentDetailsResultListener;
import com.tomtom.online.sdk.traffic.incidents.response.BaseTrafficIncident;
import com.tomtom.online.sdk.traffic.incidents.response.IncidentDetailsResponse;
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncident;
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncidentCluster;
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncidentVisitor;

import java.util.ArrayList;
import java.util.List;

public class TrafficIncidentListPresenter implements LifecycleObserver {

    private final TrafficIncidentListFunctionalExample model;
    private final TrafficIncidentListFragment view;
    private final TrafficIncidentListRequester incidentListRequester;
    private final TrafficIncidentItemCreator incidentItemCreator;

    public TrafficIncidentListPresenter(TrafficIncidentListFragment view) {
        this.view = view;
        model = new TrafficIncidentListFunctionalExample();
        this.incidentListRequester = new TrafficIncidentListRequester(view.getContext());
        this.incidentItemCreator = new TrafficIncidentItemCreator(view.getContext());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        incidentListRequester.findIncidentDetails(incidentDetailsResultListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        view.updateTrafficIncidentsList(new ArrayList<>());
    }

    TrafficIncidentListFunctionalExample getModel() {
        return model;
    }

    //tag::doc_traffic_result_listener[]
    private IncidentDetailsResultListener incidentDetailsResultListener = new IncidentDetailsResultListener() {
        @Override
        public void onTrafficIncidentDetailsResult(IncidentDetailsResponse result) {

            final List<TrafficIncidentItem> items = new ArrayList<>();

            TrafficIncidentVisitor visitor = new TrafficIncidentVisitor() {
                @Override
                public void visit(TrafficIncidentCluster cluster) {
                    items.add(incidentItemCreator.createClusterOfIncidents(cluster));
                }

                @Override
                public void visit(TrafficIncident incident) {
                    items.add(incidentItemCreator.createSingleIncident(incident));
                }
            };

            for (BaseTrafficIncident incident : result.getIncidents()) {
                incident.accept(visitor);
            }

            view.updateTrafficIncidentsList(items);
        }

        @Override
        public void onTrafficIncidentDetailsError(Throwable error) {
            Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    //end::doc_traffic_result_listener[]
}
