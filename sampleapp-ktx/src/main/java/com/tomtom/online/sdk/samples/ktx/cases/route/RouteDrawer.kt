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

import android.content.Context
import android.graphics.Color
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.map.model.LineCapType
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.sdk.examples.R

open class RouteDrawer(private val context: Context, private val tomtomMap: TomtomMap) {

    fun drawDefault(routes: List<FullRoute>) {
        //Add all routes
        var routeIdx = 0
        routes.forEach { route ->
            //tag::doc_display_route[]
            val routeBuilder = RouteBuilder(route.coordinates)
                    .style(RouteStyle.DEFAULT_ROUTE_STYLE)
                    .startIcon(createIcon(R.drawable.ic_map_route_origin))
                    .endIcon(createIcon(R.drawable.ic_map_route_destination))
                    .tag(routeIdx.toString())
            tomtomMap.addRoute(routeBuilder)
            //end::doc_display_route[]
            routeIdx++
        }
    }

    fun drawCustom(routes: List<FullRoute>,
                   style: RouteStyle = createCustomStyle(),
                   startIconResId: Int = R.drawable.ic_map_route_origin,
                   endIconResId: Int = R.drawable.ic_map_fav) {

        routes.forEach { route ->
            val routeBuilder = RouteBuilder(route.coordinates)
                .style(style)
                .startIcon(createIcon(startIconResId))
                .endIcon(createIcon(endIconResId))
            tomtomMap.addRoute(routeBuilder)
        }
    }

    fun drawDotted(routes: List<FullRoute>,
                   style: RouteStyle = createDottedStyle(),
                   startIconResId: Int = R.drawable.ic_map_route_origin,
                   endIconResId: Int = R.drawable.ic_map_route_destination) {

        routes.forEach { route ->
            val routeBuilder = RouteBuilder(route.coordinates)
                .style(style)
                .startIcon(createIcon(startIconResId))
                .endIcon(createIcon(endIconResId))
            tomtomMap.addRoute(routeBuilder)
        }
    }

    fun setAllAsInactive() {
        //To simplify, mark all as inactive and then selected as active
        //In more advanced flow, one can control style of each route separately
        tomtomMap.routeSettings.routes.forEach { route ->
            tomtomMap.routeSettings.updateRouteStyle(route.id,
                    RouteStyle.DEFAULT_INACTIVE_ROUTE_STYLE)
        }
    }

    fun setActiveByIdx(idx: Int) {
        //Find route by id and mark selected as active
        val routeId = tomtomMap.routeSettings.routes[idx].id
        tomtomMap.routeSettings.updateRouteStyle(routeId, RouteStyle.DEFAULT_ROUTE_STYLE)
        tomtomMap.routeSettings.bringRouteToFront(routeId)
    }

    fun updateRouteStyle(idx: Int, routeStyle: RouteStyle) {
        val routeId = tomtomMap.routeSettings.routes[idx].id
        tomtomMap.routeSettings.updateRouteStyle(routeId, routeStyle)
    }

    private fun createIcon(iconResId: Int): Icon = Icon.Factory.fromResources(
            context, iconResId, DEFAULT_ICON_SCALE)

    private fun createCustomStyle(): RouteStyle {
        return (
                //tag::doc_create_custom_route_style[]
                RouteStyleBuilder.create()
                        .withWidth(ROUTE_WIDTH)
                        .withFillColor(Color.BLACK)
                        .withOutlineColor(Color.RED)
                        .build()
                //end::doc_create_custom_route_style[]
                )
    }

    private fun createDottedStyle(): RouteStyle {
        return RouteStyleBuilder.create()
            .withWidth(DOTTED_ROUTE_WIDTH)
            .withFillColor(COLOR_LIGHT_BLUE)
            .withOutlineColor(COLOR_LIGHT_BLACK)
            .withLineCapType(LineCapType.ROUND)
            .withDashList(DASH_LIST)
            .build()
    }

    companion object {
        const val DEFAULT_ICON_SCALE = 2.0
        const val ROUTE_WIDTH = 2.0
        private const val DOTTED_ROUTE_WIDTH = 0.3
        private const val DASH_LENGTH = 0.01
        private const val DASH_GAP = 2.0
        private val ROUTE_DASH = DashDescriptor(DASH_LENGTH, DASH_GAP)
        private val DASH_LIST: List<DashDescriptor> = listOf(ROUTE_DASH)
        private val COLOR_LIGHT_BLUE = Color.rgb(26, 181, 196)
        private val COLOR_LIGHT_BLACK = Color.rgb(48, 48, 48)
    }

}