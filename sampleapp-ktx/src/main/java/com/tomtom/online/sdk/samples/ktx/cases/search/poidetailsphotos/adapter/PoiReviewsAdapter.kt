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
package com.tomtom.online.sdk.samples.ktx.cases.search.poidetailsphotos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomtom.online.sdk.search.poi.details.Review
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.poi_details_review_item.view.*

class PoiReviewsAdapter(private var poiReviews: List<Review>) :
    RecyclerView.Adapter<PoiReviewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiReviewsViewHolder =
        PoiReviewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.poi_details_review_item, parent, false)
        )

    override fun getItemCount(): Int = poiReviews.size

    override fun onBindViewHolder(holder: PoiReviewsViewHolder, position: Int) {
        holder.bind(poiReviews[position])
    }
}

class PoiReviewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(review: Review) {
        itemView.review_date.text = review.date?.toString(POI_REVIEW_DATE_PATTERN)
        itemView.review_content.text = review.text
    }

    private companion object {
        private const val POI_REVIEW_DATE_PATTERN = "yyyy-MM-dd"
    }
}