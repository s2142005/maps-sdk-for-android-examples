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

package com.tomtom.online.sdk.samples.ktx.menu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tomtom.sdk.examples.R
import com.tomtom.online.sdk.samples.ktx.menu.data.MenuItem
import kotlinx.android.synthetic.main.list_item_menu.view.*

open class MenuAdapter(private val dataSet: List<MenuItem>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleTV: TextView = view.title
        private val iconIV: ImageView = view.icon
        private val descriptionTV: TextView = view.description
        private val bannerIV: ImageView = view.banner

        fun bind(item: MenuItem) {
            titleTV.text = itemView.context.getText(item.title)
            iconIV.setImageResource(item.icon)
            descriptionTV.text = itemView.context.getText(item.description)
            bannerIV.setImageResource(item.banner)
            itemView.setOnClickListener {
                Navigation.findNavController(it).navigate(item.onClickNavigateTo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_menu, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}