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


class SearchMenuFragment : MenuFragment() {

    companion object {
        val MENU_ITEMS = listOf(
            MenuItem(
                title = R.string.menu_search_address_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_search_address_description,
                banner = R.drawable.ic_menu_search_adress_search,
                onClickNavigateTo = R.id.addressSearchFragment
            ),
            MenuItem(
                title = R.string.menu_search_poi_categories_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_search_poi_categories_description,
                banner = R.drawable.ic_menu_search_poi_categories_search,
                onClickNavigateTo = R.id.poiCategoriesSearchFragment
            ),
            MenuItem(
                title = R.string.menu_category_search_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_category_search_description,
                banner = R.drawable.ic_menu_search_category_search,
                onClickNavigateTo = R.id.categorySearchFragment
            ),
            MenuItem(
                title = R.string.menu_language_parameter_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_language_parameter_description,
                banner = R.drawable.ic_menu_search_language_parameter,
                onClickNavigateTo = R.id.languageParamFragment
            ),
            MenuItem(
                title = R.string.menu_typeahead_search_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_typeahead_search_description,
                banner = R.drawable.ic_menu_search_typeahead_search,
                onClickNavigateTo = R.id.typeaheadSearchFragment
            ),
            MenuItem(
                title = R.string.menu_autocomplete_search_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_autocomplete_search_description,
                banner = R.drawable.ic_menu_search_autocomplete_search,
                onClickNavigateTo = R.id.autocompleteSearchFragment
            ),
            MenuItem(
                title = R.string.menu_search_max_fuzziness_param_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_search_max_fuzziness_param_description,
                banner = R.drawable.ic_menu_search_max_fuzziness_parameters,
                onClickNavigateTo = R.id.maxFuzzinessParamFragment
            ),
            MenuItem(
                title = R.string.menu_search_reverse_geocoding_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_search_reverse_geocoding_description,
                banner = R.drawable.ic_menu_search_reverse_geocoding,
                onClickNavigateTo = R.id.revGeoFragment
            ),
            MenuItem(
                title = R.string.menu_search_along_route_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_search_along_route_description,
                banner = R.drawable.ic_menu_search_along_the_route,
                onClickNavigateTo = R.id.alongRouteFragment
            ),
            MenuItem(
                title = R.string.menu_geometry_search_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_geometry_search_description,
                banner = R.drawable.ic_menu_search_geometry_search,
                onClickNavigateTo = R.id.geometrySearchFragment
            ),
            MenuItem(
                title = R.string.menu_search_entry_points_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_search_entry_points_description,
                banner = R.drawable.ic_menu_search_entry_points,
                onClickNavigateTo = R.id.entryPointsFragment
            ),
            MenuItem(
                title = R.string.menu_search_opening_hours_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_search_opening_hours_description,
                banner = R.drawable.ic_menu_search_opening_hours,
                onClickNavigateTo = R.id.openingHoursFragment
            ),
            MenuItem(
                title = R.string.menu_search_entry_adp_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_search_entry_adp_description,
                banner = R.drawable.ic_menu_search_adp,
                onClickNavigateTo = R.id.adpSearchFragment
            ),
            MenuItem(
                title = R.string.menu_batch_search_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_batch_search_description,
                banner = R.drawable.ic_menu_batch_search,
                onClickNavigateTo = R.id.batchSearchFragment
            ),
            MenuItem(
                title = R.string.menu_search_entry_rev_geo_polygons_title,
                icon = R.drawable.ic_map_menu_icon,
                description = R.string.menu_search_entry_rev_geo_polygons_description,
                banner = R.drawable.ic_menu_search_rev_geo_polygons,
                onClickNavigateTo = R.id.revGeoPolygonsFragment
            )
        )
    }

    override fun createMenuAdapter() = SubMenuAdapter(MENU_ITEMS)

}
