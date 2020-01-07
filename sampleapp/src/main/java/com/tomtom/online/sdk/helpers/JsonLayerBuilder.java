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

import com.tomtom.online.sdk.map.style.layers.Visibility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

class JsonLayerBuilder {

    enum Type {
        Raster, Line
    }

    private Type type;
    private String id;
    private String source;
    private Visibility visibility = Visibility.NONE;
    private Integer lineColor;
    private Integer lineWidth;

    JsonLayerBuilder(String layerId, String sourceId) {
        this.id = layerId;
        this.source = sourceId;
    }

    JsonLayerBuilder withType(Type type) {
        this.type = type;
        return this;
    }

    JsonLayerBuilder withVisibility(Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    JsonLayerBuilder withLineColor(int color) {
        this.lineColor = color;
        return this;
    }

    JsonLayerBuilder withLineWidth(Integer width) {
        this.lineWidth = width;
        return this;
    }

    String build() {
        JSONObject layerJson = new JSONObject();
        try {
            // Mandatory
            layerJson.put("id", id);
            layerJson.put("source", source);
            layerJson.put("layout",
                    new JSONObject().put("visibility", visibility.name().toLowerCase()));
            layerJson.put("type", type.name().toLowerCase());

            if (lineWidth != null || lineColor != null) {
                layerJson.put("paint", new JSONObject()
                        .put("line-color", parseLineColor())
                        .put("line-width", lineWidth));
            }

            return layerJson.toString();
        } catch (JSONException exception) {
            return "{}";
        }
    }

    private String parseLineColor() {
        return String.format(Locale.getDefault(), "rgba(%d, %d, %d, %d)",
                (lineColor >> 16) & 0xff,
                (lineColor >> 8) & 0xff,
                (lineColor) & 0xff,
                lineColor >>> 24);
    }
}

