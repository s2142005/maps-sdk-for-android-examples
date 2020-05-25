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
package com.tomtom.online.sdk.samples.ktx.cases.geofencing.report

import android.content.Context
import com.google.common.collect.ImmutableList
import com.tomtom.online.sdk.geofencing.report.FenceDetails
import com.tomtom.online.sdk.geofencing.report.Report
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.samples.ktx.cases.geofencing.report.utils.InsideFencesDescriptionProcessor
import com.tomtom.online.sdk.samples.ktx.cases.geofencing.report.utils.InsideOutsideFencesDescriptionProcessor
import com.tomtom.online.sdk.samples.ktx.cases.geofencing.report.utils.OutsideFencesDescriptionProcessor
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R

class ReportServiceMarkerDrawer(private val context: Context, private val tomtomMap: TomtomMap) {

    fun drawMarkersForFences(report: Report) {
        val fenceDetailsList = ArrayList<FenceDetails>()
        fenceDetailsList.addAll(report.inside)
        fenceDetailsList.addAll(report.outside)

        fenceDetailsList.forEach { fenceDetail ->
            val markerBuilder = MarkerBuilder(fenceDetail.closestPoint)
                .icon(createMarkerIcon())
                .markerBalloon(
                    SimpleMarkerBalloon(
                        context.resources.getString(
                            R.string.report_service_fence_marker_balloon,
                            fenceDetail.fence.name
                        )
                    )
                )
                .tag(FENCE_MARKERS_TAG)

            tomtomMap.addMarker(markerBuilder)
        }
    }

    fun drawDraggableMarkerAtDefaultPosition() {
        val markerBuilder = MarkerBuilder(Locations.AMSTERDAM_BARNDESTEEG)
            .markerBalloon(BaseMarkerBalloon())
            .tag(DRAGGABLE_MARKER_TAG)
            .draggable(true)

        tomtomMap.addMarker(markerBuilder)
    }

    private fun findDraggableMarker(): Marker? {
        return tomtomMap.markerSettings.findMarkerByTag(DRAGGABLE_MARKER_TAG).orNull()
    }

    fun updateDraggableMarkerText(report: Report) {
        val descriptionProcessors = ImmutableList.of(
            InsideFencesDescriptionProcessor(),
            OutsideFencesDescriptionProcessor(),
            InsideOutsideFencesDescriptionProcessor()
        )

        findDraggableMarker()?.let { marker ->
            val baseMarkerBalloon = marker.markerBalloon.get() as BaseMarkerBalloon

            for (descriptionProcessor in descriptionProcessors) {
                if (descriptionProcessor.isValid(report)) {
                    baseMarkerBalloon.setText(descriptionProcessor.getText(context, report))
                }
            }
            marker.select()
        }
    }

    fun removeFenceMarkers() {
        tomtomMap.markerSettings.removeMarkerByTag(FENCE_MARKERS_TAG)
    }

    private fun createMarkerIcon(): Icon {
        return Icon.Factory.fromResources(context, R.drawable.ic_marker_entry_point)
    }

    companion object {
        private const val DRAGGABLE_MARKER_TAG = "DRAGGABLE_MARKER"
        const val FENCE_MARKERS_TAG = "FENCE_MARKERS"
    }
}