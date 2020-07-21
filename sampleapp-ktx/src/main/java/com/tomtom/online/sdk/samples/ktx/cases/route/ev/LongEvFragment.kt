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

package com.tomtom.online.sdk.samples.ktx.cases.route.ev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.common.util.DateFormatter
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.ev.route.EvFullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.samples.ktx.utils.formatter.DistanceFormatter
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_ev.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class LongEvFragment : RouteFragment<LongEvViewModel>() {

    override fun routingViewModel(): LongEvViewModel = ViewModelProviders.of(this).get(LongEvViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_routing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewModel()
        confViewActions()
    }

    private fun confViewModel() {
        viewModel = routingViewModel()
        viewModel.evRoutes.observe(
            viewLifecycleOwner, ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::displayEvRoute,
                onError = ::showError
            )
        )
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_route_ev, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        route_ev_short_range_vehicle.setOnClickListener { viewModel.planEvRouteShortRange() }
        route_ev_long_range_vehicle.setOnClickListener { viewModel.planEvRouteLongRange() }
    }

    private fun displayEvRoute(routes: List<EvFullRoute>) {
        mainViewModel.applyOnMap(MapAction {
            clear()
            drawRoute(this, routes)
            displayRoutesOverview()
            updateInfoBarSubtitle(routes.first())
        })
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation(location = Locations.AMSTERDAM_BERLIN_CENTER, zoomLevel = ZOOM_LEVEL_FOR_EXAMPLE)
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearMarkers()
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_amsterdam_to_berlin)
    }

    private fun updateInfoBarSubtitle(route: EvFullRoute) {
        val routeSummary = route.routeSummary?.summary

        val dateWithTimezone = DateFormatter().formatWithTimeZone(routeSummary?.arrivalTime)
        val arrivalTime = dateWithTimezone.toString(TIME_FORMAT)
        val formattedDistance = DistanceFormatter.format(routeSummary?.lengthInMeters ?: 0)

        infoBarView.subtitleTextView.text =
            getString(R.string.routing_info_bar_subtitle, arrivalTime, formattedDistance)
        val routingType = getString(R.string.remaining_charge, viewModel.availableCharge)
        infoBarView.subtitleTextView.append(routingType)
    }

    private fun drawRoute(tomtomMap: TomtomMap, routes: List<EvFullRoute>) {
        context?.let { ctx ->
            RouteDrawer(ctx, tomtomMap).drawEv(routes)
        }
        drawRouteMarkers(tomtomMap)
    }

    private fun drawRouteMarkers(tomtomMap: TomtomMap) {
        LongEvMarkerDrawer(requireContext(), tomtomMap).createMarkersForChargingWaypoints(viewModel.chargingWaypoints)
    }

    private fun clearMarkers() {
        mainViewModel.applyOnMap(MapAction { markerSettings.removeMarkers() })
    }

    companion object {
        private const val ZOOM_LEVEL_FOR_EXAMPLE = 4.0
        private const val TIME_FORMAT = "HH:mm"
    }
}
