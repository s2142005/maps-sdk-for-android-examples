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
package com.tomtom.online.sdk.samples.cases.search;

import android.content.Context;

import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;
import com.tomtom.online.sdk.search.api.SearchError;
import com.tomtom.online.sdk.search.api.revgeo.RevGeoSearchResultListener;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse;

public class ReverseGeocodingPresenter extends BaseFunctionalExamplePresenter implements RevGeoSearchResultListener {

    private ReverseGeoMarker revGeoMarker;
    private ReverseGeocodingRequester revGeoSearch;
    protected Context context;

    private final TomtomMapCallback.OnMapLongClickListener onMapLongClickListener =
            latLng -> {
                revGeoMarker = new ReverseGeoMarker(getContext(), tomtomMap);
                tomtomMap.removeMarkers();
                revGeoMarker.createMarker(latLng);
                revGeoSearch = new ReverseGeocodingRequester(context, this);
                revGeoSearch.performReverseGeocode(latLng);
            };

    private final TomtomMapCallback.OnMarkerClickListener onMarkerClickListener = marker -> tomtomMap.centerOn(marker.getPosition());

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        context = view.getContext();
        setupTomtomMap();
        centerOnAmsterdam();
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new ReverseGeocodingFunctionalExample();
    }

    @Override
    public void cleanup() {
        tomtomMap.getMarkerSettings().removeMarkers();
        tomtomMap.removeOnMarkerClickListener(onMarkerClickListener);
        tomtomMap.removeOnMapLongClickListener(onMapLongClickListener);
    }

    public void centerOnAmsterdam() {
        tomtomMap.centerOn(
                Locations.AMSTERDAM_LOCATION.getLatitude(),
                Locations.AMSTERDAM_LOCATION.getLongitude(),
                DEFAULT_ZOOM_LEVEL,
                MapConstants.ORIENTATION_NORTH
        );
    }

    protected void setupTomtomMap() {
        tomtomMap.addOnMarkerClickListener(onMarkerClickListener);
        tomtomMap.addOnMapLongClickListener(onMapLongClickListener);
    }

    @Override
    public void onSearchResult(ReverseGeocoderSearchResponse response) {
        String address = new AddressResponseFormatter(context).format(response);
        revGeoMarker.updateMarkerBalloon(address);
    }

    @Override
    public void onSearchError(SearchError error) {
        revGeoMarker.updateMarkerBalloon(context.getString(R.string.reverse_geocoding_error));
    }
}
