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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_dynamic_layer_order.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class DynamicLayerOrderFragment : ExampleFragment() {

    private lateinit var dynamicLayerOrderViewModel: DynamicLayerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewModel()
        confViewActions()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_dynamic_layer_order, mapControlButtonsContainer, true)
    }

    private fun confViewModel() {
        dynamicLayerOrderViewModel = ViewModelProviders.of(this).get(DynamicLayerViewModel::class.java)
        dynamicLayerOrderViewModel.routes.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::processRoutingResults,
            onError = ::showError)
        )
    }

    override fun onExampleStarted() {
        dynamicLayerOrderViewModel.planSanFranciscoToSantaCruzRoute()
        centerOnLocation(location = Locations.SAN_JOSE, zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE)
    }

    override fun onExampleEnded() {
        mainViewModel.applyOnMap(MapAction {
            routeSettings.clearRoute()
            uiSettings.setStyleUrl("asset://styles/mapssdk-default-style.json")
        })
    }

    private fun processRoutingResults(routes: List<FullRoute>) {
        mainViewModel.applyOnMap(MapAction {
            if (routeSettings.routes.isEmpty()) {
                RouteDrawer(requireContext(), this).drawDefault(routes)

                val layerDrawer = DynamicLayerDrawer(requireContext(), this)
                layerDrawer.addGeoJsonLayer(routes.first())
                layerDrawer.addImageLayers()
            }
        })
    }

    private fun confViewActions() {
        map_dynamic_layer_order_image_btn.setOnClickListener { moveImageLayerToFront() }
        map_dynamic_layer_order_geojson_btn.setOnClickListener { moveGeoJsonLayerToFront() }
        map_dynamic_layer_order_roads_btn.setOnClickListener { moveRoadsLayerToFront() }
    }

    private fun moveImageLayerToFront() {
        mainViewModel.applyOnMap(MapAction {
            DynamicLayerDisplayer(this).moveImageLayerToFront()
        })
    }

    private fun moveGeoJsonLayerToFront() {
        mainViewModel.applyOnMap(MapAction {
            DynamicLayerDisplayer(this).moveGeoJsonLayerToFront()
        })
    }

    private fun moveRoadsLayerToFront() {
        mainViewModel.applyOnMap(MapAction {
            DynamicLayerDisplayer(this).moveRoadsLayerToFront()
        })
    }

    companion object {
        private const val DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 7.5
    }
}