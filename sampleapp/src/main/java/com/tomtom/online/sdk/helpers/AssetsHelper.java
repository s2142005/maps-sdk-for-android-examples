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
package com.tomtom.online.sdk.helpers;

import android.content.Context;

import com.tomtom.online.sdk.common.util.AssetUtils;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

/**
 * Wraps the AssetUtils to handle all checked exceptions and propagate runtime one.
 * It is in cases if we know that resources should be available and
 * we don't want to make source code dirty with explicitly handled exceptions.
 */
public class AssetsHelper {

    @NotNull
    public static String getAssetFileContent(Context context, String path) {
        try {
            return AssetUtils.getAssetFile(context, path);
        } catch (Exception ex) {
            return rethrowWithMessage();
        }
    }

    @NotNull
    private static String rethrowWithMessage() {
        Timber.w("Failed to load file from assets");
        throw new RuntimeException("Failed to load file from assets");
    }
}
