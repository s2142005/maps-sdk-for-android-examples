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
import android.widget.TextView

class InfoTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var runnable: Runnable? = null


    fun displayAsToast(msg: String, duration: Long = DEFAULT_DURATION_MILLIS) {
        text = msg
        visibility = View.VISIBLE

        runnable?.let {
            handler.removeCallbacks(runnable)
        }

        runnable = Runnable { visibility = View.GONE }
        handler.postDelayed(runnable, duration)
    }

    fun displayPermanently(msg: String) {
        text = msg
        visibility = View.VISIBLE
    }

    companion object {
        const val DEFAULT_DURATION_MILLIS = 2000L
    }

}