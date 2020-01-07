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
package com.tomtom.online.sdk.samples.cases.geofencing.report;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.ExampleFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

import java.util.UUID;

public class GeofencingReportFragment extends ExampleFragment<GeofencingReportPresenter> {

    private static final String PROJECT_UUID_BUNDLE_KEY = "PROJECT_UUID_BUNDLE_KEY";

    @Override
    protected GeofencingReportPresenter createPresenter() {
        return new GeofencingReportPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setCurrentProjectId((UUID) savedInstanceState.getSerializable(PROJECT_UUID_BUNDLE_KEY));
        }
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.addOption(R.string.btn_one_fence);
        view.addOption(R.string.btn_two_fences);
    }

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {
        if (newValues[0]) {
            presenter.drawOneFence();
        } else if (newValues[1]) {
            presenter.drawTwoFences();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PROJECT_UUID_BUNDLE_KEY, presenter.getCurrentProjectId());
    }
}
