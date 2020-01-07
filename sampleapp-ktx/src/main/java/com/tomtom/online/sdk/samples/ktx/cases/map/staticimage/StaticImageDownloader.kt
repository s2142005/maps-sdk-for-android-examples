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

package com.tomtom.online.sdk.samples.ktx.cases.map.staticimage

import android.widget.ImageView
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.online.sdk.staticimage.StaticImage

class StaticImageDownloader {

    fun loadImage(mapsApiKey: String,
                  targetImageView: ImageView,
                  style: String = StaticImage.STYLE_MAIN,
                  layer: String = StaticImage.LAYER_BASIC,
                  zoomLevel: Int = DEFAULT_ZOOM_LEVEL_FOR_MAP) {

        //tag::doc_static_image[]
        StaticImage.builder(mapsApiKey)
                .center(Locations.AMSTERDAM)
                .zoom(zoomLevel)
                .layer(layer)
                .style(style)
                .mapSize(DEFAULT_DIMENSION_SIZE, DEFAULT_DIMENSION_SIZE)
                .png()
                .build()
                .downloadInto(targetImageView)
        //end::doc_static_image[]
    }

    companion object {
        private const val DEFAULT_ZOOM_LEVEL_FOR_MAP = 12
        private const val DEFAULT_DIMENSION_SIZE = 512
    }

}