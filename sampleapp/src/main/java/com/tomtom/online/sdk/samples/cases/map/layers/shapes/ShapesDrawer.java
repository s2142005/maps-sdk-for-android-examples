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
package com.tomtom.online.sdk.samples.cases.map.layers.shapes;

import android.graphics.Color;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.Circle;
import com.tomtom.online.sdk.map.CircleBuilder;
import com.tomtom.online.sdk.map.Polygon;
import com.tomtom.online.sdk.map.PolygonBuilder;
import com.tomtom.online.sdk.map.Polyline;
import com.tomtom.online.sdk.map.PolylineBuilder;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.utils.Locations;

import java.util.List;
import java.util.Random;

class ShapesDrawer {

    private TomtomMap tomtomMap;

    ShapesDrawer(TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
    }

    void drawShapePolygon(List<LatLng> coordinates) {
        int color = randomColor();

        //tag::doc_shape_polygon[]
        Polygon polygon = PolygonBuilder.create()
                .coordinates(coordinates)
                .color(color)
                .build();

        tomtomMap.getOverlaySettings().addOverlay(polygon);
        //end::doc_shape_polygon[]
    }

    void drawShapePolyline(List<LatLng> coordinates) {
        int color = randomColor();

        //tag::doc_shape_polyline[]
        Polyline polyline = PolylineBuilder.create()
                .coordinates(coordinates)
                .color(color)
                .build();

        tomtomMap.getOverlaySettings().addOverlay(polyline);
        //end::doc_shape_polyline[]
    }

    void drawShapeCircle() {
        int color = randomColor();
        LatLng position = Locations.AMSTERDAM_LOCATION;
        double radiusM = 5000.0; //meter

        //tag::doc_shape_circle[]
        Circle circle = CircleBuilder.create()
                .fill(true)
                .radius(radiusM)
                .position(position)
                .color(color)
                .build();

        tomtomMap.getOverlaySettings().addOverlay(circle);
        //end::doc_shape_circle[]
    }

    private int randomColor() {
        Random random = new Random();
        int alpha = random.nextInt(255), red = random.nextInt(255), green = random.nextInt(255), blue = random.nextInt(255);
        return Color.argb(alpha, red, green, blue);
    }
}
