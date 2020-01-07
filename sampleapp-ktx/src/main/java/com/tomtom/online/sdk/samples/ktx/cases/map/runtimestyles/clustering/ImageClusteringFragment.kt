/**
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.clustering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R

class ImageClusteringFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onExampleStarted() {
        confStyleForExample()
        centerOnLocation(location = Locations.AMSTERDAM, zoomLevel = ZOOM_LEVEL_FOR_EXAMPLE)
    }

    override fun onExampleEnded() {
        cleanup()
    }

    private fun confStyleForExample() {
        mainViewModel.applyOnMap(MapAction {
            val imageDrawer = ImageClusteringDrawer(requireContext(), this)
            imageDrawer.addImages()
            imageDrawer.addSourceToStyle()
            imageDrawer.addLayersToStyle()
        })
    }

    private fun cleanup() {
        mainViewModel.applyOnMap(MapAction {
            val imageDrawer = ImageClusteringDrawer(requireContext(), this)
            imageDrawer.removeImages()
            imageDrawer.removeLayers()
            imageDrawer.removeSource()
        })
    }

    companion object {
        private const val ZOOM_LEVEL_FOR_EXAMPLE = 9.0
    }
}