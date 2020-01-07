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

package com.tomtom.online.sdk.samples.ktx.cases.route.modes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_travel_modes.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class RouteTravelModesFragment : RouteFragment<RouteTravelModesViewModel>() {

    override fun routingViewModel(): RouteTravelModesViewModel = ViewModelProviders.of(this)
        .get(RouteTravelModesViewModel::class.java)

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
        centerOnLocation()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_route_travel_modes, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        route_travel_mode_car_btn.setOnClickListener { viewModel.planRouteForCar() }
        route_travel_mode_truck_btn.setOnClickListener { viewModel.planRouteForTruck() }
        route_travel_mode_pedestrian_btn.setOnClickListener { viewModel.planRouteForPedestrian() }
    }

    override fun drawRoutes(tomtomMap: TomtomMap, routes: List<FullRoute>) {
        context?.let { ctx ->
            if (viewModel.isDotted) {
                RouteDrawer(ctx, tomtomMap).drawDotted(routes)
            } else {
                RouteDrawer(ctx, tomtomMap).drawDefault(routes)
            }
        }
    }

    override fun drawSelectedRouteByIdx(idx: Int) {
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_amsterdam_to_rotterdam)
    }

}
