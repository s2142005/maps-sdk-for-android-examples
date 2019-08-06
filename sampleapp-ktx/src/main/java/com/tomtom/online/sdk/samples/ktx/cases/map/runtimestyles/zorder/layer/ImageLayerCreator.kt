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