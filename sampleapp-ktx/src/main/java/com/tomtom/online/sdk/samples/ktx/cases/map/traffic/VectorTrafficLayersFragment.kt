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
import android.view.View
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.samples.ktx.MapAction

class VectorTrafficLayersFragment : TrafficLayersFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerTrafficObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewActions()
    }

    override fun turnOnFlowTiles() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_vector_flow_on[]
                tomtomMap.trafficSettings.turnOnTrafficFlowTiles()
                //end::doc_traffic_vector_flow_on[]
            }
        })
    }

    override fun turnOnIncidents() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_vector_incidents_on[]
                tomtomMap.trafficSettings.turnOnTrafficIncidents()
                //end::doc_traffic_vector_incidents_on[]
            }
        })
    }

    override fun turnOffTraffic() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                tomtomMap.trafficSettings.turnOffTrafficFlowTiles()
                tomtomMap.trafficSettings.turnOffTrafficIncidents()
            }
        })
    }

    override fun turnOffFlowTiles() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_flow_off[]
                tomtomMap.trafficSettings.turnOffTrafficFlowTiles()
                //end::doc_traffic_flow_off[]
            }

        })
    }

    override fun turnOffIncidents() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_incidents_off[]
                tomtomMap.trafficSettings.turnOffTrafficIncidents()
                //end::doc_traffic_incidents_off[]
            }
        })
    }

    @Suppress("unused")
    private fun showLegacyVectorTrafficIncidentsTiles() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_legacy_traffic_vector_incidents_on[]
                tomtomMap.trafficSettings.turnOnVectorTrafficIncidents()
                //end::doc_legacy_traffic_vector_incidents_on[]
            }
        })
    }

    @Suppress("unused")
    private fun showLegacyVectorTrafficFlowTiles() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_legacy_traffic_vector_flow_on[]
                tomtomMap.trafficSettings.turnOnRasterTrafficFlowTiles()
                //end::doc_legacy_traffic_vector_flow_on[]
            }
        })
    }

    @Suppress("unused")
    fun exampleOfUsingTrafficStyle() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_flow_styles[]
                tomtomMap.trafficSettings.turnOnVectorTrafficFlowTiles(TrafficFlowType.RelativeTrafficFlowStyle()) //default
                tomtomMap.trafficSettings.turnOnVectorTrafficFlowTiles(TrafficFlowType.AbsoluteTrafficFlowStyle())
                tomtomMap.trafficSettings.turnOnVectorTrafficFlowTiles(TrafficFlowType.RelativeDelayTrafficFlowStyle())
                //end::doc_traffic_flow_styles[]

                //tag::doc_get_style_info[]
                val style = tomtomMap.trafficSettings.trafficVectorFlowStyle
                //end::doc_get_style_info[]
            }
        })
    }

    @Suppress("unused")
    fun exampleOfUsingTrafficClickListeners() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_flow_vector_listener[]
                tomtomMap.trafficSettings.setOnTrafficFlowClickListener(DefaultOnTrafficFlowClickListener(this))
                //end::doc_traffic_flow_vector_listener[]

                //tag::doc_traffic_incident_vector_listener[]
                tomtomMap.trafficSettings.setOnTrafficIncidentsClickListener(DefaultOnTrafficIncidentClickListener(this))
                //end::doc_traffic_incident_vector_listener[]
            }
        })
    }

    @Suppress("unused")
    fun exampleOfUsingTrafficBalloonAdapter() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_flow_balloon_view_adapter[]
                tomtomMap.trafficSettings.setTrafficFlowBalloonViewAdapter(TrafficFlowBalloonViewAdapter.Default())
                //end::doc_traffic_flow_balloon_view_adapter[]

                //tag::doc_traffic_incident_balloon_view_adapter[]
                tomtomMap.trafficSettings.setTrafficIncidentsBalloonViewAdapter(TrafficIncidentsBalloonViewAdapter.Default())
                //end::doc_traffic_incident_balloon_view_adapter[]
            }
        })
    }
}