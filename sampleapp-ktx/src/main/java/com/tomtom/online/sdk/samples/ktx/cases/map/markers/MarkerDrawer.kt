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

package com.tomtom.online.sdk.samples.ktx.cases.map.markers

import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.samples.ktx.utils.formatter.LatLngFormatter
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations

class MarkerDrawer(private val tomtomMap: TomtomMap) {

    fun createSimpleMarkers(location: LatLng, number: Int, offsetMultiplier: Float) {
        val list = Locations.randomLocations(location, number, offsetMultiplier)
        list.forEach { position ->
            //tag::doc_create_simple_marker[]
            val markerBuilder = MarkerBuilder(position)
                    .markerBalloon(SimpleMarkerBalloon(positionToText(position)))
            tomtomMap.addMarker(markerBuilder)
            //end::doc_create_simple_marker[]
        }
    }

    fun createDecalMarkers(location: LatLng, number: Int, offsetMultiplier: Float, icon: Icon) {
        val list = Locations.randomLocations(location, number, offsetMultiplier)
        list.forEach { position ->
            //tag::doc_create_decal_marker[]
            val markerBuilder = MarkerBuilder(position)
                    .icon(icon)
                    .markerBalloon(SimpleMarkerBalloon(positionToText(position)))
                    .tag(SERIALIZABLE_MARKER_TAG)
                    .iconAnchor(MarkerAnchor.Bottom)
                    .decal(true) //By default is false
            tomtomMap.addMarker(markerBuilder)
            //end::doc_create_decal_marker[]
        }
    }

    fun createAnimatedMarkers(location: LatLng, number: Int, offsetMultiplier: Float, icon: Icon) {
        val list = Locations.randomLocations(location, number, offsetMultiplier)
        list.forEach { position ->
            //tag::doc_create_animated_marker[]
            val markerBuilder = MarkerBuilder(position)
                    .icon(icon)
            tomtomMap.addMarker(markerBuilder)
            //end::doc_create_animated_marker[]
        }
    }

    fun createDraggableMarkers(location: LatLng, number: Int, offsetMultiplier: Float) {
        val list = Locations.randomLocations(location, number, offsetMultiplier)
        list.forEach { position ->
            //tag::doc_create_simple_draggable_marker[]
            val markerBuilder = MarkerBuilder(position)
                    .markerBalloon(SimpleMarkerBalloon(positionToText(position)))
                    .draggable(true)
            tomtomMap.addMarker(markerBuilder)
            //end::doc_create_simple_draggable_marker[]
        }
    }

    fun createMarkerWithSimpleBalloon(position: LatLng) {
        //tag::doc_init_popup_layout[]
        tomtomMap.markerSettings.markerBalloonViewAdapter = TextBalloonViewAdapter()
        val balloon = SimpleMarkerBalloon(SIMPLE_BALLOON_TEXT)
        val markerBuilder = MarkerBuilder(position)
                .markerBalloon(balloon)

        val marker = tomtomMap.addMarker(markerBuilder)
        //end::doc_init_popup_layout[]
        marker.select()
    }

    fun createMarkerWithCustomBalloon(position: LatLng, layoutRes: Int) {
        //tag::doc_show_popup_layout[]
        tomtomMap.markerSettings.markerBalloonViewAdapter = SingleLayoutBalloonViewAdapter(layoutRes)
        val markerBuilder = MarkerBuilder(position)
                .markerBalloon(BaseMarkerBalloon())

        val marker = tomtomMap.addMarker(markerBuilder)
        //end::doc_show_popup_layout[]
        marker.select()
    }

    fun createMarkersWithClustering(centerLocation: LatLng, number: Int, offsetMultiplier: Float) {
        val list = Locations.randomLocations(centerLocation, number, offsetMultiplier)
        list.forEach { position ->
            //tag::doc_add_marker_to_cluster[]
            val markerBuilder = MarkerBuilder(position)
                    .shouldCluster(true)
                    .markerBalloon(SimpleMarkerBalloon(positionToText(position)))
            //end::doc_add_marker_to_cluster[]
            tomtomMap.addMarker(markerBuilder)
        }
    }

    private fun positionToText(position: LatLng): String {
        return LatLngFormatter.toSimpleString(position)
    }

    companion object {
        private const val SERIALIZABLE_MARKER_TAG = "tag"
        private const val SIMPLE_BALLOON_TEXT = "Welcome to TomTom"
    }
}