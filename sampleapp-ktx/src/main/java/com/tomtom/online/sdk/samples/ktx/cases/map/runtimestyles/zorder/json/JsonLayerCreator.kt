/*
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.json

import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.util.*

object JsonLayerCreator {

    fun fromDescriptor(layerDescriptor: JsonLayerDescriptor): String {
        return try {
            val layerJson = JSONObject()
            layerJson.put("id", layerDescriptor.layerId)
            layerJson.put("source", layerDescriptor.sourceId)
            layerJson.put("layout", JSONObject().put("visibility", layerDescriptor.visibility.name.toLowerCase()))
            layerJson.put("type", layerDescriptor.type.name.toLowerCase())

            if (layerDescriptor.lineWidth != null && layerDescriptor.lineColor != null) {
                layerJson.put("paint", JSONObject()
                    .put("line-color", parseLineColor(layerDescriptor.lineColor))
                    .put("line-width", layerDescriptor.lineWidth))
            }
            layerJson.toString()
        } catch (exception: JSONException) {
            Timber.e("Failed to create JSON from descriptor, returning empty JSON")
            "{}"
        }
    }

    private fun parseLineColor(lineColor: Int): String {
        return String.format(Locale.getDefault(), "rgba(%d, %d, %d, %d)",
            lineColor shr 16 and 0xff,
            lineColor shr 8 and 0xff,
            lineColor and 0xff,
            lineColor.ushr(24))
    }
}