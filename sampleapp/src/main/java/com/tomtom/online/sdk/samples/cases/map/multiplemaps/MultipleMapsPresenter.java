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
package com.tomtom.online.sdk.samples.cases.map.multiplemaps;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.map.gestures.GesturesConfiguration;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

public class MultipleMapsPresenter extends BaseFunctionalExamplePresenter {

    final static int SECOND_MAP_ANIMATION_TIME = 0;
    final static int MAP_ZOOM_LEVEL_FOR_SECOND_MAP_DIFF = 4;
    final static int DEFAULT_MAP_ZOOM_LEVEL_FOR_EXAMPLE = 12;

    private static final String NIGHT_STYLE_URL_PATH = "asset://styles/night.json";

    private TomtomMap miniTomtomMap;

    public void bindMiniMap(final TomtomMap map) {
        miniTomtomMap = map;
        configureMiniMap(miniTomtomMap);
    }

    @Override
    public void bind(final FunctionalExampleFragment view, final TomtomMap map) {
        super.bind(view, map);
        if (!view.isMapRestored()) {
            centerOn(Locations.AMSTERDAM_CENTER_LOCATION, DEFAULT_MAP_ZOOM_LEVEL_FOR_EXAMPLE);
        }
        tomtomMap.addOnCameraMoveFinishedListener(onMapChanged);
    }

    @Override
    public void onPause() {
        super.onPause();
        tomtomMap.removeOnCameraMoveFinishedListener(onMapChanged);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new MultipleMapsFunctionalExample();
    }

    private void configureMiniMap(final TomtomMap miniTomtomMap) {
        miniTomtomMap.setMyLocationEnabled(true);
        miniTomtomMap.getUiSettings().getCompassView().hide();
        miniTomtomMap.getUiSettings().getCurrentLocationView().hide();
        miniTomtomMap.getUiSettings().setStyleUrl(NIGHT_STYLE_URL_PATH);
        miniTomtomMap.getUiSettings().getLogoView().applyInvertedLogo();
        miniTomtomMap.updateGesturesConfiguration(
                new GesturesConfiguration.Builder()
                        .zoomEnabled(false)
                        .panningEnabled(false)
                        .tiltEnabled(false)
                        .enableEventsIntercepting(false)
                        .rotationEnabled(false)
                        .forceMinMaxZoom(false)
                        .build()
        );
    }

    private TomtomMapCallback.OnCameraMoveFinishedListener onMapChanged = new TomtomMapCallback.OnCameraMoveFinishedListener() {
        @Override
        public void onCameraMoveFinished() {
            // This callback is not called too often, only when map centering animation or map
            // transition using gestures is finished.
            // To have more frequent updates, one can register for onCameraChanged listener
            // However, this may cause performance issues as onCameraChanged is called very often.
            CameraPosition cameraPosition = tomtomMap.getUiSettings().getCameraPosition();
            LatLng focalLatLng = tomtomMap.getCenterOfMap();

            double miniMapZoomLevel = cameraPosition.getZoom() <= MAP_ZOOM_LEVEL_FOR_SECOND_MAP_DIFF ?
                    cameraPosition.getZoom() : cameraPosition.getZoom() - MAP_ZOOM_LEVEL_FOR_SECOND_MAP_DIFF;

            double miniMapBearing = cameraPosition.getBearing();

            CameraPosition miniMapPosition = CameraPosition.builder()
                    .focusPosition(focalLatLng)
                    .zoom(miniMapZoomLevel)
                    .bearing(miniMapBearing)
                    .animationDuration(SECOND_MAP_ANIMATION_TIME)
                    .build();

            if (miniTomtomMap != null) {
                miniTomtomMap.getUiSettings().setCameraPosition(miniMapPosition);
            }
        }
    };
}