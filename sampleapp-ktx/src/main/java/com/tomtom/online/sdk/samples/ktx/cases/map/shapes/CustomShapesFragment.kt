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

package com.tomtom.online.sdk.samples.ktx.cases.map.shapes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_custom_shapes.*
import kotlinx.android.synthetic.main.fragment_map_custom_shapes.*

class CustomShapesFragment : ExampleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_custom_shapes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnAmsterdamWithCustomZoom()
    }

    override fun onResume() {
        super.onResume()
        registerListeners()
    }

    override fun onPause() {
        super.onPause()
        unregisterListeners()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        removeShapes()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_custom_shapes, shapesControlButtonsContainer, true)
    }

    private fun confViewActions() {
        map_custom_shapes_polygon_btn.setOnClickListener {
            removeShapes()
            centerOnAmsterdamWithCustomZoom()
            drawPolygon()
        }

        map_custom_shapes_polyline_btn.setOnClickListener {
            removeShapes()
            centerOnAmsterdamWithCustomZoom()
            drawPolyline()
        }

        map_custom_shapes_circle_btn.setOnClickListener {
            removeShapes()
            centerOnAmsterdamWithCustomZoom()
            drawCircle()
        }
    }

    private fun drawPolygon() {
        mainViewModel.applyOnMap(MapAction {
            ShapesDrawer(this).createPolygon(Locations.AMSTERDAM)
        })
    }

    private fun drawPolyline() {
        mainViewModel.applyOnMap(MapAction {
            ShapesDrawer(this).createPolyline(Locations.AMSTERDAM)
        })
    }

    private fun drawCircle() {
        mainViewModel.applyOnMap(MapAction {
            ShapesDrawer(this).createCircle(Locations.AMSTERDAM)
        })
    }

    private fun registerListeners() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_register_shape_listeners[]
                tomtomMap.addOnCircleClickListener { infoTextView.displayAsToast(getString(R.string.toast_circle_clicked)) }
                tomtomMap.addOnPolygonClickListener { infoTextView.displayAsToast(getString(R.string.toast_polygon_clicked)) }
                tomtomMap.addOnPolylineClickListener { infoTextView.displayAsToast(getString(R.string.toast_polyline_clicked)) }
                //end::doc_register_shape_listeners[]
            }
        })
    }

    private fun unregisterListeners() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_unregister_shape_listeners[]
                tomtomMap.removeOnCircleClickListeners()
                tomtomMap.removeOnPolygonClickListeners()
                tomtomMap.removeOnPolylineClickListeners()
                //end::doc_unregister_shape_listeners[]
            }
        })
    }

    private fun centerOnAmsterdamWithCustomZoom() {
        centerOnLocation(zoomLevel = ZOOM_LEVEL_FOR_EXAMPLE)
    }

    private fun removeShapes() {
        mainViewModel.applyOnMap(MapAction { overlaySettings.removeOverlays() })
    }

    companion object {
        private const val ZOOM_LEVEL_FOR_EXAMPLE = 9.0
    }

}
