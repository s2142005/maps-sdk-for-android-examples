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

package com.tomtom.online.sdk.samples.ktx

import android.app.Application
import android.os.Environment
import android.util.Log
import com.tomtom.online.sdk.common.util.LogUtils
import com.tomtom.sdk.examples.BuildConfig
import java.io.File

@Suppress("unused")
class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogs()
    }

    private fun initLogs() {
        if (BuildConfig.DEBUG) {
            LogUtils.enableLogs(Log.VERBOSE)
        }
    }

    companion object {
        val LOG_FILE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator
    }

}