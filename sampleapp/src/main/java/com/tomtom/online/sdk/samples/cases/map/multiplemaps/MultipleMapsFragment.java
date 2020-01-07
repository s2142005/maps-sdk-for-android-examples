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
package com.tomtom.online.sdk.samples.cases.map.multiplemaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.ExampleFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

public class MultipleMapsFragment extends ExampleFragment<MultipleMapsPresenter> {

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {

    }

    @Override
    protected MultipleMapsPresenter createPresenter() {
        return new MultipleMapsPresenter();
    }

    @Override
    protected void onOptionsButtonsView(final OptionsButtonsView view) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isRestored = savedInstanceState.getBoolean(MAP_RESTORE_KEY, false);
        }
        return inflater.inflate(R.layout.multiple_maps_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment miniMapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mini_map_fragment);

        miniMapFragment.getAsyncMap(tomtomMap -> presenter.bindMiniMap(tomtomMap));
        //Make sure that mini map is drawn on top of the main map
        //If not set, the mini map may be invisible on old devices
        //With low Android version
        miniMapFragment.setZOrderMediaOverlay(true);
    }

}
