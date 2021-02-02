/*
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */

package com.tomtom.online.sdk.samples.ktx.cases.map.trafficalongtheroute

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.route.information.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_traffic_along_the_route.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class TrafficAlongTheRouteFragment : RouteFragment<TrafficAlongTheRouteViewModel>() {

    override fun routingViewModel(): TrafficAlongTheRouteViewModel = ViewModelProviders.of(this)
        .get(TrafficAlongTheRouteViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation(Locations.LONDON)
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_traffic_along_the_route, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        traffic_on_route_london_btn.setOnClickListener { viewModel.planRouteLondon() }
        traffic_on_route_los_angeles_btn.setOnClickListener { viewModel.planRouteLosAngeles() }
    }

    override fun displayRoutingResults(routes: List<FullRoute>) {
        mainViewModel.applyOnMap(MapAction {
            clear()
            drawRoutes(this, routes)
            displayRoutesOverview()
            updateInfoBar(routes[DEFAULT_ROUTE_IDX])
        })
    }

    override fun drawRoutes(tomtomMap: TomtomMap, routes: List<FullRoute>) {
        context?.let { ctx ->
            RouteDrawer(ctx, tomtomMap).drawRouteWithTraffic(routes)
        }
    }

    override fun updateInfoBarTitle() {
        infoBarView.visibility = View.INVISIBLE
    }
}
