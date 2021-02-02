/**
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.search.poidetailsphotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.navigation.navOptions
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.MarkerBuilder
import com.tomtom.online.sdk.map.TomtomMapCallback
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceObserver
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchDetails
import com.tomtom.online.sdk.search.poi.details.PoiDetails
import com.tomtom.sdk.examples.R

class PoiDetailsPhotosFragment : ExampleFragment() {

    val viewModel: PoiDetailsPhotosViewModel by navGraphViewModels(R.id.poi_details_photos_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.default_map_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewModel()
    }

    override fun onExampleStarted() {
        super.onExampleStarted()
        viewModel.search(FUZZY_SEARCH_QUERY_TERM)
    }

    private fun confViewModel() {
        this.viewModel.searchResults.observe(
            viewLifecycleOwner,
            ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = {
                    viewModel.searchResults.removeObservers(this)
                    showMarkers(it)
                },
                onError = ::showError
            )
        )
    }

    private fun showMarkers(list: List<FuzzySearchDetails>) {
        mainViewModel.applyOnMap(MapAction {
            val markerIcon = Icon.Factory.fromResources(requireContext(), R.drawable.ic_favourites)
            list.filter { fuzzyDetails -> fuzzyDetails.poi != null && fuzzyDetails.additionalDataSources != null }
                .forEach { fuzzyDetails ->
                    markerSettings.addMarker(
                        MarkerBuilder(fuzzyDetails.position)
                            .icon(markerIcon)
                            .decal(true)
                            .tag(fuzzyDetails)
                    )
                }
            zoomToAllMarkers()
        })
    }

    private fun showFragment(fuzzyPair: Pair<FuzzySearchDetails, PoiDetails>) {
        Navigation.findNavController(requireView()).navigate(
            R.id.poiDetailsFragment,
            bundleOf(POI_DETAILS_KEY to fuzzyPair.second, POI_KEY to fuzzyPair.first),
            navOptions {
                anim {
                    enter = R.anim.slide_in_left
                    exit = R.anim.slide_out_left
                    popEnter = R.anim.slide_in_right
                    popExit = R.anim.slide_out_right
                }
            })
    }

    private val onMarkerClickListener = TomtomMapCallback.OnMarkerClickListener { marker ->
        viewModel.recycleBitmaps()
        val fuzzySearchDetails = marker.tag as FuzzySearchDetails
        retrievePoiDetails(fuzzySearchDetails)
    }

    private fun retrievePoiDetails(fuzzySearchDetails: FuzzySearchDetails) {
        viewModel.poiDetails.value = null
        viewModel.poiDetails.observe(
            this, ResourceObserver(
                hideLoading = {},
                showLoading = ::showLoading,
                onSuccess = {
                    viewModel.poiDetails.removeObservers(this)
                    downloadImages(fuzzySearchDetails, it)
                },
                onError = {
                    hideLoading()
                    showError(it)
                }
            )
        )
        viewModel.searchPoiDetails(fuzzySearchDetails)
    }

    private fun downloadImages(fuzzySearchDetails: FuzzySearchDetails, poiDetails: PoiDetails) {
        viewModel.poiPhotos.value = null
        viewModel.poiPhotos.observe(
            this, ResourceObserver(
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = {
                    viewModel.poiPhotos.removeObservers(this)
                    showFragment(Pair(fuzzySearchDetails, poiDetails))
                },
                onError = ::showError
            )
        )
        viewModel.downloadPoiPhoto(poiDetails.photos)
    }

    override fun onResume() {
        super.onResume()
        registerListeners()
    }

    override fun onPause() {
        super.onPause()
        unregisterListeners()
    }

    override fun onExampleEnded() {
        super.onExampleEnded()
        mainViewModel.applyOnMap(MapAction { markerSettings.removeMarkers() })
        viewModel.recycleBitmaps()
    }

    private fun registerListeners() {
        mainViewModel.applyOnMap(MapAction {
            addOnMarkerClickListener(onMarkerClickListener)
        })
    }

    private fun unregisterListeners() {
        mainViewModel.applyOnMap(MapAction {
            removeOnMarkerClickListener(onMarkerClickListener)
        })
    }

    companion object {
        internal const val FUZZY_SEARCH_QUERY_TERM = "Restaurant"
        internal const val POI_DETAILS_KEY = "Details"
        internal const val POI_KEY = "Poi"
    }
}