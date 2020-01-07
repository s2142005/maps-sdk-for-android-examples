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

package com.tomtom.online.sdk.samples.ktx.cases.map.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.core.maps.gestures.GesturesDetectionSettings
import com.tomtom.core.maps.gestures.GesturesDetectionSettingsBuilder
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.TomtomMapCallback
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.views.InfoTextView
import com.tomtom.sdk.examples.R
import java.util.*

class MapEventsFragment : ExampleFragment() {

    private lateinit var infoTextView: InfoTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoTextView = view.findViewById(R.id.infoTextView)
    }

    override fun onResume() {
        super.onResume()
        registerListeners()
    }

    override fun onPause() {
        super.onPause()
        unregisterListeners()
    }

    private fun registerListeners() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_map_set_map_manipulation_listeners[]
                tomtomMap.addOnMapClickListener(showMessageWhenOnMapClick)
                tomtomMap.addOnMapLongClickListener(showMessageWhenOnMapLongClick)
                tomtomMap.addOnMapViewPortChangedListener(showMessageWhenOnMapViewPortChanges)
                //end::doc_map_set_map_manipulation_listeners[]
            }
        })
    }

    private fun unregisterListeners() {
        mainViewModel.applyOnMap(MapAction { removeOnMapClickListener(showMessageWhenOnMapClick) })
        mainViewModel.applyOnMap(MapAction { removeOnMapLongClickListener(showMessageWhenOnMapLongClick) })
        mainViewModel.applyOnMap(MapAction { removeOnMapViewPortChangedListener(showMessageWhenOnMapViewPortChanges) })
    }

    //Lambda cannot be used in the below code as it causes memory leaks
    //In that case, it is required to use the object syntax

    //tag::doc_map_define_map_manipulation_listeners[]
    private val showMessageWhenOnMapClick: TomtomMapCallback.OnMapClickListener = object : TomtomMapCallback.OnMapClickListener {
        override fun onMapClick(latLng: LatLng) {
            displayMessage(SINGLE_PRESS_TITLE, latLng)
        }
    }

    private val showMessageWhenOnMapLongClick: TomtomMapCallback.OnMapLongClickListener = object : TomtomMapCallback.OnMapLongClickListener {
        override fun onMapLongClick(latLng: LatLng) {
            displayMessage(LONG_CLICK_TITLE, latLng)
        }
    }

    private val showMessageWhenOnMapViewPortChanges: TomtomMapCallback.OnMapViewPortChanged = object : TomtomMapCallback.OnMapViewPortChanged {
        override fun onMapViewPortChanged(lat: Float, lon: Float, zoom: Double, perspectie: Float, yaw: Float) {
            displayMessage(VIEW_PORT_CHANGE_TITLE, LatLng(lat.toDouble(), lon.toDouble()))
        }
    }
    //end::doc_map_define_map_manipulation_listeners[]

    private fun displayMessage(title: String, latLng: LatLng) {
        val message = String.format(Locale.getDefault(), TOAST_MESSAGE, title,
                latLng.latitude, latLng.longitude)
        infoTextView.displayAsToast(message)
    }

    // This method is only here to provide dynamic code snippet for documentation.
    @Suppress("unused")
    private fun exampleForGesturesDetectionEnabling() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_gesture_disable_zoom[]
                tomtomMap.updateGesturesDetectionSettings(
                        GesturesDetectionSettingsBuilder.create()
                                .zoomEnabled(false)
                                .build())
                //end::doc_gesture_disable_zoom[]

                //tag::doc_gesture_disable_tilt[]
                tomtomMap.updateGesturesDetectionSettings(
                        GesturesDetectionSettingsBuilder.create()
                                .tiltEnabled(false)
                                .build()
                )
                //end::doc_gesture_disable_tilt[]

                //tag::doc_gesture_disable_rotation[]
                tomtomMap.updateGesturesDetectionSettings(
                        GesturesDetectionSettingsBuilder.create()
                                .rotationEnabled(false)
                                .build()
                )
                //end::doc_gesture_disable_rotation[]

                //tag::doc_gesture_disable_panning[]
                tomtomMap.updateGesturesDetectionSettings(
                        GesturesDetectionSettingsBuilder.create()
                                .panningEnabled(false)
                                .build()
                )
                //end::doc_gesture_disable_panning[]

                //tag::doc_gesture_disable_rotation_panning[]
                tomtomMap.updateGesturesDetectionSettings(
                        GesturesDetectionSettingsBuilder.create()
                                .rotationEnabled(false)
                                .panningEnabled(false)
                                .build()
                )
                //end::doc_gesture_disable_rotation_panning[]

                //tag::doc_gesture_enable_all[]
                tomtomMap.updateGesturesDetectionSettings(
                        GesturesDetectionSettingsBuilder.create()
                                .rotationEnabled(true)
                                .panningEnabled(true)
                                .zoomEnabled(true)
                                .tiltEnabled(true)
                                .build()
                )
                //end::doc_gesture_enable_all[]

                //tag::doc_gesture_enable_all_default[]
                tomtomMap.updateGesturesDetectionSettings(
                        GesturesDetectionSettings.createDefault()
                )
                //end::doc_gesture_enable_all_default[]
            }
        })
    }


    companion object {
        private const val TOAST_MESSAGE = "%s : %f, %f"
        private const val LONG_CLICK_TITLE = "Long Press"
        private const val SINGLE_PRESS_TITLE = "Single Press"
        private const val VIEW_PORT_CHANGE_TITLE = "Panning"
    }

}