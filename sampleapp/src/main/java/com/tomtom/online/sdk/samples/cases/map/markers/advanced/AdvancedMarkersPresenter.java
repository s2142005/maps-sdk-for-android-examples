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
package com.tomtom.online.sdk.samples.cases.map.markers.advanced;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.Marker;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.map.rx.RxTomtomMap;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.map.markers.MarkerDrawer;
import com.tomtom.online.sdk.samples.cases.map.markers.balloons.TypedBalloonViewAdapter;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class AdvancedMarkersPresenter extends BaseFunctionalExamplePresenter {

    private static final String TOAST_MESSAGE = "%s : %f, %f";
    private static final int TOAST_DURATION_MS = 2000; //milliseconds
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RxTomtomMap rxTomtomMap;
    private MarkerDrawer markerDrawer;

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        tomtomMap.getMarkerSettings().setMarkerBalloonViewAdapter(new TypedBalloonViewAdapter());

        //tag::doc_register_draggable_marker_listener[]
        tomtomMap.getMarkerSettings().addOnMarkerDragListener(onMarkerDragListener);
        //end::doc_register_draggable_marker_listener[]

        if (!view.isMapRestored()) {
            centerMapOnLocation();
        }

        markerDrawer = new MarkerDrawer(view.getContext(), tomtomMap);

        //tag::doc_register_rx_disposable[]
        rxTomtomMap = new RxTomtomMap(tomtomMap);
        Disposable longClickDisposable = rxTomtomMap
                .getOnMapLongClickObservable()
                .subscribe(latLng -> Timber.i("long click on " + latLng));
        Disposable onClickDisposable = rxTomtomMap
                .getOnMapClickObservable()
                .subscribe(latLng -> Timber.i("click on " + latLng));
        Disposable markerClickDisposable = rxTomtomMap
                .getOnMarkerClickObservable()
                .subscribe(marker -> Timber.i("marker clicked " + marker.getId()));
        //end::doc_register_rx_disposable[]
        compositeDisposable.add(longClickDisposable);
        compositeDisposable.add(onClickDisposable);
        compositeDisposable.add(markerClickDisposable);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new AdvancedMarkersFunctionalExample();
    }

    @Override
    public void cleanup() {
        if (!isMapReady()) {
            return;
        }
        tomtomMap.removeMarkers();
        compositeDisposable.dispose();
    }

    @Override
    public void onPause() {
        if (!isMapReady()) {
            return;
        }
        tomtomMap.removeOnMarkerDragListeners();
        compositeDisposable.dispose();
    }

    public void createAnimatedMarkers() {
        resetMap();

        markerDrawer.createRandomAnimatedMarkers(5);
    }

    public void createDraggableMarkers() {
        resetMap();

        markerDrawer.createRandomDraggableMarkers(5);
    }

    //tag::doc_create_draggable_marker_listener[]
    TomtomMapCallback.OnMarkerDragListener onMarkerDragListener = new TomtomMapCallback.OnMarkerDragListener() {
        @Override
        public void onStartDragging(@NonNull Marker marker) {
            Timber.d("onMarkerDragStart(): " + marker.toString());
            displayMessage(R.string.marker_dragging_start_message, marker.getPosition().getLatitude(), marker.getPosition().getLongitude());
        }

        @Override
        public void onStopDragging(@NonNull Marker marker) {
            Timber.d("onMarkerDragEnd(): " + marker.toString());
            displayMessage(R.string.marker_dragging_end_message, marker.getPosition().getLatitude(), marker.getPosition().getLongitude());
        }

        @Override
        public void onDragging(@NonNull Marker marker) {
            Timber.d("onMarkerDragging(): " + marker.toString());
        }
    };
    //end::doc_create_draggable_marker_listener[]

    private void displayMessage(@StringRes int titleId, double lat, double lon) {
        String title = view.getContext().getString(titleId);
        Timber.d("Functional Example on %s", title);
        String message = String.format(java.util.Locale.getDefault(),
                TOAST_MESSAGE,
                title,
                lat,
                lon);

        view.showInfoText(message, TOAST_DURATION_MS);
    }

    private boolean isMapReady() {
        return tomtomMap != null;
    }

    private void resetMap(){
        tomtomMap.removeMarkers();
        centerMapOnLocation();
    }

    private void centerMapOnLocation() {
        tomtomMap.centerOn(
                Locations.AMSTERDAM_LOCATION.getLatitude(),
                Locations.AMSTERDAM_LOCATION.getLongitude(),
                DEFAULT_ZOOM_LEVEL,
                MapConstants.ORIENTATION_NORTH
        );

    }

}
