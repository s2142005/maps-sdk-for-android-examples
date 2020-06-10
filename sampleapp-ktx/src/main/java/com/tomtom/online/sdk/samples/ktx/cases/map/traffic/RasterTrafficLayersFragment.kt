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
import com.tomtom.core.maps.MapChangedListenerAdapter
import com.tomtom.online.sdk.map.TomtomMapCallback
import com.tomtom.online.sdk.map.TrafficFlowType
import com.tomtom.online.sdk.map.style.layers.Visibility
import com.tomtom.online.sdk.samples.ktx.MapAction

class RasterTrafficLayersFragment : TrafficLayersFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (exampleViewModel.isRestored) {
            registerTrafficObservers()
            confViewActions()
        }
    }

    private val rasterOnMapChangedListener: TomtomMapCallback.OnMapChangedListener =
        object : MapChangedListenerAdapter() {
            override fun onDidFinishLoadingStyle() {
                registerTrafficObservers()
                confViewActions()
            }
        }

    override fun onExampleStarted() {
        super.onExampleStarted()
        mainViewModel.applyOnMap(MapAction {
            uiSettings.setStyleUrl("asset://styles/mapssdk-raster_style.json")
            addOnMapChangedListener(rasterOnMapChangedListener)
        })
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        mainViewModel.applyOnMap(MapAction {
            removeOnMapChangedListener(rasterOnMapChangedListener)
        })
    }

    override fun turnOnFlowTiles() {
        changeVisibilityOfTrafficFlow(Visibility.VISIBLE)
    }

    override fun turnOnIncidents() {
        changeVisibilityOfTrafficIncidents(Visibility.VISIBLE)
    }

    override fun turnOffFlowTiles() {
        changeVisibilityOfTrafficFlow(Visibility.NONE)
    }

    override fun turnOffIncidents() {
        changeVisibilityOfTrafficIncidents(Visibility.NONE)
    }

    override fun turnOffTraffic() {
        changeVisibilityOfTrafficIncidents(Visibility.NONE)
        changeVisibilityOfTrafficFlow(Visibility.NONE)
    }

    private fun changeVisibilityOfTrafficFlow(visibility: Visibility) {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_flow_on[]
                val layers = tomtomMap.styleSettings.findLayersById("tomtom-flow-raster-layer")
                layers.forEach { layer ->
                    layer.visibility = visibility
                }
                //end::doc_traffic_flow_on[]
            }
        })
    }

    private fun changeVisibilityOfTrafficIncidents(visibility: Visibility) {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_incidents_on[]
                val layers = tomtomMap.styleSettings.findLayersById("tomtom-incidents-layer")
                layers.forEach { layer ->
                    layer.visibility = visibility
                }
                //end::doc_traffic_incidents_on[]
            }
        })
    }

    @Suppress("unused")
    private fun turnOnLegacyTrafficIncidents() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_legacy_traffic_flow_inc_tiles_raster_on[]
                tomtomMap.trafficSettings.turnOnRasterTrafficIncidents()
                //end::doc_legacy_traffic_flow_inc_tiles_raster_on[]
            }
        })
    }

    @Suppress("unused")
    private fun turnOnLegacyTrafficFlow() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_legacy_traffic_flow_on[]
                tomtomMap.trafficSettings.turnOnRasterTrafficFlowTiles()
                //end::doc_legacy_traffic_flow_on[]
            }
        })
    }

    @Suppress("unused")
    fun exampleOfUsingTrafficStyle() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_traffic_flow_styles[]
                //default
                tomtomMap.trafficSettings.turnOnRasterTrafficFlowTiles(TrafficFlowType.RelativeTrafficFlowStyle())
                tomtomMap.trafficSettings.turnOnRasterTrafficFlowTiles(TrafficFlowType.AbsoluteTrafficFlowStyle())
                tomtomMap.trafficSettings.turnOnRasterTrafficFlowTiles(TrafficFlowType.RelativeDelayTrafficFlowStyle())
                tomtomMap.trafficSettings.turnOnRasterTrafficFlowTiles(TrafficFlowType.ReducedSensitivityTrafficFlowStyle())
                //end::doc_traffic_flow_styles[]
            }
        })
    }

}