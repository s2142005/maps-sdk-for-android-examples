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

import com.tomtom.online.sdk.map.BaseMarkerBalloon;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.samples.cases.route.matrix.data.AmsterdamPoi;

public class MatrixRouteMarkerDrawer {

    private Context context;

    MatrixRouteMarkerDrawer(Context context) {
        this.context = context;
    }

    MarkerBuilder addMarkerForPoi(AmsterdamPoi poi) {
        final String text = AmsterdamPoi.getNameWithPrefix(context, poi.getLocation());
        final BaseMarkerBalloon balloon = new SimpleMarkerBalloon(text);
        final MarkerBuilder markerBuilder = new MarkerBuilder(poi.getLocation());

        markerBuilder.markerBalloon(balloon);

        Icon icon = createMarkerIcon(poi);

        if (icon != null) {
            markerBuilder.icon(icon);
        }
        return markerBuilder;
    }

    private Icon createMarkerIcon(AmsterdamPoi poi) {
        return Icon.Factory.fromResources(context, poi.getIconResId());
    }
}
