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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.sources

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.core.maps.OnMapTapListener
import com.tomtom.online.sdk.common.func.FuncUtils
import com.tomtom.online.sdk.common.geojson.FeatureCollection
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_map_dynamic_sources.*

class DynamicSourcesFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_dynamic_sources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewActions()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.applyOnMap(MapAction {
            gestureDetector.addOnMapTapListener(geoJsonTapListener)
        })
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.applyOnMap(MapAction {
            gestureDetector.removeOnMapTapListener(geoJsonTapListener)
        })
    }

    override fun onExampleStarted() {
        centerOnLocation(location = Locations.BUCKINGHAM_PALACE, zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE)
    }

    override fun onExampleEnded() {
        removeDynamicSource(GEOJSON_LAYER_ID, GEOJSON_SOURCE_ID)
        removeDynamicSource(IMAGE_LAYER_ID, IMAGE_SOURCE_ID)
    }

    private fun confViewActions() {
        map_dynamic_sources_geojson_btn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                when (isChecked) {
                    true -> addCustomGeoJsonToStyle()
                    false -> removeDynamicSource(GEOJSON_LAYER_ID, GEOJSON_SOURCE_ID)
                }
            }
        }
        map_dynamic_sources_image_btn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                when (isChecked) {
                    true -> addCustomImageToStyle()
                    false -> removeDynamicSource(IMAGE_LAYER_ID, IMAGE_SOURCE_ID)
                }
            }
        }
    }

    private fun addCustomGeoJsonToStyle() {
        mainViewModel.applyOnMap(MapAction {
            DynamicSourcesDrawer(requireContext(), this).addCustomGeoJsonToStyle()
        })
    }

    private fun addCustomImageToStyle() {
        mainViewModel.applyOnMap(MapAction {
            DynamicSourcesDrawer(requireContext(), this).addCustomImageToStyle()
        })
    }

    private val geoJsonTapListener = object : OnMapTapListener {
        override fun onMapTap(x: Float, y: Float) {
            mainViewModel.applyOnMap(MapAction {
                let { tomtomMap ->
                    val point = PointF(x, y)
                    //tag::query_style_for_features[]
                    val layerIds = listOf(GEOJSON_LAYER_ID)
                    val featureCollection = tomtomMap.displaySettings.featuresAtPoint(point, layerIds)
                    //end::query_style_for_features[]
                    processFeatureCollection(featureCollection)
                }
            })
        }

        override fun onMapLongTap(x: Float, y: Float) {
            //not used right now
        }
    }


    private fun processFeatureCollection(featureCollection: FeatureCollection) {
        //tag::process_feature_list[]
        val featureList = featureCollection.features
        FuncUtils.forEach(featureList) { feature -> FuncUtils.apply(feature.id) { id -> displayToast(id) } }
        //end::process_feature_list[]
    }

    private fun displayToast(id: String) =
        infoTextView.displayAsToast(requireContext().getString(R.string.interactive_layers_polygon, id), TOAST_DURATION)

    private fun removeDynamicSource(layerId: String, sourceId: String) {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::remove_layer_and_source[]
                tomtomMap.styleSettings.removeLayer(layerId)
                tomtomMap.styleSettings.removeSource(sourceId)
                //end::remove_layer_and_source[]
            }
        })
    }

    companion object {
        private const val DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 15.0
        private const val TOAST_DURATION = 2000L //milliseconds
        private const val IMAGE_SOURCE_ID = "image-source-id"
        private const val IMAGE_LAYER_ID = "layer-image-id"
        private const val GEOJSON_SOURCE_ID = "geojson-source-id"
        private const val GEOJSON_LAYER_ID = "layer-line-id"
    }
}