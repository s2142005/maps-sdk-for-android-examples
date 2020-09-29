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
package com.tomtom.online.sdk.samples.ktx.cases.search.poidetailsphotos.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.poi_photo_view_pager_page.view.*

class PoiPhotosAdapter(private var poiPhotos: List<Bitmap>) :
    RecyclerView.Adapter<PoiPhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiPhotosViewHolder =
        PoiPhotosViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.poi_photo_view_pager_page, parent, false)
        )

    override fun getItemCount(): Int = poiPhotos.size

    override fun onBindViewHolder(holder: PoiPhotosViewHolder, position: Int) {
        holder.bind(poiPhotos[position])
    }

    fun clearData() {
        poiPhotos = listOf()
        notifyDataSetChanged()
    }
}

class PoiPhotosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(bitmap: Bitmap) {
        itemView.poi_image.setImageBitmap(bitmap)
    }
}