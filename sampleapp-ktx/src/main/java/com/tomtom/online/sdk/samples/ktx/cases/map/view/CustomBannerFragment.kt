/**
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
@file:Suppress("DEPRECATION")

package com.tomtom.online.sdk.samples.ktx.cases.map.view

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.tomtom.core.maps.MapChangedListenerAdapter
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.TomtomMapCallback
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.sdk.examples.R

class CustomBannerFragment : ExampleFragment() {

    private lateinit var viewModel: CustomBannerViewModel
    private lateinit var container: FrameLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.container = inflater.inflate(R.layout.fragment_map_custom_banner, container, false) as FrameLayout
        return this.container
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        confViewModel()
        recreateViews()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnLocation()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_register_on_map_changed_listener[]
                tomtomMap.addOnMapChangedListener(onRenderFrameListener)
                //end::doc_register_on_map_changed_listener[]

                //tag::doc_register_on_map_long_click[]
                tomtomMap.addOnMapLongClickListener(onMapLongClickListener)
                //end::doc_register_on_map_long_click[]
            }
        })
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_unregister_listeners[]
                tomtomMap.removeOnMapChangedListener(onRenderFrameListener)
                tomtomMap.removeOnMapLongClickListener(onMapLongClickListener)
                //end::doc_unregister_listeners[]
            }
        })
    }

    private fun confViewModel() {
        viewModel = ViewModelProviders.of(this).get(CustomBannerViewModel::class.java)
    }

    private fun recreateViews() {
        val coordinatesList = viewModel.mapBannerPositions.toList()
        viewModel.mapBannerPositions.clear()
        mainViewModel.applyOnMap(MapAction {
            coordinatesList.forEach {
                val screenPosition = pixelForLatLng(it)
                createAndAddViewToLayout(it, screenPosition)
            }
        })
    }

    //tag::doc_create_and_inflate_view[]
    private fun createAndAddViewToLayout(position: LatLng, rawScreenPosition: PointF) {
        val view = inflateCustomView(position)
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                setViewPosition(view, rawScreenPosition)
                lockViewToPosition(position)
            }
        })
        container.addView(view)
    }
    //end::doc_create_and_inflate_view[]

    //tag::doc_inflate_view[]
    private fun inflateCustomView(position: LatLng): View {
        val annotationBalloon =
            LayoutInflater.from(requireContext()).inflate(R.layout.custom_banner_view, container, false)
        annotationBalloon.tag = position

        val textView = annotationBalloon.findViewById<TextView>(R.id.banner_title)
        textView.text = getString(R.string.custom_banner_title, position.latitude, position.longitude)

        val removeButton = annotationBalloon.findViewById<ImageButton>(R.id.banner_remove_button)
        removeButton.setOnClickListener { removeView(annotationBalloon) }

        return annotationBalloon
    }
    //end::doc_inflate_view[]

    private fun removeView(view: View) {
        viewModel.mapBannerPositions.remove(view.tag)
        container.removeView(view)
    }

    //tag::doc_calculate_offset[]
    private fun setViewPosition(view: View, rawScreenPosition: PointF) {
        view.x = rawScreenPosition.x - (view.width / 2)
        view.y = rawScreenPosition.y - view.height
    }
    //end::doc_calculate_offset[]

    private fun lockViewToPosition(position: LatLng) {
        //tag::doc_lock_view_with_position[]
        viewModel.mapBannerPositions.add(position)
        //end::doc_lock_view_with_position[]
    }

    private val onRenderFrameListener = object : MapChangedListenerAdapter() {
        override fun onDidFinishRenderingFrame() {
            mainViewModel.applyOnMap(MapAction {
                let { tomtomMap ->
                    //tag::doc_update_view_with_position[]
                    viewModel.mapBannerPositions.forEach { latLng ->
                        val screenPosition = tomtomMap.pixelForLatLng(latLng)
                        setViewPosition(container.findViewWithTag(latLng), screenPosition)
                    }
                    //end::doc_update_view_with_position[]
                }
            })
        }
    }

    //tag::doc_define_on_map_long_click[]
    private val onMapLongClickListener = TomtomMapCallback.OnMapLongClickListener { latLng ->
        processLongClickEvent(latLng)
    }
    //end::doc_define_on_map_long_click[]

    private fun processLongClickEvent(latLng: LatLng) {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_action_on_map_long_click[]
                val screenPosition = tomtomMap.pixelForLatLng(latLng)
                createAndAddViewToLayout(latLng, screenPosition)
                //end::doc_action_on_map_long_click[]
            }
        })
    }
}