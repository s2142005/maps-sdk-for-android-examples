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

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.Marker;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.R;

public class ReverseGeoMarker {

    private final Context context;
    private final TomtomMap tomtomMap;
    private Marker marker;
    private final SimpleMarkerBalloon balloon;

    public ReverseGeoMarker(Context context, TomtomMap tomtomMap) {
        this.context = context;
        this.tomtomMap = tomtomMap;
        balloon = createSimpleMarkerBallon();
    }

    @NonNull
    @VisibleForTesting
    SimpleMarkerBalloon createSimpleMarkerBallon() {
        return new SimpleMarkerBalloon("Welcome to TomTom");
    }

    public void createMarker(LatLng latLng) {
        MarkerBuilder markerBuilder = new MarkerBuilder(latLng).markerBalloon(balloon);
        balloon.setText(context.getString(R.string.reverse_geocoding_fetching));
        marker = tomtomMap.addMarker(markerBuilder);
    }

    public void updateMarkerBalloon(String text) {
        balloon.setText(text);
        marker.select();
    }
}
