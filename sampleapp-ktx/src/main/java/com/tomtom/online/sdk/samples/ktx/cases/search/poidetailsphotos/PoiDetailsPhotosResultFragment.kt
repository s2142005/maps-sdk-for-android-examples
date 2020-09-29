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
package com.tomtom.online.sdk.samples.ktx.cases.search.poidetailsphotos

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.common.base.Joiner
import com.tomtom.online.sdk.samples.ktx.cases.search.poidetailsphotos.PoiDetailsPhotosFragment.Companion.POI_DETAILS_KEY
import com.tomtom.online.sdk.samples.ktx.cases.search.poidetailsphotos.PoiDetailsPhotosFragment.Companion.POI_KEY
import com.tomtom.online.sdk.samples.ktx.cases.search.poidetailsphotos.adapter.PoiPhotosAdapter
import com.tomtom.online.sdk.samples.ktx.cases.search.poidetailsphotos.adapter.PoiReviewsAdapter
import com.tomtom.online.sdk.samples.ktx.utils.text.TextFormatter
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchDetails
import com.tomtom.online.sdk.search.poi.details.PoiDetails
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_poi_details.*

class PoiDetailsPhotosResultFragment : Fragment() {

    private lateinit var photosAdapter: PoiPhotosAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_poi_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = this.requireArguments()
        val fuzzyDetails = arguments[POI_KEY] as FuzzySearchDetails
        val poiDetails = arguments[POI_DETAILS_KEY] as PoiDetails
        confViewModel()
        confViewPager()
        confTabLayout()
        confPoiDetailsViews(fuzzyDetails, poiDetails)
    }

    private fun confViewModel() {
        val viewModel: PoiDetailsPhotosViewModel by navGraphViewModels(R.id.poi_details_photos_graph)
        photosAdapter = PoiPhotosAdapter(viewModel.poiPhotos.value?.data.orEmpty())
    }

    private fun confViewPager() {
        poi_photos_view.adapter = photosAdapter
        poi_photos_view.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                val tabLayout: TabLayout = requireView().findViewById(R.id.tab_layout)
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        tabLayout.animate().cancel()
                        tabLayout.alpha = VIEW_ALPHA_VISIBLE
                    }
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        tabLayout.animate()
                            .alpha(VIEW_ALPHA_INVISIBLE)
                            .setStartDelay(VIEW_ALPHA_ANIMATION_DELAY)
                            .duration = VIEW_ALPHA_ANIMATION_DURATION
                    }
                    ViewPager2.SCROLL_STATE_SETTLING -> {
                    }
                }
            }
        })
    }

    private fun confTabLayout() {
        val tabLayout: TabLayout = requireView().findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, poi_photos_view) { _, _ ->
        }.attach()
    }

    private fun confPoiDetailsViews(fuzzyDetails: FuzzySearchDetails, poiDetails: PoiDetails) {
        poi_name.text = fuzzyDetails.poi?.name
        poi_address.text = fuzzyDetails.address?.freeFormAddress
        confCategoriesLabel(fuzzyDetails)
        confRatingLabel(poiDetails)
        confPricingLabel(poiDetails)
        confSocialMediaView(poiDetails)
        confReviews(poiDetails)
    }

    private fun confCategoriesLabel(fuzzyDetails: FuzzySearchDetails) {
        poi_category.text = getString(R.string.poi_details_category_title)

        fuzzyDetails.poi?.let {
            val categoriesList = it.categories
            val poiCategoriesValue = if (categoriesList.isNotEmpty()) {
                Joiner.on(CATEGORY_DIVIDER).join(categoriesList)
            } else {
                getString(R.string.data_not_available)
            }
            poi_category.append(TextFormatter.applyColor(poiCategoriesValue, Color.BLACK))
        }
    }

    private fun confRatingLabel(poiDetails: PoiDetails) {
        poi_rating.text = getString(R.string.poi_details_rating_title)

        val poiRatingValue = poiDetails.rating?.let {
            getString(R.string.poi_details_rating_value, it.value, it.maxValue, it.totalRatings)
        } ?: getString(R.string.data_not_available)

        poi_rating.append(TextFormatter.applyColor(poiRatingValue, Color.BLACK))
    }

    private fun confPricingLabel(poiDetails: PoiDetails) {
        poi_pricing.text = getString(R.string.poi_details_pricing_title)

        val poiPricingValue = poiDetails.priceRange?.let {
            getString(R.string.poi_details_pricing_value, it.value, it.maxValue)
        } ?: getString(R.string.data_not_available)

        poi_pricing.append(TextFormatter.applyColor(poiPricingValue, Color.BLACK))
    }

    private fun confSocialMediaView(poiDetails: PoiDetails) {
        poi_social_media.text = getString(R.string.poi_details_social_media)

        if (poiDetails.socialMedias.isEmpty()) {
            poi_social_media_details.text = getString(R.string.data_not_available)
            return
        }
        poiDetails.socialMedias.forEach { socialMedia ->
            poi_social_media_details.append(socialMedia.url + NEW_LINE_DIVIDER)
        }
    }

    private fun confReviews(poiDetails: PoiDetails) {
        if (poiDetails.reviews.isNotEmpty()) {
            poi_reviews_title.text = getString(R.string.poi_details_review_title)

            val recyclerView = reviews_recycler_view
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = PoiReviewsAdapter(poiDetails.reviews)
            recyclerView.isNestedScrollingEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        photosAdapter.clearData()
    }

    private companion object {
        private const val NEW_LINE_DIVIDER = "\n"
        private const val CATEGORY_DIVIDER = "/"
        private const val VIEW_ALPHA_VISIBLE = 1.0f
        private const val VIEW_ALPHA_INVISIBLE = 0.0f
        private const val VIEW_ALPHA_ANIMATION_DELAY = 300L
        private const val VIEW_ALPHA_ANIMATION_DURATION = 500L
    }
}