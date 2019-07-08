/*
 * Copyright (c) 2015-2019 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */

package com.tomtom.online.sdk.samples.ktx.cases.route.types

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_types.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class RouteTypesFragment : RouteFragment<RouteTypesViewModel>() {

    override fun routingViewModel(): RouteTypesViewModel = ViewModelProviders.of(this)
            .get(RouteTypesViewModel::class.java)

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
        layoutInflater.inflate(R.layout.control_buttons_route_types, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        route_types_fastest_btn.setOnClickListener { viewModel.planFastestRoute() }
        route_types_shortest_btn.setOnClickListener { viewModel.planShortestRoute() }
        route_types_eco_btn.setOnClickListener { viewModel.planEcoRoute() }
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_amsterdam_to_rotterdam)
    }

}
