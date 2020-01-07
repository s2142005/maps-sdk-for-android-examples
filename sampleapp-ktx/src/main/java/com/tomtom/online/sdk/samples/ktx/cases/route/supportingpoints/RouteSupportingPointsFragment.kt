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

package com.tomtom.online.sdk.samples.ktx.cases.route.supportingpoints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_supporting_points.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class RouteSupportingPointsFragment : RouteFragment<RouteSupportingPointsViewModel>() {

    override fun routingViewModel(): RouteSupportingPointsViewModel = ViewModelProviders.of(this)
            .get(RouteSupportingPointsViewModel::class.java)

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
        centerOnLocation(location = Locations.CONDEIXA)
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
        layoutInflater.inflate(R.layout.control_buttons_route_supporting_points, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        route_supporting_points_0m_btn.setOnClickListener { viewModel.planFastestRoute() }
        route_supporting_points_10km_btn.setOnClickListener { viewModel.planShortestRoute() }
    }

    private fun confMapActions() {
        mainViewModel.applyOnMap(MapAction {
            routeSettings.addOnRouteClickListener { route ->
                val routeIdx = (route.tag as String).toInt()
                viewModel.selectRouteIdx(routeIdx)
            }
        })
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_supporting_points_description)
    }

    private fun removeMapActions() {
        mainViewModel.applyOnMap(MapAction { routeSettings.removeOnRouteClickListeners() })
    }
}
