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

package com.tomtom.online.sdk.samples.ktx.cases.route.alternatives

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_alternatives.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class RouteAlternativesFragment : RouteFragment<RouteAlternativesViewModel>() {

    override fun routingViewModel(): RouteAlternativesViewModel = ViewModelProviders.of(this)
            .get(RouteAlternativesViewModel::class.java)

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

    override fun onResume() {
        super.onResume()
        confMapActions()
    }

    override fun onPause() {
        super.onPause()
        removeMapActions()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_route_alternatives, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        one_alternative_btn.setOnClickListener { viewModel.planRouteWithOneAlternative() }
        three_alternative_btn.setOnClickListener { viewModel.planRouteWithThreeAlternatives() }
        five_alternative_btn.setOnClickListener { viewModel.planRouteWithFiveAlternatives() }
    }

    private fun confMapActions() {
        mainViewModel.applyOnMap(MapAction {
            routeSettings.addOnRouteClickListener { route ->
                val routeIdx = (route.tag as String).toInt()
                viewModel.selectRouteIdx(routeIdx)
            }
        })
    }

    private fun removeMapActions() {
        mainViewModel.applyOnMap(MapAction { routeSettings.removeOnRouteClickListeners() })
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_amsterdam_to_rotterdam)
    }
}