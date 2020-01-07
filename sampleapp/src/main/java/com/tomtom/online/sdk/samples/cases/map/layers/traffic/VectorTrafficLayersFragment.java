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
package com.tomtom.online.sdk.samples.cases.map.layers.traffic;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.ButtonStrategy;
import com.tomtom.online.sdk.samples.cases.ExampleFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

public class VectorTrafficLayersFragment extends ExampleFragment<VectorTrafficLayersPresenter> {

    ButtonStrategy buttonPressStrategy = new ButtonStrategy.NoPressAnyButtons();

    @Override
    protected VectorTrafficLayersPresenter createPresenter() {
        return new VectorTrafficLayersPresenter();
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.setSelectMode(OptionsButtonsView.SelectMode.MULTI);
        view.addOption(getString(R.string.traffic_incidents));
        view.addOption(getString(R.string.traffic_flow));
        view.addOption(getString(R.string.traffic_off_btn));
        optionsView.selectLast();
        if (presenter instanceof TrafficPresenter){
            buttonPressStrategy = new LastButtonPressedByDefault(presenter, optionsView);
        }
    }

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {
        buttonPressStrategy.manageButtons(oldValues, newValues);
    }

    @Override
    public boolean isMapRestored() {
        return super.isMapRestored();
    }

}
