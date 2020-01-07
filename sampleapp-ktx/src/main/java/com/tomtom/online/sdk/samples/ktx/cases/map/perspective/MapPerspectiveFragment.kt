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

package com.tomtom.online.sdk.samples.ktx.cases.map.perspective

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_perspective.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class MapPerspectiveFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        mainViewModel.applyOnMap(MapAction { set2DMode() })
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_perspective, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        map_perspective_2d_btn.setOnClickListener {
            mainViewModel.applyOnMap(MapAction {
                let { tomtomMap ->
                    //tag::doc_change_map_to_2D[]
                    tomtomMap.set2DMode()
                    //end::doc_change_map_to_2D[]
                }
            })
        }

        map_perspective_3d_btn.setOnClickListener {
            mainViewModel.applyOnMap(MapAction {
                let { tomtomMap ->
                    //tag::doc_change_map_to_3D[]
                    tomtomMap.set3DMode()
                    //end::doc_change_map_to_3D[]
                }
            })
        }
    }

}