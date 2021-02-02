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
package com.tomtom.online.sdk.samples.cases.map.manipulation.events.camera;

import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.samples.R;

@SuppressWarnings("unused")
public class CameraManipulationListenersExample {

    private TomtomMap tomtomMap;

    public CameraManipulationListenersExample(TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
    }

    @SuppressWarnings("unused")
    private void exampleForCameraMovementCallbacks() {
        //tag::doc_map_define_camera_manipulation_listeners[]
        TomtomMapCallback.OnCameraChangedListener onCameraChangedListener =
                cameraPosition -> displayMessage(R.string.menu_events_on_camera_changed);

        TomtomMapCallback.OnCameraMoveStartedListener onCameraMoveStartedListener =
                () -> displayMessage(R.string.menu_events_on_camera_move_started);

        TomtomMapCallback.OnCameraMoveListener onCameraMoveListener =
                () -> displayMessage(R.string.menu_events_on_camera_move);

        TomtomMapCallback.OnCameraMoveFinishedListener onCameraMoveFinishedListener =
                () -> displayMessage(R.string.menu_events_on_camera_move_finished);

        TomtomMapCallback.OnCameraMoveCanceledListener onCameraMoveCanceledListener =
                () -> displayMessage(R.string.menu_events_on_camera_move_canceled);
        //end::doc_map_define_camera_manipulation_listeners[]

        //tag::doc_map_set_camera_manipulation_listeners[]
        tomtomMap.addOnCameraChangedListener(onCameraChangedListener);
        tomtomMap.addOnCameraMoveStartedListener(onCameraMoveStartedListener);
        tomtomMap.addOnCameraMoveListener(onCameraMoveListener);
        tomtomMap.addOnCameraMoveFinishedListener(onCameraMoveFinishedListener);
        tomtomMap.addOnCameraMoveCanceledListener(onCameraMoveCanceledListener);
        //end::doc_map_set_camera_manipulation_listeners[]

        //tag::doc_map_unregister_camera_manipulation_listeners[]
        tomtomMap.removeOnCameraChangedListener(onCameraChangedListener);
        tomtomMap.removeOnCameraMoveStartedListener(onCameraMoveStartedListener);
        tomtomMap.removeOnCameraMoveListener(onCameraMoveListener);
        tomtomMap.removeOnCameraMoveFinishedListener(onCameraMoveFinishedListener);
        tomtomMap.removeOnCameraMoveCanceledListener(onCameraMoveCanceledListener);
        //end::doc_map_unregister_camera_manipulation_listeners[]
    }

    private void displayMessage(int resId) {
    }
}
