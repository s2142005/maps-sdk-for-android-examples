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
package com.tomtom.online.sdk.samples.ktx.utils.csv

import android.content.Context
import android.location.Location
import android.os.Environment
import com.google.common.io.Closer
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader


private const val TIME_COL_IDX = 0
private const val PROVIDER_COL_IDX = 1
private const val LATITUDE_COL_IDX = 2
private const val LONGITUDE_COL_IDX = 3
private const val ACCURACY_COL_IDX = 4
private const val BEARING_COL_IDX = 5
private const val SPEED_COL_IDX = 7
private const val ALTITUDE_COL_IDX = 9
private const val CSV_SEPARATOR = ","
private const val DEFAULT_HEADER_LINES = 0


fun parseFromAssets(
        context: Context,
        assetFileName: String,
        numberOfHeaderLines: Int = DEFAULT_HEADER_LINES
): List<Location> {

    val result = ArrayList<Location>()
    val closer = Closer.create()

    try {

        val fileStream = closer.register(context.assets.open(assetFileName))
        val fileReader = BufferedReader(InputStreamReader(fileStream))

        // Skip header lines
        for (i in 0..numberOfHeaderLines) {
            fileReader.readLine()
        }

        // Read and parse gps data
        var fileLine = fileReader.readLine()
        while (fileLine != null) {
            result.add(parseLine(fileLine))
            fileLine = fileReader.readLine()
        }
    } catch (e: IOException) {
        closer.rethrow(e)
    } finally {
        closer.close()
    }

    return result
}

private fun parseLine(line: String): Location {

    val tokens = line.split(CSV_SEPARATOR)
    if (tokens.isEmpty()) {
        throw IllegalArgumentException("CSV file line format incorrect")
    }

    val location = Location(tokens[PROVIDER_COL_IDX])
    location.time = tokens[TIME_COL_IDX].toLong()
    location.latitude = tokens[LATITUDE_COL_IDX].toDouble()
    location.longitude = tokens[LONGITUDE_COL_IDX].toDouble()
    location.accuracy = tokens[ACCURACY_COL_IDX].toFloat()
    location.bearing = tokens[BEARING_COL_IDX].toFloat()
    location.speed = tokens[SPEED_COL_IDX].toFloat()
    location.altitude = tokens[ALTITUDE_COL_IDX].toDouble()

    return location
}
