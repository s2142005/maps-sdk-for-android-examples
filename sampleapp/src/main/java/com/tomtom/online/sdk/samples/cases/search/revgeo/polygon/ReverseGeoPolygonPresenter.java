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
package com.tomtom.online.sdk.samples.cases.search.revgeo.polygon;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.tomtom.core.maps.gestures.GesturesDetectionSettings;
import com.tomtom.core.maps.gestures.GesturesDetectionSettingsBuilder;
import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.search.ReverseGeoMarker;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQuery;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.schedulers.Schedulers;

public class ReverseGeoPolygonPresenter extends BaseFunctionalExamplePresenter implements RevGeoUiCallback {

    static final String ACTIVE_ENTITY_TYPE_COUNTRY = "Country";
    static final String ACTIVE_ENTITY_TYPE_CITY = "Municipality";
    static final int GEOMETRY_ZOOM_LEVEL_FOR_COUNTRY = 6;
    static final int GEOMETRY_ZOOM_LEVEL_FOR_MUNICIPALITY = 14;

    private static final int ZOOM_LEVEL_FOR_EXAMPLE = 3;

    private ReverseGeoMarker revGeoMarker;
    private Context context;
    private SearchApi searchApi;
    private String activeEntityType = ACTIVE_ENTITY_TYPE_COUNTRY;
    private SerialDisposable serialDisposable = new SerialDisposable();
    private RevGeoWithAdpRequester revGeoWithAdpRequester;

    private TomtomMapCallback.OnMapLongClickListener onMapLongClickListener;
    private TomtomMapCallback.OnMarkerClickListener onMarkerClickListener;
    private ProgressDisplayable progressDisplayable;

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        context = view.getContext();
        onMapLongClickListener = initOnMapLongClickListener();
        onMarkerClickListener = initOnMarkerClickListener();

