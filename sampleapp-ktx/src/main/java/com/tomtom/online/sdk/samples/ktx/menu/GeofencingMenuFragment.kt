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

class GeofencingMenuFragment : MenuFragment() {

    companion object {
        val MENU_ITEMS = listOf(
                MenuItem(title = R.string.menu_geofencing_report_service_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_geofencing_report_service_description,
                        banner = R.drawable.ic_geofencing_menu_report_service,
                        onClickNavigateTo = R.id.reportServiceFragment)
        )
    }

    override fun createMenuAdapter() = SubMenuAdapter(MENU_ITEMS)

}
