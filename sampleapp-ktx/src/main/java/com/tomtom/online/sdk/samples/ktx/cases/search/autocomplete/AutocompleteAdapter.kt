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

package com.tomtom.online.sdk.samples.ktx.cases.search.autocomplete

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomtom.online.sdk.search.data.autocomplete.response.Segment
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.list_item_autocomplete.view.*

class AutocompleteAdapter(private val onAutocompleteListener: OnAutocompleteClickListener) :
    RecyclerView.Adapter<AutocompleteViewHolder>() {

    private val autocompleteSuggestions: MutableList<Segment> = mutableListOf()

    fun updateData(foundAutocompletes: List<Segment>) {
        this.autocompleteSuggestions.clear()
        this.autocompleteSuggestions.addAll(foundAutocompletes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutocompleteViewHolder =
        AutocompleteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_autocomplete, parent, false)
        )

    override fun getItemCount(): Int = autocompleteSuggestions.size

    override fun onBindViewHolder(holder: AutocompleteViewHolder, position: Int) {
        holder.bind(autocompleteSuggestions[position], onAutocompleteListener)
    }
}

interface OnAutocompleteClickListener {
    fun onAutocompleteClicked(segment: Segment)
}

class AutocompleteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(autocompleteSuggestion: Segment, onAutocompleteClickListener: OnAutocompleteClickListener) {
        itemView.autocomplete_name.text = autocompleteSuggestion.value
        itemView.autocomplete_type.text = autocompleteSuggestion.type.toString()
        itemView.setOnClickListener {
            onAutocompleteClickListener.onAutocompleteClicked(autocompleteSuggestion)
        }
    }
}
