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

package com.tomtom.online.sdk.samples.ktx.cases.route.consumption

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.online.sdk.samples.ktx.cases.route.consumption.RouteConsumptionViewModel.ExampleType
import com.tomtom.online.sdk.samples.ktx.utils.formatter.DistanceFormatter
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_consumption.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class RouteConsumptionFragment : RouteFragment<RouteConsumptionViewModel>() {

    override fun routingViewModel(): RouteConsumptionViewModel = ViewModelProviders.of(this)
            .get(RouteConsumptionViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_routing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        hideInfoBarImage()
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
        layoutInflater.inflate(R.layout.control_buttons_route_consumption, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        route_consumption_combustion_btn.setOnClickListener { viewModel.planRouteForCombustion() }
        route_consumption_electric_btn.setOnClickListener { viewModel.planRouteForElectric() }
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_amsterdam_to_utrecht)
    }

    override fun updateInfoBarSubtitle(route: FullRoute) {
        val routeSummary = route.summary

        val distance = routeSummary.lengthInMeters
        val formattedDistance = DistanceFormatter.format(distance)

        when (viewModel.exampleType) {
            ExampleType.ELECTRIC ->
                infoBarView.subtitleTextView.text =
                        formatText(routeSummary.batteryConsumptionInkWh, CONSUMPTION_UNIT_ELECTRIC, formattedDistance)

            ExampleType.COMBUSTION ->
                infoBarView.subtitleTextView.text =
                        formatText(routeSummary.fuelConsumptionInLiters, CONSUMPTION_UNIT_COMBUSTION, formattedDistance)
        }
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

    private fun formatText(routeConsumption: Float, consumptionUnit: String, distance: String): String {
        return getString(R.string.route_consumption_info_bar_subtitle, routeConsumption, consumptionUnit, distance)
    }

    companion object {
        private const val CONSUMPTION_UNIT_ELECTRIC = "kWh"
        private const val CONSUMPTION_UNIT_COMBUSTION = "liters"
    }

}
