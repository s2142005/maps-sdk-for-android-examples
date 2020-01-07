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

package com.tomtom.online.sdk.samples.ktx.cases.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomtom.online.sdk.samples.ktx.utils.formatter.DistanceFormatter
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.search_result_list_item.view.*

class SearchResultsAdapter(private val searchResults: MutableList<FuzzySearchResult> = mutableListOf()) : RecyclerView.Adapter<SearchResultViewHolder>() {

    fun updateData(searchResults: List<FuzzySearchResult>) {
        this.searchResults.clear()
        this.searchResults.addAll(searchResults)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder =
        SearchResultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_result_list_item, parent, false))

    override fun getItemCount(): Int = searchResults.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(searchResults[position])
    }
}

class SearchResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val distanceTv = itemView.distanceTv
    private val primaryAddressTv = itemView.primaryAddressTv
    private val secondaryAddressTv = itemView.secondaryAddressTv

    fun bind(fuzzySearchResult: FuzzySearchResult) {
        distanceTv.text = DistanceFormatter.format(fuzzySearchResult.distance.toInt())
        primaryAddressTv.text = fuzzySearchResult.address.freeformAddress
        secondaryAddressTv.text = fuzzySearchResult.address.country
    }
}
