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

package com.tomtom.online.sdk.samples.ktx.menu

import com.tomtom.online.sdk.samples.ktx.menu.adapters.SubMenuAdapter
import com.tomtom.online.sdk.samples.ktx.menu.data.MenuItem
import com.tomtom.sdk.examples.R

class RoutingMenuFragment : MenuFragment() {

    companion object {
        val MENU_ITEMS = listOf(
                MenuItem(title = R.string.menu_route_travel_modes_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_route_travel_modes_description,
                        banner = R.drawable.ic_menu_routing_travel_modes,
                        onClickNavigateTo = R.id.routeTravelModesFragment),
                MenuItem(title = R.string.menu_route_types_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_route_types_description,
                        banner = R.drawable.ic_routing_menu_route_types,
                        onClickNavigateTo = R.id.routeTypesFragment),
                MenuItem(title = R.string.menu_route_avoids_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_route_avoids_description,
                        banner = R.drawable.ic_routing_menu_route_avoid,
                        onClickNavigateTo = R.id.routeAvoidFragment),
                MenuItem(title = R.string.menu_route_waypoints_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_route_waypoints_description,
                        banner = R.drawable.ic_routing_menu_route_waypoints,
                        onClickNavigateTo = R.id.routeWaypointsFragment),
                MenuItem(title = R.string.menu_route_alternatives_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_route_alternatives_description,
                        banner = R.drawable.ic_routing_menu_route_alternatives,
                        onClickNavigateTo = R.id.routeAlternativesFragment),
                MenuItem(title = R.string.menu_route_consumption_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_route_consumption_description,
                        banner = R.drawable.ic_routing_menu_route_consumption,
                        onClickNavigateTo = R.id.routeConsumptionFragment),
                MenuItem(title = R.string.menu_route_supporting_points_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_route_supporting_points_description,
                        banner = R.drawable.ic_routing_menu_route_supporting_points,
                        onClickNavigateTo = R.id.routeSupportingPointsFragment),
                MenuItem(title = R.string.menu_route_reachable_range_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_route_reachable_range_description,
                        banner = R.drawable.ic_routing_menu_reachable_range,
                        onClickNavigateTo = R.id.reachableRangeFragment),
                MenuItem(title = R.string.menu_batch_routing_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_batch_routing_description,
                        banner = R.drawable.ic_menu_batch_routing,
                        onClickNavigateTo = R.id.batchRoutingFragment),
                MenuItem(title = R.string.menu_route_avoid_vignettes_and_areas_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_route_avoid_vignettes_and_areas_description,
                        banner = R.drawable.ic_routing_menu_avoid_vignettes_and_areas,
                        onClickNavigateTo = R.id.avoidVignettesAndAreasFragment)
        )
    }

    override fun createMenuAdapter() = SubMenuAdapter(MENU_ITEMS)
}
