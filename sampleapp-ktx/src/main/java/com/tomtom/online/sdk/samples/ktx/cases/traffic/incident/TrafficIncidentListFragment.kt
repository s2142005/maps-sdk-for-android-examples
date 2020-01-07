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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.cases.traffic.incident.model.TrafficIncidentItem
import com.tomtom.online.sdk.samples.ktx.cases.traffic.incident.utils.TrafficIncidentItemCreator
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.traffic.api.incident.details.IncidentDetailsResultListener
import com.tomtom.online.sdk.traffic.incidents.response.IncidentDetailsResponse
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncident
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncidentCluster
import com.tomtom.online.sdk.traffic.incidents.response.TrafficIncidentVisitor
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_traffic_incident_list.*

class TrafficIncidentListFragment : ExampleFragment() {

    private lateinit var viewModel: TrafficIncidentListViewModel
    private lateinit var trafficIncidentAdapter: TrafficIncidentResultsAdapter
    private lateinit var trafficIncidentItemCreator: TrafficIncidentItemCreator
    private var listOfIncidents: MutableList<TrafficIncidentItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_traffic_incident_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        confViewModel()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        viewModel.findIncidentDetails()
    }

    private fun displayIncidents(incidentDetails: IncidentDetailsResponse) {
        listOfIncidents.clear()

        context?.let { ctx ->
            trafficIncidentItemCreator = TrafficIncidentItemCreator(ctx)

            incidentDetails.incidents.forEach { incident ->
                incident.accept(trafficVisitor)
            }

            trafficIncidentAdapter.updateData(listOfIncidents)
        }
    }

    private fun setupRecyclerView() {
        incidentResultRv?.let { view ->
            trafficIncidentAdapter = TrafficIncidentResultsAdapter()
            view.adapter = trafficIncidentAdapter
            view.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun confViewModel() {
        viewModel = ViewModelProviders.of(this).get(TrafficIncidentListViewModel::class.java)
        viewModel.trafficResponse.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::displayIncidents,
            onError = ::showError))
    }

    //tag::doc_traffic_result_visitor[]
    private val trafficVisitor = object : TrafficIncidentVisitor {

        override fun visit(cluster: TrafficIncidentCluster) {
            listOfIncidents.add(trafficIncidentItemCreator.createClusterOfIncidents(cluster))
        }

        override fun visit(incident: TrafficIncident) {
            listOfIncidents.add(trafficIncidentItemCreator.createSingleIncident(incident))
        }
    }
    //end::doc_traffic_result_visitor[]

    @Suppress("unused")
    //tag::doc_traffic_result_listener[]
    private val incidentDetailsResultListener = object : IncidentDetailsResultListener {
        override fun onTrafficIncidentDetailsResult(result: IncidentDetailsResponse) {
            listOfIncidents.clear()

            context?.let { ctx ->
                trafficIncidentItemCreator = TrafficIncidentItemCreator(ctx)

                result.incidents.forEach { incident ->
                    incident.accept(trafficVisitor)
                }

                trafficIncidentAdapter.updateData(listOfIncidents)
            }
        }

        override fun onTrafficIncidentDetailsError(error: Throwable) {
            Toast.makeText(view?.context, error.message, Toast.LENGTH_SHORT).show()
        }
    }
    //end::doc_traffic_result_listener[]
}
