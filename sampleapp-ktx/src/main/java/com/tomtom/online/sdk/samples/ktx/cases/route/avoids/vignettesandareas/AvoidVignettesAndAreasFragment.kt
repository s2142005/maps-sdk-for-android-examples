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

package com.tomtom.online.sdk.samples.ktx.cases.route.avoids.vignettesandareas

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.map.PolylineBuilder
import com.tomtom.online.sdk.map.RouteStyle
import com.tomtom.online.sdk.map.RouteStyleBuilder
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteDrawer
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteFragment
import com.tomtom.online.sdk.samples.ktx.cases.route.avoids.vignettesandareas.AvoidVignettesAndAreasViewModel.ExampleType.*
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_route_avoid_vignettes_and_areas.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class AvoidVignettesAndAreasFragment : RouteFragment<AvoidVignettesAndAreasViewModel>() {

    override fun routingViewModel() = ViewModelProviders.of(this)
            .get(AvoidVignettesAndAreasViewModel::class.java)

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
        centerOnLocation(Locations.BUDAPEST)
    }

    override fun onResume() {
        super.onResume()
        confMapActions()
    }

    override fun onPause() {
        super.onPause()
        removeMapActions()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearMap()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_route_avoid_vignettes_and_areas, routingControlButtonsContainer, true)
    }

    private fun confViewActions() {
        route_no_avoid_btn.setOnClickListener { viewModel.planBaseRoute() }

        route_avoid_vignettes_btn.setOnClickListener {
            //tag::doc_route_avoid_vignettes[]
            val listOfVignettes = listOf("HUN", "CZE", "SVK")
            //end::doc_route_avoid_vignettes[]
            viewModel.planAvoidVignettesRoute(listOfVignettes)
        }

        route_avoid_area_btn.setOnClickListener {
            //tag::doc_route_avoid_area[]
            val boundingBox = BoundingBox(Locations.ARAD_TOP_LEFT_NEIGHBORHOOD, Locations.ARAD_BOTTOM_RIGHT_NEIGHBORHOOD)
            //end::doc_route_avoid_area[]
            viewModel.planAvoidAreaRoute(boundingBox)
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

    override fun displayRoutingResults(routes: List<FullRoute>) {
        addTagsToRoutes(routes)
        super.displayRoutingResults(routes)
    }

    override fun drawRoutes(tomtomMap: TomtomMap, routes: List<FullRoute>) {
        super.drawRoutes(tomtomMap, routes)

        if (viewModel.exampleType == AVOID_AREAS) {
            drawBoundingBox(tomtomMap)
        }
    }

    private fun addTagsToRoutes(routes: List<FullRoute>) {
        viewModel.routesDescription.observe(this, Observer {
            routes.forEachIndexed { index, fullRoutes ->
                fullRoutes.tag = getString(it[index])
            }
        })
        viewModel.routesDescription.removeObservers(this)
    }

    override fun drawSelectedRouteByIdx(idx: Int) {
        mainViewModel.applyOnMap(MapAction {
            val routeDrawer = RouteDrawer(requireContext(), this)
            routeDrawer.setActiveByIdx(idx)
            updateStyleForRoute(routeDrawer)
        })
    }

    private fun updateStyleForRoute(routeDrawer: RouteDrawer) {
        when (viewModel.exampleType) {
            AVOID_VIGNETTES -> routeDrawer
                    .updateRouteStyle(DEFAULT_ROUTE_IDX, createMagentaRouteStyle())
            AVOID_AREAS -> routeDrawer
                    .updateRouteStyle(DEFAULT_ROUTE_IDX, createGreenRouteStyle())
            NO_AVOID -> routeDrawer
                    .updateRouteStyle(DEFAULT_ROUTE_IDX, createDefaultRouteStyle())
        }
    }

    private fun createMagentaRouteStyle(): RouteStyle {
        return RouteStyleBuilder.create().withFillColor(Color.MAGENTA).build()
    }

    private fun createGreenRouteStyle(): RouteStyle {
        return RouteStyleBuilder.create().withFillColor(Color.GREEN).build()
    }

    private fun createDefaultRouteStyle(): RouteStyle {
        return RouteStyle.DEFAULT_ROUTE_STYLE
    }

    private fun drawBoundingBox(tomtomMap: TomtomMap) {
        tomtomMap.overlaySettings.addOverlay(PolylineBuilder.create()
                .coordinate(Locations.ARAD_TOP_LEFT_NEIGHBORHOOD)
                .coordinate(Locations.ARAD_BOTTOM_LEFT_NEIGHBORHOOD)
                .coordinate(Locations.ARAD_BOTTOM_RIGHT_NEIGHBORHOOD)
                .coordinate(Locations.ARAD_TOP_RIGHT_NEIGHBORHOOD)
                .coordinate(Locations.ARAD_TOP_LEFT_NEIGHBORHOOD)
                .color(Color.BLUE)
                .build())
    }

    private fun clearMap() {
        mainViewModel.applyOnMap(MapAction {
            overlaySettings.removeOverlays()
        })
    }

    override fun updateInfoBarTitle() {
        infoBarView.titleTextView.text = getString(R.string.title_route_czech_republic_to_romania)
    }

    override fun updateInfoBarSubtitle(route: FullRoute) {
        super.updateInfoBarSubtitle(route)
        val routingType = getString(R.string.routing_type, route.tag.toString())
        infoBarView.subtitleTextView.append(routingType)
    }

}