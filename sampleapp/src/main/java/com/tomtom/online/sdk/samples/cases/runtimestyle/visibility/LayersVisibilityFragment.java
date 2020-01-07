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
package com.tomtom.online.sdk.samples.cases.runtimestyle.visibility;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.ButtonStrategy;
import com.tomtom.online.sdk.samples.cases.ExampleFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

public class LayersVisibilityFragment extends ExampleFragment<LayersVisibilityPresenter> {

    ButtonStrategy buttonPressStrategy = new ButtonStrategy.NoPressAnyButtons();

    @Override
    protected LayersVisibilityPresenter createPresenter() {
        return new LayersVisibilityPresenter();
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.setSelectMode(OptionsButtonsView.SelectMode.MULTI);
        view.addOption(R.string.label_visibility_road_network_btn);
        view.addOption(R.string.label_visibility_woodland_btn);
        view.addOption(R.string.label_visibility_build_up_btn);
    }

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {
        lateInitButtonPressStrategy();
        buttonPressStrategy.manageButtons(oldValues, newValues);
    }

    private void lateInitButtonPressStrategy() {
        if (buttonPressStrategy instanceof ButtonStrategy.NoPressAnyButtons) {
            buttonPressStrategy = new LayersVisibilityButtonsStrategy(presenter);
        }
    }

    @Override
    public boolean isMapRestored() {
        final boolean[] previousState = new boolean[]{
                optionsView.isSelected(0),
                optionsView.isSelected(1),
                optionsView.isSelected(2)
        };
        onChange(previousState, previousState);
        return super.isMapRestored();
    }

}
