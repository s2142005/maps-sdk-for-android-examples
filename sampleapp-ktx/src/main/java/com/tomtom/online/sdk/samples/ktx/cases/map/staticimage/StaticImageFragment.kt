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

package com.tomtom.online.sdk.samples.ktx.cases.map.staticimage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tomtom.online.sdk.common.config.loader.SingleManifestValueLoader
import com.tomtom.online.sdk.common.config.provider.ConfigProvider
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.staticimage.StaticImage.LAYER_HYBRID
import com.tomtom.online.sdk.staticimage.StaticImage.STYLE_NIGHT
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_map_static_image.*

class StaticImageFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_static_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewActions()
        confImageViews(getMapsApiKey(requireContext()))
    }

    private fun confViewActions() {
        map_1.setOnClickListener { displayToastMessage(R.string.staticMap1_msg) }
        map_2.setOnClickListener { displayToastMessage(R.string.staticMap2_msg) }
        map_3.setOnClickListener { displayToastMessage(R.string.staticMap3_msg) }
        map_4.setOnClickListener { displayToastMessage(R.string.staticMap4_msg) }
        map_5.setOnClickListener { displayToastMessage(R.string.staticMap5_msg) }
        map_6.setOnClickListener { displayToastMessage(R.string.staticMap6_msg) }
    }

    private fun confImageViews(apiKey: String) {
        StaticImageDownloader().loadImage(mapsApiKey = apiKey, targetImageView = map_1)
        StaticImageDownloader().loadImage(mapsApiKey = apiKey, targetImageView = map_2, style = STYLE_NIGHT)
        StaticImageDownloader().loadImage(mapsApiKey = apiKey, targetImageView = map_3, zoomLevel = 15)
        StaticImageDownloader().loadImage(mapsApiKey = apiKey, targetImageView = map_4, layer = LAYER_HYBRID)
        StaticImageDownloader().loadImage(mapsApiKey = apiKey, targetImageView = map_5, style = STYLE_MAIN_AZURE)
        StaticImageDownloader().loadImage(mapsApiKey = apiKey, targetImageView = map_6, style = STYLE_NIGHT, zoomLevel = 8)
    }

    private fun displayToastMessage(messageId: Int) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show()
    }

    private fun getMapsApiKey(context: Context): String {
        return SingleManifestValueLoader(context, ConfigProvider.ONLINE_MAPS_KEY).value
    }

    companion object {
        private const val STYLE_MAIN_AZURE = "main-azure"
    }
}
