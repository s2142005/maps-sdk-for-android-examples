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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.layer

import android.content.Context
import androidx.annotation.DrawableRes
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.map.style.layers.LayerFactory
import com.tomtom.online.sdk.map.style.sources.SourceFactory
import com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.DynamicLayerType
import com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.json.JsonLayerCreator
import com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.zorder.json.JsonLayerDescriptor
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R

class ImageLayerCreator(private val context: Context, private val tomtomMap: TomtomMap) {

    fun addImages(layerId: String, sourceId: String) {
        addImage(layerId, sourceId, "1", Locations.SAN_JOSE_IMG1, R.drawable.img1)
        addImage(layerId, sourceId, "2", Locations.SAN_JOSE_IMG2, R.drawable.img2)
        addImage(layerId, sourceId, "3", Locations.SAN_JOSE_IMG3, R.drawable.img3)
    }

    private fun addImage(layerId: String, sourceId: String, imageId: String, coordinate: LatLng, @DrawableRes imageResId: Int) {
        val imageLayerId = layerId.plus(imageId)
        val imageSourceId = sourceId.plus(imageId)

        addImageSource(imageSourceId, coordinate, imageResId)
        addImageLayer(imageLayerId, imageSourceId)
    }

    private fun addImageSource(sourceId: String, coordinate: LatLng, @DrawableRes imageResId: Int) {
        val source = SourceFactory.createImageSource(sourceId, quadWithDelta(coordinate, 0.25))
        source.setImage(context.resources.getDrawable(imageResId))

        tomtomMap.styleSettings.addSource(source)
    }

    private fun addImageLayer(layerId: String, sourceId: String) {
        val imageLayerDescriptor = JsonLayerDescriptor(layerId = layerId, sourceId = sourceId, type = DynamicLayerType.Raster)
        val jsonLayer = JsonLayerCreator.fromDescriptor(imageLayerDescriptor)

        val layer = LayerFactory.createLayer(jsonLayer)
        tomtomMap.styleSettings.addLayer(layer)
    }

    private fun quadWithDelta(coordinate: LatLng, delta: Double): List<LatLng> {
        return listOf(
            LatLng(coordinate.latitude + delta / 2, coordinate.longitude - delta),
            LatLng(coordinate.latitude + delta / 2, coordinate.longitude + delta),
            LatLng(coordinate.latitude - delta / 2, coordinate.longitude + delta),
            LatLng(coordinate.latitude - delta / 2, coordinate.longitude - delta)
        )
    }

}