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
package com.tomtom.online.sdk.samples.cases.runtimestyle.zorder;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.ButtonStrategy;
import com.tomtom.online.sdk.samples.cases.RoutePlannerFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

public class ZOrderFragment extends RoutePlannerFragment<ZOrderPresenter> {

    ButtonStrategy buttonPressStrategy = new ButtonStrategy.NoPressAnyButtons();

    @Override
    protected ZOrderPresenter createPresenter() {
        return new ZOrderPresenter(this);
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.addOption(R.string.label_btn_images_layer);
        view.addOption(R.string.label_btn_geojson_layer);
        view.addOption(R.string.label_btn_roads_layer);
        optionsView.selectItem(0, true);
    }

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {
        lateInitButtonPressStrategy();
        buttonPressStrategy.manageButtons(oldValues, newValues);
    }

    @Override
    public void repeatRequestWhenNotFinished() {
        // Not needed to re-query for the route.
    }

    private void lateInitButtonPressStrategy() {
        if (buttonPressStrategy instanceof ButtonStrategy.NoPressAnyButtons) {
            buttonPressStrategy = new ZOrderButtonsStrategy(presenter);
        }
    }
}
