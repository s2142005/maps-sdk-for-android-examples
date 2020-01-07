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
package com.tomtom.online.sdk.samples.cases.search.revgeo.polygon;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.tomtom.online.sdk.common.util.LogUtils;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.ExampleFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

public class ReverseGeoPolygonFragment extends ExampleFragment<ReverseGeoPolygonPresenter> implements ProgressDisplayable {

    public static final int FIRST_INDEX = 0;
    private RevGeoProgressDialog revGeoProgressDialog = new RevGeoProgressDialog();
    private String REVGEO_ENTITY_TYPE_KEY = "REVGEO_ENTITY_TYPE_KEY";
    private String REV_GEO_DIALOG_TAG = "REV_GEO_DIALOG";

    @Override
    protected ReverseGeoPolygonPresenter createPresenter() {
        return new ReverseGeoPolygonPresenter();
    }

    @Override
    public void showInProgressDialog() {
        LogUtils.checkMainThread();
        optionsView.setEnabled(false);
        revGeoProgressDialog.show(getFragmentManager(), REV_GEO_DIALOG_TAG);
    }

    @Override
    public void hideInProgressDialog() {
        LogUtils.checkMainThread();
        revGeoProgressDialog.dismiss();
        optionsView.setEnabled(true);
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.addOption(R.string.menu_polygon_revgeo_country);
        view.addOption(R.string.menu_polygon_revgeo_municipality);
        view.selectItem(FIRST_INDEX, true);
    }

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {
        if (newValues[0]) {
            presenter.setActiveTypeToCountry();
        } else if (newValues[1]) {
            presenter.setActiveTypeToMunicipality();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setActiveEntityType(savedInstanceState.getString(REVGEO_ENTITY_TYPE_KEY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(REVGEO_ENTITY_TYPE_KEY, presenter.getActiveEntityType());
    }
}
