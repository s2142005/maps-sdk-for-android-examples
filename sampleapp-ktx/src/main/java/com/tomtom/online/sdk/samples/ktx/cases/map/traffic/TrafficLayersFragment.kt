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

package com.tomtom.online.sdk.samples.ktx.cases.map.traffic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.model.MapTilesType
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_map_traffic_layers.*

abstract class TrafficLayersFragment : ExampleFragment() {

    private lateinit var trafficLayersViewModel: TrafficLayersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_traffic_layers, container, false)
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation(location = Locations.LONDON, zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE)
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_incidents_off[]
                tomtomMap.trafficSettings.turnOffTrafficIncidents()
                //end::doc_traffic_incidents_off[]

                //tag::doc_traffic_off[]
                tomtomMap.trafficSettings.turnOffTraffic()
                //end::doc_traffic_off[]

                tomtomMap.uiSettings.loadDefaultStyle()
            }
        })
    }

    internal fun registerTrafficObservers() {
        trafficLayersViewModel = ViewModelProviders.of(this).get(TrafficLayersViewModel::class.java)
        trafficLayersViewModel.isIncidentOn.observe(this, Observer { isIncidentOn ->
            traffic_incidents_btn.isChecked = isIncidentOn
            when (isIncidentOn) {
                true -> turnOnIncidents()
                false -> turnOffIncidents()
            }
        })

        trafficLayersViewModel.isFlowOn.observe(this, Observer { isFlowOn ->
            traffic_flow_btn.isChecked = isFlowOn
            when (isFlowOn) {
                true -> turnOnFlowTiles()
                false -> turnOffFlowTiles()
            }
        })

        trafficLayersViewModel.isFlowOrIncidentOn.observe(this, Observer { isFlowOrIncidentOn ->
            traffic_off_btn.isChecked = !isFlowOrIncidentOn
            if (!isFlowOrIncidentOn) {
                turnOffTraffic()
            }
        })
    }

    internal fun confViewActions() {
        traffic_incidents_btn.setOnCheckedChangeListener { _, isChecked ->
            trafficLayersViewModel.setIncidentOn(isChecked)
        }

        traffic_flow_btn.setOnCheckedChangeListener { _, isChecked ->
            trafficLayersViewModel.setFlowOn(isChecked)
        }

        traffic_off_btn.setOnClickListener {
            trafficLayersViewModel.setFlowOn(false)
            trafficLayersViewModel.setIncidentOn(false)
        }
    }

    abstract fun turnOnFlowTiles()

    abstract fun turnOnIncidents()

    abstract fun turnOffFlowTiles()

    abstract fun turnOffIncidents()

    abstract fun turnOffTraffic()

    companion object {
        private const val DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 12.0
    }
}