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
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.clustering

import android.content.Context
import com.tomtom.online.sdk.common.util.AssetUtils
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.map.style.images.ImageFactory
import com.tomtom.online.sdk.map.style.layers.LayerFactory
import com.tomtom.online.sdk.map.style.sources.SourceFactory
import com.tomtom.sdk.examples.R

class ImageClusteringDrawer(private val context: Context, private val tomtomMap: TomtomMap) {

    fun addLayersToStyle() {
        //tag::adding_image_layer_to_style[]
        val imageLayerJson = AssetUtils.getAssetFile(context, LAYER_CLUSTER_IMAGE_PATH)
        val imageLayer = LayerFactory.createLayer(imageLayerJson)
        tomtomMap.styleSettings.addLayer(imageLayer)
        //end::adding_image_layer_to_style[]

        val clusteredImageLayerJson = AssetUtils.getAssetFile(context, LAYER_CLUSTERED_PATH)
        val clusteredImageLayer = LayerFactory.createLayer(clusteredImageLayerJson)
        tomtomMap.styleSettings.addLayer(clusteredImageLayer)

        val countLayerJson = AssetUtils.getAssetFile(context, LAYER_CLUSTER_COUNT_PATH)
        val countLayer = LayerFactory.createLayer(countLayerJson)
        tomtomMap.styleSettings.addLayer(countLayer)
    }

    fun addSourceToStyle() {
        //tag::adding_map_source_to_style[]
        val imageSourceJson = AssetUtils.getAssetFile(context, SOURCE_PATH)
        val source = SourceFactory.createSource(imageSourceJson)
        tomtomMap.styleSettings.addSource(source)
        //end::adding_map_source_to_style[]
    }

    fun addImages() {
        IMAGE_PAIRS.forEach { pair ->
            val imageId = pair.first
            val imageResId = pair.second
            //tag::add_image_to_style[]
            val image = ImageFactory.createImage(imageId, context.resources.getDrawable(imageResId))
            tomtomMap.styleSettings.addImage(image)
            //end::add_image_to_style[]
        }
    }

    fun removeImages() {
        IMAGE_PAIRS.forEach { pair ->
            tomtomMap.styleSettings.removeImage(pair.first)
        }
    }

    fun removeLayers() {
        tomtomMap.styleSettings.removeLayer(LAYER_CLUSTERED_IMAGES_ID)
        tomtomMap.styleSettings.removeLayer(LAYER_CLUSTER_IMAGE_ID)
        tomtomMap.styleSettings.removeLayer(LAYER_CLUSTER_COUNT_ID)
    }

    fun removeSource() {
        tomtomMap.styleSettings.removeSource(SOURCE_ID)
    }

    companion object {
        private const val SOURCE_ID = "IC-clustering-source"
        private const val SOURCE_PATH = "layers/image_clustering_source.json"
        private const val LAYER_CLUSTERED_IMAGES_ID = "IC-layer-clustered"
        private const val LAYER_CLUSTER_COUNT_ID = "IC-layer-symbol-count"
        private const val LAYER_CLUSTER_IMAGE_ID = "IC-layer-cluster-image"
        private const val LAYER_CLUSTERED_PATH = "layers/layer_image.json"
        private const val LAYER_CLUSTER_IMAGE_PATH = "layers/layer_clustered_image.json"
        private const val LAYER_CLUSTER_COUNT_PATH = "layers/layer_clustered_count.json"
        private val IMAGE_PAIRS = listOf(
            Pair("ic_amsterdam_rokin", R.drawable.ic_amsterdam_rokin),
            Pair("ic_amsterdam_night", R.drawable.ic_amsterdam_night),
            Pair("ic_cluster", R.drawable.ic_cluster),
            Pair("ic_gas_station", R.drawable.ic_gas_station),
            Pair("ic_jet_airplane", R.drawable.ic_jet_airplane),
            Pair("ic_skyscrapers", R.drawable.ic_skyscrapers),
            Pair("ic_road_with_traffic", R.drawable.ic_road_with_traffic),
            Pair("ic_traffic_light", R.drawable.ic_traffic_light),
            Pair("ic_tunnel", R.drawable.ic_tunnel)
        )
    }
}