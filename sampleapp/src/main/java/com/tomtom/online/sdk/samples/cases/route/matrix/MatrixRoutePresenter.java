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
package com.tomtom.online.sdk.samples.cases.route.matrix;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.common.util.Contextable;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.Polyline;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingQuery;
import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingQueryBuilder;
import com.tomtom.online.sdk.routing.data.matrix.MatrixRoutingResponse;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.route.matrix.data.AmsterdamPoi;
import com.tomtom.online.sdk.samples.utils.Locations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.tomtom.online.sdk.map.MapConstants.ORIENTATION_SOUTH;

public class MatrixRoutePresenter implements LifecycleObserver, MatrixResponseDisplayCallback {

    private final static double DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 12.0;
    private final static int DEFAULT_MAP_PADDING = 0;

    private final MatrixRouteFunctionalExample model;
    private MatrixRoutesTableViewModel routesTableViewModel;
    private Context context;
    private final MatrixRouteView view;

    private MatrixRouteRequester routeRequester;
    private MatrixResponseDisplay responseHandler;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    MatrixRoutePresenter(final MatrixRouteView view) {

        if (view == null) {
            throw new IllegalArgumentException("Fragment can't be null.");
        }

        this.view = view;
        model = new MatrixRouteFunctionalExample();
        context = view.getContext();
        routeRequester = new MatrixRouteRequester(context);
        responseHandler = new MatrixResponseDisplay(context, this);

        routesTableViewModel = ViewModelProviders.of(view.getFragment()).get(MatrixRoutesTableViewModel.class);
        routesTableViewModel.getLastMatrixRoutingResponse().observe(view.getFragment(), matrixRoutingResponse -> view.runOnTomtomMap(tomtomMap
                -> proceedWithResponse(matrixRoutingResponse)));
    }

    public FunctionalExampleModel getModel() {
        return model;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        view.runOnTomtomMap(tomtomMap -> tomtomMap.centerOn(
                Locations.AMSTERDAM_CENTER_LOCATION.getLatitude(),
                Locations.AMSTERDAM_CENTER_LOCATION.getLongitude(),
                DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE, ORIENTATION_SOUTH));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        compositeDisposable.clear();
    }

    void planMatrixOfRoutesFromAmsterdamCenterToRestaurants() {
        cleanup();

        List<LatLng> origins = provideLocationsListForPois(AmsterdamPoi.CITY_AMSTERDAM);
        List<LatLng> destinations = provideLocationsListForPois(
                AmsterdamPoi.RESTAURANT_BRIDGES,
                AmsterdamPoi.RESTAURANT_GREETJE,
                AmsterdamPoi.RESTAURANT_LA_RIVE,
                AmsterdamPoi.RESTAURANT_WAGAMAMA,
                AmsterdamPoi.RESTAURANT_ENVY);

        MatrixRoutingQuery query = MatrixRoutingQueryBuilder.create(origins, destinations).build();
        changeTableHeadersToOneToMany();
        proceedWithQuery(query);
    }

    private void changeTableHeadersToOneToMany() {
        view.changeTableHeadersToOneToMany();
    }

    void planMatrixOfRoutesFromPassengersToTaxi() {
        cleanup();

        List<LatLng> origins = provideLocationsListForPois(AmsterdamPoi.PASSENGER_ONE, AmsterdamPoi.PASSENGER_TWO);
        List<LatLng> destinations = provideLocationsListForPois(AmsterdamPoi.TAXI_ONE, AmsterdamPoi.TAXI_TWO);

        //tag::doc_matrix_query[]
        MatrixRoutingQuery query = MatrixRoutingQueryBuilder.create(origins, destinations).build();
        //end::doc_matrix_query[]

        changeTableHeadersToManyToMany();
        proceedWithQuery(query);
    }

    private void changeTableHeadersToManyToMany() {
        view.changeTableHeadersToManyToMany();
    }

    private void proceedWithQuery(MatrixRoutingQuery query) {
        Disposable subscribe = routeRequester.performMatrixRouting(query, routingResponseConsumer, onError);
        compositeDisposable.add(subscribe);
    }

    private void proceedWithResponse(MatrixRoutingResponse matrixRoutingResponse) {
        view.updateMatrixRoutesList(matrixRoutingResponse);
        responseHandler.displayPoiOnMap(matrixRoutingResponse);
        setupMapAfterResponse();
    }

    private void setupMapAfterResponse() {
        view.runOnTomtomMap(tomtomMap -> {
            confMapPadding(tomtomMap);
            tomtomMap.getMarkerSettings().zoomToAllMarkers();
        });
    }

    @NonNull
    private List<LatLng> provideLocationsListForPois(AmsterdamPoi... pois) {
        final List<LatLng> locations = new ArrayList<>();

        for (AmsterdamPoi poi : pois) {
            locations.add(poi.getLocation());
        }

        return locations;
    }

    void cleanup() {
        view.runOnTomtomMap(tomtomMap -> {
            tomtomMap.getOverlaySettings().removeOverlays();
            tomtomMap.removeMarkers();
            tomtomMap.setPadding(DEFAULT_MAP_PADDING, DEFAULT_MAP_PADDING, DEFAULT_MAP_PADDING, DEFAULT_MAP_PADDING);
        });
        view.hideMatrixRoutesTable();
    }

    private Consumer<MatrixRoutingResponse> routingResponseConsumer = matrixRoutingResponse -> routesTableViewModel.saveMatrixRoutingResponse(matrixRoutingResponse);

    private Consumer<Throwable> onError = throwable -> {
        Timber.e(throwable, throwable.getMessage());
        Toast.makeText(context, R.string.matrix_routing_error_message, Toast.LENGTH_LONG).show();
    };

    private void confMapPadding(TomtomMap tomtomMap) {
        int offsetTop = context.getResources().getDimensionPixelSize(R.dimen.matrix_routing_box_height);
        int offsetBottom = context.getResources().getDimensionPixelSize(R.dimen.control_top_panel_height);
        int offsetDefault = context.getResources().getDimensionPixelSize(R.dimen.offset_big);

        tomtomMap.setPadding(offsetTop, offsetDefault, offsetBottom, offsetDefault);
    }

    @Override
    public void onPolylineForPoiCreated(Polyline polyline) {
        view.runOnTomtomMap(tomtomMap -> tomtomMap.getOverlaySettings().addOverlay(polyline));
    }

    @Override
    public void onMarkerForPoiCreated(MarkerBuilder markerBuilder) {
        view.runOnTomtomMap(tomtomMap -> tomtomMap.getMarkerSettings().addMarker(markerBuilder));
    }

    interface MatrixRouteView extends Contextable {

        void updateMatrixRoutesList(MatrixRoutingResponse matrixRoutingResponse);

        void changeTableHeadersToOneToMany();

        void changeTableHeadersToManyToMany();

        void hideMatrixRoutesTable();

        Fragment getFragment();

        void runOnTomtomMap(final OnMapReadyCallback callback);
    }
}
