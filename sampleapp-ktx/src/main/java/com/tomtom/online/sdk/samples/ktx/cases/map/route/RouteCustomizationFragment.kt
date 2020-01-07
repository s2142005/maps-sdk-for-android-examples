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

package com.tomtom.online.sdk.samples.ktx.cases.map.route

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_customization.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class RouteCustomizationFragment : RouteFragment<RouteCustomizationViewModel>() {

    override fun routingViewModel(): RouteCustomizationViewModel = ViewModelProviders.of(this)
            .get(RouteCustomizationViewModel::class.java)

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
        layoutInflater.inflate(R.layout.control_buttons_route_customization, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        route_customization_basic_btn.setOnClickListener { viewModel.planDefaultRoute() }
        route_customization_custom_btn.setOnClickListener { viewModel.planCustomRoute() }
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
            if (viewModel.isCustom) {
                RouteDrawer(ctx, tomtomMap).drawCustom(routes)
            } else {
                RouteDrawer(ctx, tomtomMap).drawDefault(routes)
            }
        }
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = ROUTE_AMSTERDAM_TO_ROTTERDAM
    }

    companion object {
        private const val ROUTE_AMSTERDAM_TO_ROTTERDAM = "Amsterdam to Rotterdam"
    }
}
