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

package com.tomtom.online.sdk.samples.ktx.cases.route.waypoints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_waypoints.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class RouteWaypointsFragment : RouteFragment<RouteWaypointsViewModel>() {

    override fun routingViewModel(): RouteWaypointsViewModel = ViewModelProviders.of(this)
            .get(RouteWaypointsViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_routing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation(location = Locations.AMSTERDAM_BERLIN_CENTER, zoomLevel = ZOOM_LEVEL_FOR_EXAMPLE)
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearMarkers()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_route_waypoints, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        route_waypoints_initial_order_btn.setOnClickListener { viewModel.planInitialOrderRoute() }
        route_waypoints_no_waypoints_btn.setOnClickListener { viewModel.planNoWaypointsRoute() }
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_amsterdam_to_berlin)
    }

    override fun drawRoutes(tomtomMap: TomtomMap, routes: List<FullRoute>) {
        super.drawRoutes(tomtomMap, routes)
        drawRouteMarkers(tomtomMap)
    }

    private fun drawRouteMarkers(tomtomMap: TomtomMap) {
        WaypointsMarkerDrawer(tomtomMap).createMarkersForWaypoints(viewModel.waypoints)
    }

    private fun clearMarkers() {
        mainViewModel.applyOnMap(MapAction { markerSettings.removeMarkers() })
    }

    companion object {
        private const val ZOOM_LEVEL_FOR_EXAMPLE = 4.0
    }
}
