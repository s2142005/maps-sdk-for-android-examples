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

package com.tomtom.online.sdk.samples.ktx.cases.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.samples.ktx.utils.formatter.DistanceFormatter
import com.tomtom.online.sdk.samples.ktx.views.InfoBarView
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.default_routing_fragment.*

abstract class RouteFragment<T : RouteViewModel> : ExampleFragment() {

    protected lateinit var viewModel: T
    internal lateinit var infoBarView: InfoBarView

    abstract fun routingViewModel(): T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_routing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewModel()
        confInfoBar()
        updateInfoBarTitle()
    }

    override fun onResume() {
        super.onResume()
        confCompass()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        removeRoutes()
        resetCompassMargins()
    }

    private fun confViewModel() {
        viewModel = routingViewModel()
        viewModel.routes.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::processRoutingResults,
            onError = ::showError))
        viewModel.selectedRoute.observe(this, Observer { highlightSelectedRoute() })
    }

    private fun confCompass() {
        mainViewModel.adjustCompassTopMarginForInfoBar()
    }

    private fun confInfoBar() {
        infoBarView = info_bar_view
        infoBarView.leftImageView.setImageResource(R.drawable.ic_clock)
    }

    private fun processRoutingResults(routes: List<FullRoute>) {
        displayRoutingResults(routes)
    }

    open fun displayRoutingResults(routes: List<FullRoute>) {
        mainViewModel.applyOnMap(MapAction {
            clear()
            drawRoutes(this, routes)
            displayRoutesOverview()
            selectRoute()
        })
    }

    open fun selectRoute() {
        viewModel.selectedRoute.value?.let { route ->
            viewModel.selectRouteIdx(route.second)
        } ?: run {
            viewModel.selectRouteIdx(DEFAULT_ROUTE_IDX)
        }
    }

    private fun highlightSelectedRoute() {
        viewModel.selectedRoute.value?.let { route ->
            updateInfoBar(route.first)
            drawSelectedRouteByIdx(route.second)
        }
    }

    open fun drawRoutes(tomtomMap: TomtomMap, routes: List<FullRoute>) {
        context?.let { ctx ->
            RouteDrawer(ctx, tomtomMap).drawDefault(routes)
        }
    }

    open fun drawSelectedRouteByIdx(idx: Int) {
        context?.let { ctx ->
            mainViewModel.applyOnMap(MapAction {
                val routeDrawer = RouteDrawer(ctx, this)
                routeDrawer.setAllAsInactive()
                routeDrawer.setActiveByIdx(idx)
            })
        }
    }

    private fun removeRoutes() {
        mainViewModel.applyOnMap(MapAction { routeSettings.clearRoute() })
    }

    open fun updateInfoBarTitle() {
    }

    protected fun updateInfoBar(route: FullRoute) {
        showInfoBar()
        showInfoBarImage()
        updateInfoBarTitle()
        updateInfoBarSubtitle(route)
    }

    open fun updateInfoBarSubtitle(route: FullRoute) {

        val routeSummary = route.summary

        var arrivalTime = getString(R.string.routing_response_date_not_available)
        val timeToArrival = routeSummary.arrivalTimeWithZone
        timeToArrival?.let { arrivalTime = timeToArrival.toString(TIME_FORMAT) }

        val distance = routeSummary.lengthInMeters
        val formattedDistance = DistanceFormatter.format(distance)

        infoBarView.subtitleTextView.text = getString(R.string.routing_info_bar_subtitle,
                arrivalTime, formattedDistance)
    }

    open fun showInfoBar() {
        infoBarView.visibility = View.VISIBLE
    }

    fun hideInfoBarImage() {
        infoBarView.hideImageView()
    }

    private fun showInfoBarImage() {
        infoBarView.showImageView()
    }

    private fun resetCompassMargins() {
        mainViewModel.resetCompassMargins()
    }

    companion object {
        private const val TIME_FORMAT = "HH:mm"
        const val DEFAULT_ROUTE_IDX = 0
    }
}