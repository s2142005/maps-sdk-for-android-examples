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
package com.tomtom.online.sdk.samples.cases.geofencing.report.utils;

import android.content.Context;

import com.tomtom.online.sdk.geofencing.report.Report;
import com.tomtom.online.sdk.samples.R;

public class OutsideFencesDescriptionProcessor implements FencesDescriptionProcessor {

    @Override
    public boolean isValid(Report report) {
        return !report.getOutside().isEmpty() && report.getInside().isEmpty();
    }

    @Override
    public String getText(Context context, Report report) {
        return context.getResources().getString(R.string.report_service_only_outside_fences,
                GeofencingReportParsingUtils.fenceDetailsToString(report.getOutside()));
    }
}
