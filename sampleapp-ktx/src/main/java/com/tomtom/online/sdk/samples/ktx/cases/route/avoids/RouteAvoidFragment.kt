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

package com.tomtom.online.sdk.samples.ktx.cases.route.avoids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_avoid.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class RouteAvoidFragment : RouteFragment<RouteAvoidViewModel>() {

    override fun routingViewModel(): RouteAvoidViewModel = ViewModelProviders.of(this)
            .get(RouteAvoidViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
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
        layoutInflater.inflate(R.layout.control_buttons_route_avoid, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        avoid_motorways_btn.setOnClickListener { viewModel.avoidMotorways() }
        avoid_toll_roads_btn.setOnClickListener { viewModel.avoidTollRoads() }
        avoid_ferries_btn.setOnClickListener { viewModel.avoidFerries() }
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_amsterdam_to_oslo)
    }
}
