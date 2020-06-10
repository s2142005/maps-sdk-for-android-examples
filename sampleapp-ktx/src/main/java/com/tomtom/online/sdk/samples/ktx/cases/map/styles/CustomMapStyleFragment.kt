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

package com.tomtom.online.sdk.samples.ktx.cases.map.styles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_custom_style.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class CustomMapStyleFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        centerOnLocation()
    }

    override fun onExampleEnded() {
        showBaseStyle()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_custom_style, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        map_style_basic_btn.setOnClickListener { showBaseStyle() }
        map_style_custom_btn.setOnClickListener { showNightStyle() }
    }

    private fun showBaseStyle() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::set_default_style[]
                tomtomMap.uiSettings.loadDefaultStyle()
                tomtomMap.logoSettings.applyDefaultLogo()
                //end::set_default_style[]
            }
        })
    }

    private fun showNightStyle() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_set_style[]
                tomtomMap.uiSettings.setStyleUrl("asset://styles/night.json")
                tomtomMap.logoSettings.applyInvertedLogo()
                //end::doc_set_style[]
            }
        })
    }

}
