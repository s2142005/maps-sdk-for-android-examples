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
package com.tomtom.online.sdk.samples.cases.map.manipulation.events;

import androidx.annotation.StringRes;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.map.gestures.GesturesConfiguration;
import com.tomtom.online.sdk.map.rx.RxTomtomMap;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class MapManipulationEventsPresenter extends BaseFunctionalExamplePresenter {

    private static final String TOAST_MESSAGE = "%s : %f, %f";
    private static final int TOAST_DURATION = 2000; //milliseconds

    private CompositeDisposable rxDisposables = new CompositeDisposable();

    private TomtomMapCallback.OnMapClickListener onMapClickListener =
            latLng -> displayMessage(
                    R.string.menu_events_on_map_click,
                    latLng
            );
    private TomtomMapCallback.OnMapLongClickListener onMapLongClickListener =
            latLng -> displayMessage(
                    R.string.menu_events_on_map_long_click,
                    latLng
            );
    private TomtomMapCallback.OnCameraChangedListener onCameraChangedListener =
            (cameraPosition) -> displayMessage(
                    R.string.menu_events_on_map_panning,
                    cameraPosition.getFocusPosition()
            );

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);

        setupTomtomMap();
        setupRxTomtomMap();

        if (!view.isMapRestored()) {
            centerOn(Locations.AMSTERDAM_LOCATION);
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new MapManipulationEventsFunctionalExample();
    }

    @Override
    public void cleanup() {
        Timber.d("RxUtils: clean()");
        rxDisposables.dispose();
    }

    private void setupTomtomMap() {
        tomtomMap.addOnMapClickListener(onMapClickListener);
        tomtomMap.addOnMapLongClickListener(onMapLongClickListener);
        tomtomMap.addOnCameraChangedListener(onCameraChangedListener);
    }

    private void setupRxTomtomMap() {
        //tag::doc_map_set_rx_wrapper[]
        RxTomtomMap rxTomtomMap = new RxTomtomMap(tomtomMap);
        //end::doc_map_set_rx_wrapper[]

        //tag::doc_map_set_map_manipulation_observables[]
        Disposable mapClickDisposable = rxTomtomMap
                .getOnMapClickObservable()
                .observeOn(mainThread())
                .subscribe(latLng -> displayMessage(
                        R.string.menu_events_on_map_click,
                        latLng
                ));
        //end::doc_map_set_map_manipulation_observables[]
        rxDisposables.add(mapClickDisposable);
    }

    private void displayMessage(@StringRes int titleId, LatLng latLng) {

        if (view.getContext() == null) {
            return;
        }

        String title = view.getContext().getString(titleId);
        Timber.v("Functional Example on %s", title);
        String message = String.format(java.util.Locale.getDefault(),
                TOAST_MESSAGE,
                title,
                latLng.getLatitude(),
                latLng.getLongitude());

        view.showInfoText(message, TOAST_DURATION);
    }

    @SuppressWarnings("unused")
    private void exampleForMapManipulationListeners() {
        //tag::doc_map_define_map_manipulation_listeners[]
        TomtomMapCallback.OnMapClickListener onMapClickListener =
                latLng -> displayMessage(R.string.menu_events_on_map_click, latLng);

        TomtomMapCallback.OnMapLongClickListener onMapLongClickListener =
                latLng -> displayMessage(R.string.menu_events_on_map_long_click, latLng);

        TomtomMapCallback.OnMapDoubleClickListener onMapDoubleClickListener =
                latLng -> displayMessage(R.string.menu_events_on_map_double_click, latLng);

        TomtomMapCallback.OnMapPanningListener onMapPanningListener = new TomtomMapCallback.OnMapPanningListener() {
            @Override
            public void onMapPanningStarted() {
                displayMessage(R.string.menu_events_on_map_panning_started, tomtomMap.getCenterOfMap());
            }

            @Override
            public void onMapPanningOngoing() {
                displayMessage(R.string.menu_events_on_map_panning_ongoing, tomtomMap.getCenterOfMap());
            }

            @Override
            public void onMapPanningEnded() {
                displayMessage(R.string.menu_events_on_map_panning_ended, tomtomMap.getCenterOfMap());
            }
        };
        //end::doc_map_define_map_manipulation_listeners[]

        //tag::doc_map_set_map_manipulation_listeners[]
        tomtomMap.addOnMapClickListener(onMapClickListener);
        tomtomMap.addOnMapLongClickListener(onMapLongClickListener);
        tomtomMap.addOnMapDoubleClickListener(onMapDoubleClickListener);
        tomtomMap.addOnMapPanningListener(onMapPanningListener);
        //end::doc_map_set_map_manipulation_listeners[]

        //tag::doc_map_unregister_map_manipulation_listeners[]
        tomtomMap.removeOnMapClickListener(onMapClickListener);
        tomtomMap.removeOnMapLongClickListener(onMapLongClickListener);
        tomtomMap.removeOnMapDoubleClickListener(onMapDoubleClickListener);
        tomtomMap.removeOnMapPanningListener(onMapPanningListener);
        //end::doc_map_unregister_map_manipulation_listeners[]
    }

    // This method is only here to provide dynamic code snippet for documentation.
    @SuppressWarnings("unused")
    private void exampleForGesturesDetectionEnabling() {
        //tag::doc_gesture_disable_zoom[]
        tomtomMap.updateGesturesConfiguration(
                new GesturesConfiguration.Builder()
                        .zoomEnabled(false)
                        .build()
        );
        //end::doc_gesture_disable_zoom[]

        //tag::doc_gesture_disable_tilt[]
        tomtomMap.updateGesturesConfiguration(
                new GesturesConfiguration.Builder()
                        .tiltEnabled(false)
                        .build()
        );
        //end::doc_gesture_disable_tilt[]

        //tag::doc_gesture_disable_rotation[]
        tomtomMap.updateGesturesConfiguration(
                new GesturesConfiguration.Builder()
                        .rotationEnabled(false)
                        .build()
        );
        //end::doc_gesture_disable_rotation[]

        //tag::doc_gesture_disable_panning[]
        tomtomMap.updateGesturesConfiguration(
                new GesturesConfiguration.Builder()
                        .panningEnabled(false)
                        .build()
        );
        //end::doc_gesture_disable_panning[]

        //tag::doc_gesture_disable_rotation_panning[]
        tomtomMap.updateGesturesConfiguration(
                new GesturesConfiguration.Builder()
                        .rotationEnabled(false)
                        .panningEnabled(false)
                        .build()
        );
        //end::doc_gesture_disable_rotation_panning[]

        //tag::doc_gesture_enable_all[]
        tomtomMap.updateGesturesConfiguration(
                new GesturesConfiguration.Builder()
                        .rotationEnabled(true)
                        .panningEnabled(true)
                        .zoomEnabled(true)
                        .tiltEnabled(true)
                        .build()
        );
        //end::doc_gesture_enable_all[]

        //tag::doc_gesture_enable_all_default[]
        tomtomMap.updateGesturesConfiguration(new GesturesConfiguration.Builder().build());
        //end::doc_gesture_enable_all_default[]
    }
}