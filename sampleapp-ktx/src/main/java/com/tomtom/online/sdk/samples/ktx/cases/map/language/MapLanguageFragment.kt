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

package com.tomtom.online.sdk.samples.ktx.cases.map.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_map_language.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class MapLanguageFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        setMapLanguage("en-GB")
        centerOnLocation(location = Locations.LODZ, zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE)
    }

    override fun onExampleEnded() {
        mainViewModel.applyOnMap(MapAction { setLanguage("NGT") })
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_map_language, mapControlButtonsContainer, true)
    }

    private fun setMapLanguage(language: String) {
        mainViewModel.applyOnMap(MapAction {
            val tomtomMap = this
            //tag::doc_map_language[]
            //language can be equal e.g. to "en-GB"
            tomtomMap.setLanguage(language)
            //end::doc_map_language[]
        })
    }

    private fun confViewActions() {
        map_language_english_btn.setOnClickListener {
            setMapLanguage("en-GB")
        }

        map_language_russian_btn.setOnClickListener {
            setMapLanguage("ru-RU")
        }

        map_language_dutch_btn.setOnClickListener {
            setMapLanguage("nl-NL")
        }
    }

    companion object {
        private const val DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 3.0
    }

}