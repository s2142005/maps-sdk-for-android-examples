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
package com.tomtom.online.sdk.samples.ktx.cases

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.AnimationDuration
import com.tomtom.online.sdk.map.CameraFocusArea
import com.tomtom.online.sdk.map.MapConstants
import com.tomtom.online.sdk.samples.IdlingResourceHelper
import com.tomtom.online.sdk.samples.ktx.MainViewModel
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.dialogs.ProgressFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import java.util.concurrent.TimeUnit

abstract class ExampleFragment : Fragment(), ExampleLifecycle {

    lateinit var mainViewModel: MainViewModel
    lateinit var exampleViewModel: ExampleViewModel

    private val idlingResourceHelper = IdlingResourceHelper(ProgressFragment.PROGRESS_FRAGMENT_TAG)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Handle back press for proper navigation
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, ExampleOnBackPressedCallback())

        //Example view model
        exampleViewModel = ViewModelProviders.of(this).get(ExampleViewModel::class.java)

        //Shared view model
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.applyAboutButtonVisibility(false)

        //Restore if required
        if (!isRestored()) {
            onExampleStarted()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        exampleViewModel.isRestored = true
    }

    inner class ExampleOnBackPressedCallback : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            mainViewModel.applyAboutButtonVisibility(true)
            onExampleEnded()
            isEnabled = false
            requireActivity().onBackPressed()
        }
    }

    private fun isRestored(): Boolean {
        return exampleViewModel.isRestored
    }

    override fun onExampleStarted() {
    }

    override fun onExampleEnded() {
    }

    fun centerOnLocation(location: LatLng = Locations.AMSTERDAM,
                         zoomLevel: Double = DEFAULT_MAP_ZOOM_LEVEL_FOR_EXAMPLE,
                         bearing: Int = MapConstants.ORIENTATION_NORTH) {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_map_center_on_amsterdam[]
                tomtomMap.centerOn(location.latitude,
                    location.longitude,
                    zoomLevel,
                    bearing)
                //end::doc_map_center_on_amsterdam[]
            }
        })
    }

    protected fun centerOnArea(topLeft: LatLng, bottomRight: LatLng) {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::doc_map_center_on_area[]
                val areaBox = BoundingBox(topLeft, bottomRight)
                val cameraFocusArea = CameraFocusArea.Builder(areaBox)
                    .apply {
                        bearing(MapConstants.ORIENTATION_SOUTH.toDouble())
                        pitch(45.0)
                    }
                    .build()
                tomtomMap.centerOn(cameraFocusArea, AnimationDuration(1500, TimeUnit.MILLISECONDS))
                //end::doc_map_center_on_area[]
            }
        })
    }

    open fun showLoading() {
        idlingResourceHelper.startIdling()
        fragmentManager?.let { fm ->
            ProgressFragment.show(fm)
        }
    }

    open fun hideLoading() {
        fragmentManager?.let { fm ->
            ProgressFragment.hide(fm)
        }
        idlingResourceHelper.stopIdling()
    }

    open fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val DEFAULT_MAP_ZOOM_LEVEL_FOR_EXAMPLE = 10.0
    }

}