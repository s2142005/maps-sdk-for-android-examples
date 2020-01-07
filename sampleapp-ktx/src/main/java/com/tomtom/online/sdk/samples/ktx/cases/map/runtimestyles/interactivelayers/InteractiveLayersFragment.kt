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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.interactivelayers

import android.graphics.PointF
import android.graphics.RectF
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.google.common.base.Joiner
import com.tomtom.core.maps.MapChangedListenerAdapter
import com.tomtom.core.maps.OnMapTapListener
import com.tomtom.online.sdk.common.geojson.Feature
import com.tomtom.online.sdk.common.geojson.FeatureCollection
import com.tomtom.online.sdk.map.MapFragment
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_map_events.*

class InteractiveLayersFragment : ExampleFragment() {

    private lateinit var mapViewPort: RectF

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_events, container, false)
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        confStyle()
        centerOnLocation(location = Locations.LONDON, zoomLevel = ZOOM_LEVEL_FOR_EXAMPLE)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed(onExampleRestored, HANDLER_DELAY)
    }

    override fun onPause() {
        super.onPause()
        Handler().removeCallbacks(onExampleRestored)
        unregisterListeners()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        cleanup()
    }

    private fun confStyle() {
        mainViewModel.applyOnMap(MapAction {
            InteractiveLayersCreator(requireContext(), this).addFeaturesToStyle()
        })
    }

    private fun registerListeners() {
        mainViewModel.applyOnMap(MapAction {
            addOnMapChangedListener(showMessageWhenCameraChanges)
            gestureDetector.addOnMapTapListener(showMessageWhenOnMapTap)
        })
        infoTextView.viewTreeObserver.addOnGlobalLayoutListener(infoTextViewLayoutListener)
    }

    private fun unregisterListeners() {
        mainViewModel.applyOnMap(MapAction {
            removeOnMapChangedListener(showMessageWhenCameraChanges)
            gestureDetector.removeOnMapTapListener(showMessageWhenOnMapTap)
        })
        infoTextView.viewTreeObserver.removeOnGlobalLayoutListener(infoTextViewLayoutListener)
    }

    private val showMessageWhenOnMapTap = object : OnMapTapListener {
        override fun onMapTap(x: Float, y: Float) {
            findFeaturesAtPoint(x, y)
        }

        override fun onMapLongTap(x: Float, y: Float) {
            findFeaturesAtPoint(x, y)
        }
    }

    private val showMessageWhenCameraChanges = object : MapChangedListenerAdapter() {
        override fun onCameraDidChange() {
            findFeaturesInViewPort()
        }
    }

    private val infoTextViewLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val visibility = infoTextView.visibility
        if (visibility == View.GONE) {
            findFeaturesInViewPort()
        }
    }

    private val onExampleRestored = Runnable {
        calculateViewport()
        registerListeners()
        findFeaturesInViewPort()
    }

    private fun displayTimedToastMessage(message: String) = infoTextView.displayAsToast(message)

    private fun displayPermanentToastMessage(message: String) = infoTextView.displayPermanently(message)

    private fun findFeaturesInViewPort() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::query_style_for_features_in_viewport[]
                val featureCollection = tomtomMap.displaySettings.featuresInScreenRect(mapViewPort, LAYER_LIST)
                //end::query_style_for_features_in_viewport[]

                val message = processFeatures(featureCollection, getString(R.string.interactive_layers_viewport_changed_header))
                displayPermanentToastMessage(message)
            }
        })
    }

    private fun findFeaturesAtPoint(x: Float, y: Float) {
        mainViewModel.applyOnMap(MapAction {
            val point = PointF(x, y)
            val featureCollection = displaySettings.featuresAtPoint(point, LAYER_LIST)
            val message = processFeatures(featureCollection, getString(R.string.interactive_layers_at_point_header))
            displayTimedToastMessage(message)
        })
    }

    private fun processFeatures(featureCollection: FeatureCollection, header: String): String = when (featureCollection.features.size) {
        0 -> getString(R.string.interactive_layers_no_features_found)
        else -> prepareMessage(featureCollection.features, header)
    }

    private fun prepareMessage(features: List<Feature>, header: String): String {
        val idList = Observable.fromIterable(features)
            .map { feature -> feature.id }
            .map { id -> id.or(DEFAULT_FEATURE_ID) }
            .toList()
            .blockingGet()

        val stringBuilder = StringBuilder(header)

        return Joiner.on(TOAST_MESSAGE_SPLITTER).appendTo(stringBuilder, idList.distinct()).toString()
    }

    private fun calculateViewport() {
        val mapFragment = activity!!.supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        val mapView = mapFragment.view!!

        mapViewPort = RectF(TOP_LEFT_IN_PIXELS.x, TOP_LEFT_IN_PIXELS.y, mapView.width.toFloat(), mapView.height.toFloat())
    }

    private fun cleanup() {
        mainViewModel.applyOnMap(MapAction {
            styleSettings.removeLayer(FILL_LAYER_ID)
            styleSettings.removeLayer(OUTLINE_LAYER_ID)
            styleSettings.removeSource(SOURCE_ID)
        })
    }

    companion object {
        private const val ZOOM_LEVEL_FOR_EXAMPLE = 9.0
        private const val HANDLER_DELAY = 150L
        private const val DEFAULT_FEATURE_ID = ""
        private const val FILL_LAYER_ID = "IL-test-layer-fill"
        private const val OUTLINE_LAYER_ID = "IL-test-layer-outline"
        private const val SOURCE_ID = "IL-test-source"
        private const val TOAST_MESSAGE_SPLITTER = ", "
        private val TOP_LEFT_IN_PIXELS = PointF(0.0f, 0.0f)
        private val LAYER_LIST = listOf(OUTLINE_LAYER_ID)
    }
}