        createSearchAPI();
        setupTomtomMap();
        if (!view.isMapRestored()) {
            centerOnDefaultLocation();
        }
        revGeoMarker = new ReverseGeoMarker(context, tomtomMap);
        if (view instanceof ProgressDisplayable) {
            progressDisplayable = (ProgressDisplayable) view;
        }
    }

    @VisibleForTesting
    ProgressDisplayable getProgressDisplayable() {
        return progressDisplayable;
    }

    @NonNull
    private TomtomMapCallback.OnMarkerClickListener initOnMarkerClickListener() {
        return marker -> tomtomMap.centerOn(marker.getPosition());
    }

    @NonNull
    private TomtomMapCallback.OnMapLongClickListener initOnMapLongClickListener() {
        return latLng -> {
            clearMap();
            getRevGeoMarker().createMarker(latLng);
            reverseGeocode(latLng);
        };
    }

    @VisibleForTesting
    ReverseGeoMarker getRevGeoMarker() {
        return revGeoMarker;
    }

    @VisibleForTesting
    TomtomMapCallback.OnMapLongClickListener getOnMapLongClickListener() {
        return onMapLongClickListener;
    }

    @VisibleForTesting
    TomtomMapCallback.OnMarkerClickListener getOnMarkerClickListener() {
        return onMarkerClickListener;
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new ReverseGeoPolygonFunctionalExample();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!serialDisposable.isDisposed()) {
            serialDisposable.dispose();
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        clearMap();
        tomtomMap.removeOnMarkerClickListener(onMarkerClickListener);
        tomtomMap.removeOnMapLongClickListener(onMapLongClickListener);
        tomtomMap.updateGesturesDetectionSettings(GesturesDetectionSettings.createDefault());
    }

    public void centerOnDefaultLocation() {
        tomtomMap.centerOn(
                Locations.AMSTERDAM_LOCATION.getLatitude(),
                Locations.AMSTERDAM_LOCATION.getLongitude(),
                ZOOM_LEVEL_FOR_EXAMPLE,
                MapConstants.ORIENTATION_NORTH
        );
    }

    protected void setupTomtomMap() {
        tomtomMap.addOnMarkerClickListener(onMarkerClickListener);
        tomtomMap.addOnMapLongClickListener(onMapLongClickListener);
        GesturesDetectionSettings gestureSettings = GesturesDetectionSettingsBuilder.create()
                .enableEventsIntercepting(true)
                .build();
        tomtomMap.updateGesturesDetectionSettings(gestureSettings);
    }

    protected void createSearchAPI() {
        searchApi = getSearchApi();
        revGeoWithAdpRequester = getRevGeoWithAdpRequester();
    }

    @NonNull
    @VisibleForTesting
    protected RevGeoWithAdpRequester getRevGeoWithAdpRequester() {
        return new RevGeoWithAdpRequester(searchApi);
    }

    @NonNull
    @VisibleForTesting
    protected SearchApi getSearchApi() {
        return OnlineSearchApi.create(context);
    }

    protected ReverseGeocoderSearchQuery createReverseGeocoderQuery(double latitude, double longitude) {
        return ReverseGeocoderSearchQueryBuilder
                .create(latitude, longitude).withEntityType(activeEntityType).build();
    }

    public void setActiveTypeToCountry() {
        clearMap();
        setActiveEntityType(ACTIVE_ENTITY_TYPE_COUNTRY);
        revGeoWithAdpRequester.setGeometriesZoom(GEOMETRY_ZOOM_LEVEL_FOR_COUNTRY);
    }

    public void setActiveTypeToMunicipality() {
        clearMap();
        setActiveEntityType(ACTIVE_ENTITY_TYPE_CITY);
        revGeoWithAdpRequester.setGeometriesZoom(GEOMETRY_ZOOM_LEVEL_FOR_MUNICIPALITY);
    }

    @VisibleForTesting
    protected void clearMap() {
        tomtomMap.getOverlaySettings().removeOverlays();
        tomtomMap.getMarkerSettings().removeMarkers();
    }

    public String getActiveEntityType() {
        return activeEntityType;
    }

    public void setActiveEntityType(String activeEntityType) {
        this.activeEntityType = activeEntityType;
    }

    @VisibleForTesting
    protected void reverseGeocode(LatLng latLng) {
        getProgressDisplayable().showInProgressDialog();

        //tag::doc_reverse_geocoding_with_polygon_requester[]
        ReverseGeocoderSearchQuery reverseGeocoderQuery =
                createReverseGeocoderQuery(latLng.getLatitude(), latLng.getLongitude());

        Observable<RevGeoWithAdpResponse> revGeoWithAdpResponseObservable =
                revGeoWithAdpRequester.rawReverseGeocoding(reverseGeocoderQuery)
                        .subscribeOn(getWorkerScheduler())
                        .filter(revGeoWithAdpResponse -> !revGeoWithAdpResponse.getRevGeoResponse().getAddresses().isEmpty())
                        .doOnNext(this::updateUiWithResponse);
        //end::doc_reverse_geocoding_with_polygon_requester[]

        serialDisposable.set(
                //tag::doc_reverse_geocoding_to_polygon_observable[]
                revGeoWithAdpRequester.toPolygonObservable(revGeoWithAdpResponseObservable)
                        .observeOn(getObserverScheduler())
                        .subscribe(polygon -> {
                            tomtomMap.getOverlaySettings().addOverlay(polygon);
                            getProgressDisplayable().hideInProgressDialog();
                        }, error -> getProgressDisplayable().hideInProgressDialog())
                //end::doc_reverse_geocoding_to_polygon_observable[]
        );
    }

    @VisibleForTesting
    Scheduler getWorkerScheduler() {
        return Schedulers.io();
    }

    @VisibleForTesting
    Scheduler getObserverScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public void updateUiWithResponse(RevGeoWithAdpResponse revGeoWithAdpResponse) {
        getRevGeoMarker().updateMarkerBalloon(
                FuncUtils.first(revGeoWithAdpResponse.getRevGeoResponse().getAddresses()).get()
                        .getAddress().getFreeformAddress());
    }
}
