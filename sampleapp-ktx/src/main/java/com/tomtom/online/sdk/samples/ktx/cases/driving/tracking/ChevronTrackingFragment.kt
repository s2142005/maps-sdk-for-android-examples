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

package com.tomtom.online.sdk.samples.ktx.cases.driving.tracking

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.ChevronBuilder
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.data.FullRoute
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.driving.DrivingFragment
import com.tomtom.online.sdk.samples.ktx.utils.driving.ChevronSimulatorUpdater
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_follow_the_chevron.*
import kotlinx.android.synthetic.main.default_routing_fragment.*

class ChevronTrackingFragment : DrivingFragment<ChevronTrackingViewModel>() {

    private lateinit var chevronSimulatorUpdater: ChevronSimulatorUpdater

    override fun routingViewModel(): ChevronTrackingViewModel = ViewModelProviders.of(this)
            .get(ChevronTrackingViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(R.layout.control_buttons_follow_the_chevron, routingControlButtonsContainer, true)
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        viewModel.planDefaultRoute()
    }

    override fun onResume() {
        super.onResume()
        if (exampleViewModel.isRestored) {
            setupChevronAndSimulation()
        }
    }

    override fun displayRoutingResults(routes: List<FullRoute>) {
        super.displayRoutingResults(routes)
        centerOnLocation(DEFAULT_ROUTE.origin, zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_SIMULATION)
        viewModel.createSimulator(routes)
        setupChevronAndSimulation()
        setupControls()
    }

    private fun setupControls() {
        start_tracking_btn.setOnClickListener {
            startTracking()
        }
        stop_tracking_btn.setOnClickListener {
            stopTracking()
        }
    }

    private fun startTracking() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_start_chevron_tracking[]
                tomtomMap.drivingSettings.startTracking(chevron)
                //end::doc_start_chevron_tracking[]
            }
        })
    }

    private fun stopTracking() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_stop_chevron_tracking[]
                tomtomMap.drivingSettings.stopTracking()
                //end::doc_stop_chevron_tracking[]
            }

        })
    }

    private fun setupChevronAndSimulation() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                if (tomtomMap.drivingSettings.chevrons.isEmpty()) {
                    createChevron(tomtomMap)
                    setupSimulator()
                } else {
                    restoreChevron(tomtomMap)
                    restoreSimulator()
                }
            }
        })
    }

    private fun restoreSimulator() {
        chevronSimulatorUpdater = ChevronSimulatorUpdater(chevron)
        viewModel.restoreSimulation(chevronSimulatorUpdater)
    }

    private fun restoreChevron(tomtomMap: TomtomMap) {
        chevron = tomtomMap.drivingSettings.chevrons.first()
    }

    private fun setupSimulator() {
        chevronSimulatorUpdater = ChevronSimulatorUpdater(chevron)
        viewModel.startSimulation(chevronSimulatorUpdater)
    }

    private fun createChevron(tomtomMap: TomtomMap) {
        val activeIcon = Icon.Factory.fromResources(requireContext(), R.drawable.chevron_color, CHEVRON_ICON_SCALE)
        val inactiveIcon = Icon.Factory.fromResources(requireContext(), R.drawable.chevron_shadow, CHEVRON_ICON_SCALE)
        //tag::doc_create_chevron[]
        val chevronBuilder = ChevronBuilder.create(activeIcon, inactiveIcon)
        chevron = tomtomMap.drivingSettings.addChevron(chevronBuilder)
        //end::doc_create_chevron[]
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopSimulation()
    }

    companion object {
        private const val CHEVRON_ICON_SCALE = 2.5
        private const val DEFAULT_ZOOM_LEVEL_FOR_SIMULATION = 16.0
    }
}
