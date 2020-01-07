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

package com.tomtom.online.sdk.samples.ktx.cases.map.uiextensions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.map.MapConstants
import com.tomtom.online.sdk.map.ui.arrowbuttons.ArrowButtonsGroup
import com.tomtom.online.sdk.map.ui.zoom.ZoomButtonsGroup
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.control_buttons_ui_extensions.*
import kotlinx.android.synthetic.main.default_map_fragment.*

class MapUiExtensionsFragment : ExampleFragment() {

    private lateinit var mapUiExtensionsViewModel: MapUiExtensionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.default_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateControlButtons()
        confViewActions()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapUiExtensionsViewModel =
            ViewModelProviders.of(this).get(MapUiExtensionsViewModel::class.java)
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        centerOnWithBearing()
        setLocationUpdates(true)
    }

    override fun onResume() {
        super.onResume()
        confUiElements()
        adjustCurrentLocationViewMargins()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        defaultUiVisibility()
        defaultUiComponentsIcons()
        defaultCurrentLocationViewMargins()
        setLocationUpdates(false)
    }

    private fun inflateControlButtons() {
        layoutInflater.inflate(
            R.layout.control_buttons_ui_extensions,
            mapControlButtonsContainer,
            true
        )
    }

    private fun setLocationUpdates(enabled: Boolean) {
        mainViewModel.applyOnMap(MapAction {
            isMyLocationEnabled = enabled
        })
    }

    private fun confViewActions() {
        map_uiextensions_default_btn.setOnClickListener {
            centerOnWithBearing()
            showUi()
            defaultUiComponentsIcons()
        }

        map_uiextensions_custom_btn.setOnClickListener {
            centerOnWithBearing()
            showUi()
            customUiComponentsIcons()
        }

        map_uiextensions_hide_btn.setOnClickListener {
            centerOnWithBearing()
            hideUi()
        }
    }

    private fun confUiElements() {
        when (mapUiExtensionsViewModel.isUiCustom) {
            true -> customUiComponentsIcons()
            false -> defaultUiComponentsIcons()
        }
        when (mapUiExtensionsViewModel.isUiShown) {
            true -> showUi()
            false -> hideUi()
        }
    }

    private fun hideUi() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::hide_panning_controls[]
                tomtomMap.uiSettings.panningControlsView.hide()
                //end::hide_panning_controls[]

                //tag::hide_zooming_controls[]
                tomtomMap.uiSettings.zoomingControlsView.hide()
                //end::hide_zooming_controls[]

                //tag::hide_current_location[]
                tomtomMap.uiSettings.currentLocationView.hide()
                //end::hide_current_location[]

                //tag::hide_compass[]
                tomtomMap.uiSettings.compassView.hide()
                //end::hide_compass[]
            }
        })
        mapUiExtensionsViewModel.isUiShown = false
    }

    private fun showUi() {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::show_panning_controls[]
                tomtomMap.uiSettings.panningControlsView.show()
                //end::show_panning_controls[]

                //tag::show_zooming_controls[]
                tomtomMap.uiSettings.zoomingControlsView.show()
                //end::show_zooming_controls[]

                //tag::show_current_location[]
                tomtomMap.uiSettings.currentLocationView.show()
                //end::show_current_location[]

                //tag::show_compass[]
                tomtomMap.uiSettings.compassView.show()
                //end::show_compass[]
            }
        })
        mapUiExtensionsViewModel.isUiShown = true
    }

    private fun defaultUiVisibility() {
        mainViewModel.applyOnMap(MapAction {
            uiSettings.panningControlsView.hide()
            uiSettings.zoomingControlsView.hide()
            uiSettings.currentLocationView.hide()
            uiSettings.compassView.show()
        })
    }

    private fun defaultUiComponentsIcons() {
        mainViewModel.applyOnMap(MapAction {
            uiSettings.compassView.getView<ImageView>().setImageResource(R.drawable.btn_compass)
            uiSettings.currentLocationView.getView<ImageView>()
                .setImageResource(R.drawable.btn_current_location)

            let { tomtomMap ->
                //tag::set_default_panning_controls[]
                val arrowButton: ArrowButtonsGroup = tomtomMap.uiSettings.panningControlsView.getView()
                arrowButton.arrowDownButton.setImageResource(R.drawable.btn_down)
                arrowButton.arrowUpButton.setImageResource(R.drawable.btn_up)
                arrowButton.arrowLeftButton.setImageResource(R.drawable.btn_left)
                arrowButton.arrowRightButton.setImageResource(R.drawable.btn_right)
                //end::set_default_panning_controls[]

                //tag::set_default_zooming_controls[]
                val zoomButtons: ZoomButtonsGroup = uiSettings.zoomingControlsView.getView()
                zoomButtons.zoomInButton.setImageResource(R.drawable.btn_zoom_in)
                zoomButtons.zoomOutButton.setImageResource(R.drawable.btn_zoom_out)
                //end::set_default_zooming_controls[]
            }
        })
        mapUiExtensionsViewModel.isUiCustom = false
    }

    private fun customUiComponentsIcons() {
        mainViewModel.applyOnMap(MapAction {
            uiSettings.compassView.getView<ImageView>()
                .setImageResource(R.drawable.ic_compass_custom)
            uiSettings.currentLocationView.getView<ImageView>()
                .setImageResource(R.drawable.ic_current_location_custom)

            let { tomtomMap ->
                //tag::set_custom_panning_controls[]
                val arrowButtonGroup: ArrowButtonsGroup = tomtomMap.uiSettings.panningControlsView.getView()
                arrowButtonGroup.arrowDownButton.setImageResource(R.drawable.arrow_button_down_custom)
                arrowButtonGroup.arrowUpButton.setImageResource(R.drawable.arrow_button_up_custom)
                arrowButtonGroup.arrowLeftButton.setImageResource(R.drawable.arrow_button_left_custom)
                arrowButtonGroup.arrowRightButton.setImageResource(R.drawable.arrow_button_right_custom)
                //end::set_custom_panning_controls[]

                //tag::set_custom_zooming_controls[]
                val zoomButtonsGroup: ZoomButtonsGroup = tomtomMap.uiSettings.zoomingControlsView.getView()
                zoomButtonsGroup.zoomInButton.setImageResource(R.drawable.zoom_in_button_custom)
                zoomButtonsGroup.zoomOutButton.setImageResource(R.drawable.zoom_out_button_custom)
                //end::set_custom_zooming_controls[]
            }
        })
        mapUiExtensionsViewModel.isUiCustom = true
    }

    @Suppress("unused")
    private fun confLogoComponent(gravity: Int, top: Int, left: Int, right: Int, bottom: Int) {
        mainViewModel.applyOnMap(MapAction {
            val tomtomMap = this

            //tag::set_custom_logo_gravity[]
            tomtomMap.uiSettings.logoView.setGravity(gravity)
            //end::set_custom_logo_gravity[]

            //tag::set_custom_logo_margins[]
            tomtomMap.uiSettings.logoView.setMargins(left, top, right, bottom)
            //end::set_custom_logo_margins[]

            //tag::restore_default_logo_margins[]
            tomtomMap.uiSettings.logoView.restoreDefaultMargins()
            //end::restore_default_logo_margins[]

            //tag::restore_default_logo_gravity[]
            tomtomMap.uiSettings.logoView.restoreDefaultGravity()
            //end::restore_default_logo_gravity[]

            //tag::apply_default_logo[]
            tomtomMap.uiSettings.logoView.applyDefaultLogo()
            //end::apply_default_logo[]

            //tag::apply_inverted_logo[]
            tomtomMap.uiSettings.logoView.applyInvertedLogo()
            //end::apply_inverted_logo[]
        })
    }

    private fun centerOnWithBearing() {
        centerOnLocation(bearing = MapConstants.ORIENTATION_NORTH_WEST)
    }

    private fun adjustCurrentLocationViewMargins() {
        mainViewModel.adjustCurrentLocationViewMargins()
    }

    private fun defaultCurrentLocationViewMargins() {
        mainViewModel.resetCurrentLocationViewMargins()
    }
}
