/*
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
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
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.CameraPosition
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.map.TomtomMapCallback
import com.tomtom.online.sdk.map.gestures.GesturesConfiguration
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
                tomtomMap.addOnMapClickListener(showMessageWhenOnMapClick)
                tomtomMap.addOnMapLongClickListener(showMessageWhenOnMapLongClick)
                tomtomMap.addOnCameraChangedListener(showMessageWhenCameraChanged)
            }
        })
    }

    private fun unregisterListeners() {
        mainViewModel.applyOnMap(MapAction {
            removeOnMapClickListener(showMessageWhenOnMapClick)
            removeOnMapLongClickListener(showMessageWhenOnMapLongClick)
            removeOnCameraChangedListener(showMessageWhenCameraChanged)
        })
    }

    //Lambda cannot be used in the below code as it causes memory leaks
    //In that case, it is required to use the object syntax
    private val showMessageWhenOnMapClick: TomtomMapCallback.OnMapClickListener =
        object : TomtomMapCallback.OnMapClickListener {
            override fun onMapClick(latLng: LatLng) {
                displayMessage(SINGLE_PRESS_TITLE, latLng)
            }
        }

    private val showMessageWhenOnMapLongClick: TomtomMapCallback.OnMapLongClickListener =
        object : TomtomMapCallback.OnMapLongClickListener {
            override fun onMapLongClick(latLng: LatLng) {
                displayMessage(LONG_CLICK_TITLE, latLng)
            }
        }

    private val showMessageWhenCameraChanged: TomtomMapCallback.OnCameraChangedListener =
        object : TomtomMapCallback.OnCameraChangedListener {
            override fun onCameraChanged(cameraPosition: CameraPosition) {
                displayMessage(VIEW_PORT_CHANGE_TITLE, cameraPosition.focusPosition)
            }
        }

    private fun displayMessage(title: String, latLng: LatLng) {
        val message = String.format(
            Locale.getDefault(), TOAST_MESSAGE, title,
            latLng.latitude, latLng.longitude
        )
        infoTextView.displayAsToast(message)
    }

    private fun displayMessage(title: String) {
        val message = String.format(Locale.getDefault(), TOAST_MESSAGE, title)
        infoTextView.displayAsToast(message)
    }

    @Suppress("unused")
    private fun exampleForMapManipulationListeners(tomtomMap: TomtomMap) {
        //tag::doc_map_define_map_manipulation_listeners[]
        val showMessageWhenOnMapClick: TomtomMapCallback.OnMapClickListener =
            TomtomMapCallback.OnMapClickListener { latLng -> displayMessage(SINGLE_PRESS_TITLE, latLng) }

        val showMessageWhenOnMapLongClick: TomtomMapCallback.OnMapLongClickListener =
            TomtomMapCallback.OnMapLongClickListener { latLng -> displayMessage(LONG_CLICK_TITLE, latLng) }

        val showMessageWhenOnMapDoubleClick: TomtomMapCallback.OnMapDoubleClickListener =
            TomtomMapCallback.OnMapDoubleClickListener { latLng -> displayMessage(DOUBLE_CLICK_TITLE, latLng) }

        val showMessageWhenMapPanningOccurs: TomtomMapCallback.OnMapPanningListener =
            object : TomtomMapCallback.OnMapPanningListener {
                override fun onMapPanningStarted() {
                    displayMessage(MAP_PANNING_STARTED_TITLE)
                }

                override fun onMapPanningOngoing() {
                    displayMessage(MAP_PANNING_ONGOING_TITLE)
                }

                override fun onMapPanningEnded() {
                    displayMessage(MAP_PANNING_ENDED_TITLE)
                }
            }
        //end::doc_map_define_map_manipulation_listeners[]

        //tag::doc_map_set_map_manipulation_listeners[]
        tomtomMap.addOnMapClickListener(showMessageWhenOnMapClick)
        tomtomMap.addOnMapLongClickListener(showMessageWhenOnMapLongClick)
        tomtomMap.addOnMapDoubleClickListener(showMessageWhenOnMapDoubleClick)
        tomtomMap.addOnMapPanningListener(showMessageWhenMapPanningOccurs)
        //end::doc_map_set_map_manipulation_listeners[]

        //tag::doc_map_unregister_map_manipulation_listeners[]
        tomtomMap.removeOnMapClickListener(showMessageWhenOnMapClick)
        tomtomMap.removeOnMapLongClickListener(showMessageWhenOnMapLongClick)
        tomtomMap.removeOnMapDoubleClickListener(showMessageWhenOnMapDoubleClick)
        tomtomMap.removeOnMapPanningListener(showMessageWhenMapPanningOccurs)
        //end::doc_map_unregister_map_manipulation_listeners[]
    }

    // This method is only here to provide dynamic code snippet for documentation.
    @Suppress("unused")
    private fun exampleForGesturesDetectionEnabling() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_gesture_disable_zoom[]
                tomtomMap.updateGesturesConfiguration(
                    GesturesConfiguration.Builder()
                        .zoomEnabled(false)
                        .build()
                )
                //end::doc_gesture_disable_zoom[]

                //tag::doc_gesture_disable_tilt[]
                tomtomMap.updateGesturesConfiguration(
                    GesturesConfiguration.Builder()
                        .tiltEnabled(false)
                        .build()
                )
                //end::doc_gesture_disable_tilt[]

                //tag::doc_gesture_disable_rotation[]
                tomtomMap.updateGesturesConfiguration(
                    GesturesConfiguration.Builder()
                        .rotationEnabled(false)
                        .build()
                )
                //end::doc_gesture_disable_rotation[]

                //tag::doc_gesture_disable_panning[]
                tomtomMap.updateGesturesConfiguration(
                    GesturesConfiguration.Builder()
                        .panningEnabled(false)
                        .build()
                )
                //end::doc_gesture_disable_panning[]

                //tag::doc_gesture_disable_rotation_panning[]
                tomtomMap.updateGesturesConfiguration(
                    GesturesConfiguration.Builder()
                        .rotationEnabled(false)
                        .panningEnabled(false)
                        .build()
                )
                //end::doc_gesture_disable_rotation_panning[]

                //tag::doc_gesture_enable_all[]
                tomtomMap.updateGesturesConfiguration(
                    GesturesConfiguration.Builder()
                        .rotationEnabled(true)
                        .panningEnabled(true)
                        .zoomEnabled(true)
                        .tiltEnabled(true)
                        .build()
                )
                //end::doc_gesture_enable_all[]

                //tag::doc_gesture_enable_all_default[]
                tomtomMap.updateGesturesConfiguration(
                    GesturesConfiguration.Builder().build()
                )
                //end::doc_gesture_enable_all_default[]
            }
        })
    }

    companion object {
        private const val TOAST_MESSAGE = "%s : %f, %f"
        private const val LONG_CLICK_TITLE = "Long Click"
        private const val DOUBLE_CLICK_TITLE = "Double Click"
        private const val MAP_PANNING_STARTED_TITLE = "Panning started"
        private const val MAP_PANNING_ONGOING_TITLE = "Panning ongoing"
        private const val MAP_PANNING_ENDED_TITLE = "Panning ended"
        private const val SINGLE_PRESS_TITLE = "Single Click"
        private const val VIEW_PORT_CHANGE_TITLE = "Panning"
    }
}