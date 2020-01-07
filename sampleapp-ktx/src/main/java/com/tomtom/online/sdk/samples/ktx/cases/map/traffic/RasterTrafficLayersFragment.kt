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

import com.tomtom.online.sdk.map.TrafficFlowType
import com.tomtom.online.sdk.map.model.MapTilesType
import com.tomtom.online.sdk.samples.ktx.MapAction

class RasterTrafficLayersFragment : TrafficLayersFragment() {

    override fun onExampleStarted() {
        super.onExampleStarted()
        mainViewModel.applyOnMap(MapAction { uiSettings.mapTilesType = MapTilesType.RASTER })
    }

    override fun turnOnFlowTiles() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_flow_on[]
                tomtomMap.trafficSettings.turnOnRasterTrafficFlowTiles()
                //end::doc_traffic_flow_on[]
            }
        })
    }

    override fun turnOnIncidents() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_flow_inc_tiles_raster_on[]
                tomtomMap.trafficSettings.turnOnRasterTrafficIncidents()
                //end::doc_traffic_flow_inc_tiles_raster_on[]
            }
        })
    }

    @Suppress("unused")
    fun exampleOfUsingTrafficStyle() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_flow_styles[]
                //default
                tomtomMap.uiSettings.turnOnRasterTrafficFlowTiles(TrafficFlowType.RelativeTrafficFlowStyle())
                tomtomMap.uiSettings.turnOnRasterTrafficFlowTiles(TrafficFlowType.AbsoluteTrafficFlowStyle())
                tomtomMap.uiSettings.turnOnRasterTrafficFlowTiles(TrafficFlowType.RelativeDelayTrafficFlowStyle())
                tomtomMap.uiSettings.turnOnRasterTrafficFlowTiles(TrafficFlowType.ReducedSensitivityTrafficFlowStyle())
                //end::doc_traffic_flow_styles[]
            }
        })
    }

}