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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.geofencing.GeofencingException
import com.tomtom.online.sdk.geofencing.report.ReportCallback
import com.tomtom.online.sdk.geofencing.report.Report
import com.tomtom.online.sdk.map.Marker
import com.tomtom.online.sdk.map.TomtomMapCallback
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_geofencing_report.*
import kotlinx.android.synthetic.main.default_map_fragment.*
import java.util.*

class ReportServiceFragment : ExampleFragment() {

    private lateinit var viewModel: ReportServiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewModel()
        confViewActions()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnDefaultLocation()
    }

    override fun onResume() {
        super.onResume()
        confMapActions()
    }

    override fun onPause() {
        super.onPause()
        removeMapActions()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        clearMap()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(
            R.layout.control_buttons_geofencing_report,
            mapControlButtonsContainer,
            true
        )
    }

    private fun confViewModel() {
        viewModel = ViewModelProviders.of(this).get(ReportServiceViewModel::class.java)
        viewModel.reportResponse.observe(
            this, ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::processResponse,
                onError = ::showError
            )
        )
    }

    private fun confViewActions() {
        geofencing_report_one_fence_btn.setOnClickListener {
            centerOnDefaultLocation()
            updateExampleConfig(PROJECT_UUID_ONE_FENCE)
            viewModel.requestReport(Locations.AMSTERDAM_BARNDESTEEG)
        }

        geofencing_report_two_fences.setOnClickListener {
            centerOnDefaultLocation()
            updateExampleConfig(PROJECT_UUID_TWO_FENCES)
            viewModel.requestReport(Locations.AMSTERDAM_BARNDESTEEG)
        }
    }

    private fun processResponse(report: Report) {
        processReport(report)
    }

    private fun processReport(report: Report) {
        mainViewModel.applyOnMap(MapAction {
            val markerDrawer = ReportServiceMarkerDrawer(requireContext(), this)
            markerDrawer.removeFenceMarkers()
            markerDrawer.drawMarkersForFences(report)
            markerDrawer.updateDraggableMarkerText(report)
        })
    }

    private fun drawOneFence() {
        mainViewModel.applyOnMap(MapAction {
            ReportServiceFencesDrawer(this).drawPolygonFence()
        })
    }

    private fun drawTwoFences() {
        mainViewModel.applyOnMap(MapAction {
            val reportServiceFencesDrawer = ReportServiceFencesDrawer(this)
            reportServiceFencesDrawer.drawCircularFence()
            reportServiceFencesDrawer.drawPolygonFence()
        })
    }

    private fun drawDefaultMarker() {
        mainViewModel.applyOnMap(MapAction {
            ReportServiceMarkerDrawer(requireContext(), this).drawDraggableMarkerAtDefaultPosition()
        })
    }

    private fun updateExampleConfig(projectId: UUID) {
        clearMap()

        viewModel.projectId = projectId

        when (projectId) {
            PROJECT_UUID_ONE_FENCE -> drawOneFence()
            PROJECT_UUID_TWO_FENCES -> drawTwoFences()
        }

        drawDefaultMarker()
    }

    private fun clearMap() {
        mainViewModel.applyOnMap(MapAction {
            overlaySettings.removeOverlays()
            markerSettings.removeMarkers()
        })
    }

    private val markerDragListener = object : TomtomMapCallback.OnMarkerDragListener {
        override fun onStartDragging(marker: Marker) {
            marker.deselect()
        }

        override fun onStopDragging(marker: Marker) {
            viewModel.requestReport(marker.position)
        }

        override fun onDragging(marker: Marker) {
        }
    }

    private fun confMapActions() {
        mainViewModel.applyOnMap(MapAction {
            markerSettings.addOnMarkerDragListener(markerDragListener)
        })
    }

    private fun removeMapActions() {
        mainViewModel.applyOnMap(MapAction {
            markerSettings.removeOnMarkerDragListeners()
        })
    }

    private fun centerOnDefaultLocation() {
        centerOnLocation(Locations.AMSTERDAM_BARNDESTEEG, ZOOM_LEVEL_FOR_EXAMPLE)
    }

    @Suppress("unused")
    //tag::doc_register_report_listener[]
    private val resultListener = object :
        ReportCallback {
        override fun onSuccess(report: Report) {
            processResponse(report)
        }

        override fun onError(error: GeofencingException) {
            Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
        }
    }
    //end::doc_register_report_listener[]

    companion object {
        //tag::doc_projects_ID[]
        private val PROJECT_UUID_TWO_FENCES =
            UUID.fromString("fcf6d609-550d-49ff-bcdf-02bba08baa28")
        private val PROJECT_UUID_ONE_FENCE = UUID.fromString("57287023-a968-492c-8473-7e049a606425")
        //end::doc_projects_ID[]
        private const val ZOOM_LEVEL_FOR_EXAMPLE = 12.0
    }
}
