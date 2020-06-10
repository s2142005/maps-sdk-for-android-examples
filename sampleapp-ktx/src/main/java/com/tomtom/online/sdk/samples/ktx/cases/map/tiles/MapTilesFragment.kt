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

package com.tomtom.online.sdk.samples.ktx.cases.map.tiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.map.model.MapTilesType
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_tiles.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class MapTilesFragment : ExampleFragment() {

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
        mainViewModel.applyOnMap(MapAction { uiSettings.loadDefaultStyle() })
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_tiles, mapControlButtonsContainer, true)
    }

    private fun confViewActions() {
        vector_tiles_btn.setOnClickListener {
            mainViewModel.applyOnMap(MapAction {
                let { tomtomMap ->
                    //tag::doc_load_vector_tiles[]
                    tomtomMap.uiSettings.loadDefaultStyle()
                    //end::doc_load_vector_tiles[]
                }
            })
        }

        raster_tiles_btn.setOnClickListener {
            mainViewModel.applyOnMap(MapAction {
                let { tomtomMap ->
                    //tag::doc_load_raster_tiles[]
                    tomtomMap.uiSettings.setStyleUrl("asset://styles/mapssdk-raster_style.json")
                    //end::doc_load_raster_tiles[]
                }
            })
        }
    }

    @Suppress("unused")
    private fun exampleOfUsingLegacyTiles() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_set_raster_tiles[]
                tomtomMap.uiSettings.mapTilesType = MapTilesType.RASTER
                //end::doc_set_raster_tiles[]

                //tag::doc_set_vector_tiles[]
                tomtomMap.uiSettings.mapTilesType = MapTilesType.VECTOR
                //end::doc_set_vector_tiles[]

                //tag::doc_set_none_tiles[]
                tomtomMap.uiSettings.mapTilesType = MapTilesType.NONE
                //end::doc_set_none_tiles[]
            }
        })
    }

}
