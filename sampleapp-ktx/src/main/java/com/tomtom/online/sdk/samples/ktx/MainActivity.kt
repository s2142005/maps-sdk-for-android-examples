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

package com.tomtom.online.sdk.samples.ktx

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.common.permission.AppPermissionHandler
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.samples.ktx.extensions.visibleIf
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mapFragment: MapFragment
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavController()
        initMainViewModel()
        initLocationPermissions()
        initMap()
        initAboutFragment()
    }

    private fun initNavController() {
        val navController = findNavController(R.id.main_nav_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initMap() {
        //tag::doc_initialise_map[]
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getAsyncMap(onMapReadyCallback)
        //end::doc_initialise_map[]
    }

    private fun initLocationPermissions() {
        val permissionHandler = AppPermissionHandler(this)
        permissionHandler.addLocationChecker()
        permissionHandler.askForNotGrantedPermissions()
    }

    private fun initMainViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.aboutButtonVisibility().observe(this, Observer {
            about_btn.visibleIf(it)
        })
        viewModel.mapAction().observe(this, Observer { action ->
            mapFragment.getAsyncMap { tomtomMap -> action.invoke(tomtomMap) }
        })
    }

    private fun initAboutFragment() {
        about_btn.setOnClickListener { findNavController(R.id.main_nav_fragment).navigate(R.id.about_fragment) }
    }

    //tag::doc_implement_on_map_ready_callback[]
    private val onMapReadyCallback = OnMapReadyCallback { tomtomMap ->
        val mapPaddingVertical = resources.getDimension(R.dimen.map_padding_vertical).toDouble()
        val mapPaddingHorizontal = resources.getDimension(R.dimen.map_padding_horizontal).toDouble()

        tomtomMap.uiSettings.currentLocationView.hide()
        tomtomMap.setPadding(mapPaddingVertical, mapPaddingHorizontal,
            mapPaddingVertical, mapPaddingHorizontal)
        tomtomMap.collectLogsToFile(SampleApp.LOG_FILE_PATH)
    }
    //end::doc_implement_on_map_ready_callback[]

    @Suppress("unused")
    private val onMapReadyCallbackSaveLogs = object : OnMapReadyCallback {
        //tag::doc_collect_logs_to_file_in_onready_callback[]
        override fun onMapReady(tomtomMap: TomtomMap) {
            tomtomMap.collectLogsToFile(SampleApp.LOG_FILE_PATH)
        }
        //end::doc_collect_logs_to_file_in_onready_callback[]
    }

    @Suppress("unused")
    private fun initMapWithProperties() {
        //tag::doc_initial_map_properties[]
        val keysMap = mapOf(ApiKeyType.MAPS_API_KEY to "online-maps-key")
        val cameraPosition = CameraPosition.builder()
            .focusPosition(LatLng(12.34, 23.45))
            .zoom(10.0)
            .bearing(24.0)
            .build()
        val mapProps = MapProperties.Builder()
            .customStyleUri("asset://styles/style.json")
            .backgroundColor(Color.BLUE)
            .keys(keysMap)
            .cameraPosition(cameraPosition)
            .build()
        val fragment = MapFragment.newInstance(mapProps)
        //end::doc_initial_map_properties[]
    }
}
