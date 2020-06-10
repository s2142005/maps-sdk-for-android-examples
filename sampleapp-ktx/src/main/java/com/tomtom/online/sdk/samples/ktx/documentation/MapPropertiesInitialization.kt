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
package com.tomtom.online.sdk.samples.ktx.documentation

import android.content.Context
import android.graphics.Color
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*

@Suppress("unused")
internal class MapPropertiesInitialization(val context: Context) {

    @Suppress("unused")
    private fun initMapWithProperties() {
        //tag::doc_map_properties_keys[]
        val keysMap = mapOf(
            ApiKeyType.MAPS_API_KEY to "maps-key",
            ApiKeyType.TRAFFIC_API_KEY to "traffic-key"
        )
        MapProperties.Builder()
            .keys(keysMap)
            .build()
        //end::doc_map_properties_keys[]
        //tag::doc_map_properties_camera_position[]
        val cameraPosition = CameraPosition.builder()
            .focusPosition(LatLng(12.34, 23.45))
            .zoom(10.0)
            .bearing(24.0)
            .pitch(45.2)
            .build()
        MapProperties.Builder()
            .cameraPosition(cameraPosition)
            .build()
        //end::doc_map_properties_camera_position[]
        //tag::doc_map_properties_padding[]
        MapProperties.Builder()
            .padding(MapPadding(50.0, 40.0, 100.0, 80.0))
            .build()
        //end::doc_map_properties_padding[]
        //tag::doc_map_properties_camera_area[]
        val cameraFocusArea = CameraFocusArea.Builder(
            BoundingBox(LatLng(52.407943, 4.808601), LatLng(52.323363, 4.969053))
        )
            .bearing(24.0)
            .pitch(45.2)
            .build()
        MapProperties.Builder()
            .cameraFocusArea(cameraFocusArea)
            .build()
        //end::doc_map_properties_camera_area[]
        //tag::doc_map_properties_style[]
        MapProperties.Builder()
            .customStyleUri("asset://styles/style.json")
            .build()
        //end::doc_map_properties_style[]
        //tag::doc_map_properties_background[]
        MapProperties.Builder()
            .backgroundColor(Color.BLUE)
            .build()
        //end::doc_map_properties_background[]
        //tag::doc_map_properties_hosted_styles[]
        MapProperties.Builder()
            .mapStyleSource(MapStyleSource.STYLE_MERGER)
            .build()
        //end::doc_map_properties_hosted_styles[]
        val mapProperties = MapProperties.Builder().build()
        //tag::doc_map_properties_init[]
        val fragment = MapFragment.newInstance(mapProperties)
        val view = MapView(context, mapProperties)
        //end::doc_map_properties_init[]
        //tag::doc_init_map_fragment_from_code[]
        val mapFragment = MapFragment.newInstance()
        //end::doc_init_map_fragment_from_code[]
        //tag::doc_init_map_view_from_code[]
        val mapView = MapView(context)
        //end::doc_init_map_view_from_code[]
    }
}