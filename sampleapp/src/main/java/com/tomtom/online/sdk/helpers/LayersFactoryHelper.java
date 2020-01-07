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
package com.tomtom.online.sdk.helpers;

import android.graphics.Color;

import com.tomtom.online.sdk.map.style.layers.Visibility;

/**
 * Provides the tidy way to create layer in the json string format
 */
public class LayersFactoryHelper {

    public static final int DEFAULT_LINE_WIDTH = 6;
    public static final int DEFAULT_LINE_COLOR = Color.argb(1, 26, 203, 207);

    /**
     * Creates visible image layer in json string format.
     *
     * @param layerId - wanted id of the layer
     * @param sourceId - related source id for this layer
     *
     * @return created image layer in json string format
     */
    public String createImageLayer(String layerId, String sourceId) {
        return new JsonLayerBuilder(layerId, sourceId)
                .withType(JsonLayerBuilder.Type.Raster)
                .withVisibility(Visibility.VISIBLE)
                .build();
    }

    /**
     * Creates visible geojson line layer in json string format.
     *
     * @param layerId - wanted id of the layer
     * @param sourceId - related source id for this layer
     *
     * @return created geojson line layer in json string format
     */
    public String createGeoJsonLineLayer(String layerId, String sourceId) {
        return createGeoJsonLineLayer(layerId, sourceId, DEFAULT_LINE_COLOR, DEFAULT_LINE_WIDTH);
    }

    /**
     * Creates visible geojson line layer in json string format.
     *
     * @param layerId - wanted id of the layer
     * @param sourceId - related source id for this layer
     * @param lineColor - the color of the line
     * @param lineWidth - the width of the line
     *
     * @return created geojson line layer in json string format
     */
    public String createGeoJsonLineLayer(String layerId, String sourceId, int lineColor, int lineWidth) {
        return new JsonLayerBuilder(layerId, sourceId)
                .withType(JsonLayerBuilder.Type.Line)
                .withVisibility(Visibility.VISIBLE)
                .withLineWidth(lineWidth)
                .withLineColor(lineColor)
                .build();
    }
}
