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
package com.tomtom.online.sdk.samples.ktx.utils.formatter

import android.annotation.SuppressLint
import com.tomtom.online.sdk.search.time.OpeningHours
import com.tomtom.online.sdk.search.time.TimeRange
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

object OpeningHoursFormatter {
    private const val NEW_LINE = "\n"
    private const val OPEN_24H = "Open 24h"

    @SuppressLint("SimpleDateFormat")
    private val openingHoursDateFormatHour = SimpleDateFormat("HH:mm")

    @SuppressLint("SimpleDateFormat")
    private val openingHoursDateFormatWeekday = SimpleDateFormat("EEEE")

    fun format(openingHours: OpeningHours): Pair<String, String> {
        val timeRanges = openingHours.timeRanges
        val builderWeekdays = StringBuilder()
        val builderHours = StringBuilder()
        return if (timeRanges.size == 1) {
            format24h(timeRanges, builderWeekdays, builderHours)
        } else {
            formatRegularHours(timeRanges.toList(), builderWeekdays, builderHours)
        }
    }

    private fun format24h(
        timeRanges: List<TimeRange>,
        builderWeekdays: StringBuilder,
        builderHours: StringBuilder
    ): Pair<String, String> {
        var startDate = DateTime(timeRanges.first().startDate)
        repeat(7) {
            builderWeekdays.append("${formatWeekday(startDate.toDate())}$NEW_LINE")
            builderHours.append("$OPEN_24H$NEW_LINE")
            startDate = startDate.plusDays(1)
        }
        return Pair(builderWeekdays.toString(), builderHours.toString())
    }

    private fun formatRegularHours(
        timeRanges: List<TimeRange>,
        builderWeekdays: StringBuilder,
        builderHours: StringBuilder
    ): Pair<String, String> {
        timeRanges.forEach {
            builderWeekdays.append("${formatWeekday(it.startDate)}$NEW_LINE")
            builderHours.append("${formatHour(it.startDate)} - ${formatHour(it.endDate)}$NEW_LINE")
        }
        return Pair(builderWeekdays.toString(), builderHours.toString())
    }

    private fun formatWeekday(date: Date) = openingHoursDateFormatWeekday.format(date)

    private fun formatHour(date: Date) = openingHoursDateFormatHour.format(date)
}