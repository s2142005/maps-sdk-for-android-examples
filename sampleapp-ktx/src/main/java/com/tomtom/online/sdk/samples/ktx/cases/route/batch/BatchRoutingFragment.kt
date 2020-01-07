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

package com.tomtom.online.sdk.samples.ktx.cases.route.batch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.routing.data.batch.BatchRoutingResponse
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_batch.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class BatchRoutingFragment : RouteFragment<BatchRoutingViewModel>() {

    override fun routingViewModel(): BatchRoutingViewModel = ViewModelProviders.of(this)
            .get(BatchRoutingViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.default_routing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.result.observe(this, ResourceObserver(
            hideLoading = ::hideLoading,
            showLoading = ::showLoading,
            onSuccess = ::processBatchResults,
            onError = ::showError)
        )
    }

    private fun processBatchResults(response: BatchRoutingResponse) {
        val routes = ArrayList<FullRoute>()

        response.routeRoutingResponses.forEach { routeResponse ->
            routes.addAll(routeResponse.routes)
        }
        addTagsToRoutes(routes)

        viewModel.routes.value = Resource.success(routes)
    }

    override fun onResume() {
        super.onResume()
        confMapActions()
    }

    private fun confMapActions() {
        mainViewModel.applyOnMap(MapAction {
            routeSettings.addOnRouteClickListener { route ->
                val routeIdx = (route.tag as String).toInt()
                viewModel.selectRouteIdx(routeIdx)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        removeMapActions()
    }

    private fun removeMapActions() {
        mainViewModel.applyOnMap(MapAction { routeSettings.removeOnRouteClickListeners() })
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_route_batch, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        batch_routing_travel_mode_btn.setOnClickListener { viewModel.startRoutingDependsOnTravelMode() }
        batch_routing_route_type_btn.setOnClickListener { viewModel.startRoutingDependsOnRouteType() }
        batch_routing_avoids_btn.setOnClickListener { viewModel.startRoutingDependsOnAvoids() }
    }

    private fun addTagsToRoutes(routes: List<FullRoute>) {
        viewModel.routesDescription.observe(this, Observer {
            routes.forEachIndexed { index, fullRoutes ->
                fullRoutes.tag = getString(it[index])
            }
        })
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_amsterdam_to_rotterdam)
    }

    override fun updateInfoBarSubtitle(route: FullRoute) {
        super.updateInfoBarSubtitle(route)
        val routingType = getString(R.string.routing_type, route.tag.toString())
        infoBarView.subtitleTextView.append(routingType)
    }
}
