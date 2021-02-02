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
package com.tomtom.online.sdk.samples.ktx.utils.text

import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView

object TextFormatter {

    fun applyColor(text: String, color: Int): SpannableString {
        val spannableStr = SpannableString(text)
        val backgroundColorSpan = ForegroundColorSpan(color)
        spannableStr.setSpan(
            backgroundColorSpan,
            0, spannableStr.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        return spannableStr
    }

    fun applyStyle(text: String, styleSpan: StyleSpan): SpannableString {
        val spannableStr = SpannableString(text)
        spannableStr.setSpan(
            styleSpan,
            0, spannableStr.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        return spannableStr
    }
}