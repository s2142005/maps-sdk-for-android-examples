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
package com.tomtom.online.sdk.samples.ktx.cases.map.events

import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.map.TomtomMapCallback.*

@Suppress("unused")
class CameraEventsExample(private val tomtomMap: TomtomMap) {

    private fun exampleForCameraMovementCallbacks() {
        //tag::doc_map_define_camera_manipulation_listeners[]
        val onCameraChangedListener = OnCameraChangedListener {
            displayMessage(ON_CAMERA_CHANGED_MESSAGE)
        }
        val onCameraMoveStartedListener = OnCameraMoveStartedListener {
            displayMessage(ON_CAMERA_MOVE_STARTED_MESSAGE)
        }
        val onCameraMoveListener = OnCameraMoveListener {
            displayMessage(ON_CAMERA_MOVE_MESSAGE)
        }
        val onCameraMoveFinishedListener = OnCameraMoveFinishedListener {
            displayMessage(ON_CAMERA_MOVE_FINISHED_MESSAGE)
        }
        val onCameraMoveCanceledListener = OnCameraMoveCanceledListener {
            displayMessage(ON_CAMERA_MOVE_CANCELED_MESSAGE)
        }
        //end::doc_map_define_camera_manipulation_listeners[]

        //tag::doc_map_set_camera_manipulation_listeners[]
        tomtomMap.addOnCameraChangedListener(onCameraChangedListener)
        tomtomMap.addOnCameraMoveStartedListener(onCameraMoveStartedListener)
        tomtomMap.addOnCameraMoveListener(onCameraMoveListener)
        tomtomMap.addOnCameraMoveFinishedListener(onCameraMoveFinishedListener)
        tomtomMap.addOnCameraMoveCanceledListener(onCameraMoveCanceledListener)
        //end::doc_map_set_camera_manipulation_listeners[]

        //tag::doc_map_unregister_camera_manipulation_listeners[]
        tomtomMap.removeOnCameraChangedListener(onCameraChangedListener)
        tomtomMap.removeOnCameraMoveStartedListener(onCameraMoveStartedListener)
        tomtomMap.removeOnCameraMoveListener(onCameraMoveListener)
        tomtomMap.removeOnCameraMoveFinishedListener(onCameraMoveFinishedListener)
        tomtomMap.removeOnCameraMoveCanceledListener(onCameraMoveCanceledListener)
        //end::doc_map_unregister_camera_manipulation_listeners[]
    }

    @Suppress("UNUSED_PARAMETER")
    private fun displayMessage(message: String) {}

    private companion object {
        private const val ON_CAMERA_CHANGED_MESSAGE = "Camera changed"
        private const val ON_CAMERA_MOVE_STARTED_MESSAGE = "Camera move started"
        private const val ON_CAMERA_MOVE_MESSAGE = "Camera move"
        private const val ON_CAMERA_MOVE_FINISHED_MESSAGE = "Camera move finished"
        private const val ON_CAMERA_MOVE_CANCELED_MESSAGE = "Camera move canceled"
    }
}