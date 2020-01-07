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

package com.tomtom.online.sdk.samples.ktx.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.info_bar_view.view.*

class InfoBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var leftImageView: ImageView
    var subtitleTextView: TextView
    var titleTextView: TextView

    init {
        inflate(context, R.layout.info_bar_view, this)
        titleTextView = info_bar_title
        subtitleTextView = info_bar_subtitle
        leftImageView = left_icon
    }

    fun hideImageView() {
        leftImageView.visibility = View.GONE
    }

    fun showImageView() {
        leftImageView.visibility = View.VISIBLE
    }
}