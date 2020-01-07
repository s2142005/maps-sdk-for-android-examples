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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tomtom.sdk.examples.R
import com.tomtom.online.sdk.samples.ktx.menu.adapters.MenuAdapter
import com.tomtom.online.sdk.samples.ktx.menu.data.MenuItem
import kotlinx.android.synthetic.main.fragment_menu.view.*

open class MenuFragment : Fragment() {

    companion object {
        val MENU_ITEMS = listOf(
                MenuItem(title = R.string.menu_map_title,
                        icon = R.drawable.ic_map_menu_icon,
                        description = R.string.menu_map_description,
                        banner = R.drawable.ic_map_menu_banner,
                        onClickNavigateTo = R.id.map_nav_graph),

                MenuItem(title = R.string.menu_traffic_title,
                        icon = R.drawable.ic_traffic_menu_icon,
                        description = R.string.menu_traffic_description,
                        banner = R.drawable.ic_traffic_menu_baner,
                        onClickNavigateTo = R.id.traffic_nav_graph),

                MenuItem(title = R.string.menu_routing_title,
                        icon = R.drawable.ic_routing_menu_icon,
                        description = R.string.menu_routing_description,
                        banner = R.drawable.ic_routing_menu_banner,
                        onClickNavigateTo = R.id.routing_nav_graph),

                MenuItem(title = R.string.menu_search_title,
                        icon = R.drawable.ic_search_menu_icon,
                        description = R.string.menu_search_description,
                        banner = R.drawable.ic_search_menu_banner,
                        onClickNavigateTo = R.id.search_nav_graph),

                MenuItem(title = R.string.menu_geofencing_title,
                        icon = R.drawable.ic_geofencing_menu_icon,
                        description = R.string.menu_geofencing_description,
                        banner = R.drawable.ic_geofencing_menu_banner,
                        onClickNavigateTo = R.id.geofencing_nav_graph),

                MenuItem(title = R.string.menu_driving_title,
                        icon = R.drawable.ic_driving_menu_icon,
                        description = R.string.menu_driving_description,
                        banner = R.drawable.ic_driving_menu_banner,
                        onClickNavigateTo = R.id.driving_nav_graph)
        )
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager = LinearLayoutManager(context)
        viewAdapter = createMenuAdapter()

        recyclerView = view.menu_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    open fun createMenuAdapter() = MenuAdapter(MENU_ITEMS)

}

