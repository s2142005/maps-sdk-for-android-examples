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

package com.tomtom.online.sdk.samples.ktx.cases.search.poicategories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomtom.online.sdk.search.data.poicategories.PoiCategory
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.list_item_poi_categories.view.*

class PoiCategoriesAdapter(private val onPoiCategoryClickListener: OnPoiCategoryClickListener? = null) :
    RecyclerView.Adapter<PoiCategoryViewHolder>() {

    private val poiCategories: MutableList<PoiCategory> = mutableListOf()

    fun updateData(foundPoiCategories: List<PoiCategory>) {
        this.poiCategories.clear()
        this.poiCategories.addAll(foundPoiCategories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiCategoryViewHolder =
        PoiCategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_poi_categories, parent, false)
        )

    override fun getItemCount(): Int = poiCategories.size

    override fun onBindViewHolder(holder: PoiCategoryViewHolder, position: Int) {
        holder.bind(poiCategories[position], onPoiCategoryClickListener)
    }

    companion object {
        internal const val DATA_POI_SUBCATEGORIES = "DATA_POI_SUBCATEGORIES"
        internal const val DATA_POI_CATEGORY_NAME = "DATA_POI_CATEGORY_NAME"
    }
}

interface OnPoiCategoryClickListener {
    fun onPoiCategoryClicked(poiCategory: PoiCategory)
}

class PoiCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(poiCategory: PoiCategory, onPoiCategoryClickListener: OnPoiCategoryClickListener?) {
        itemView.poi_category_name.text = poiCategory.name
        if (poiCategory.children.isEmpty()) {
            itemView.isClickable = false
            itemView.poi_category_arrow.visibility = View.INVISIBLE
        } else {
            itemView.isClickable = true
            itemView.poi_category_arrow.visibility = View.VISIBLE
            itemView.setOnClickListener {
                onPoiCategoryClickListener?.onPoiCategoryClicked(poiCategory)
            }
        }
    }
}
