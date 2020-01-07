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
package com.tomtom.online.sdk.samples.cases.runtimestyle.sources;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.ButtonStrategy;
import com.tomtom.online.sdk.samples.cases.ExampleFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

public class DynamicSourcesFragment extends ExampleFragment<DynamicSourcesPresenter> {

    ButtonStrategy buttonPressStrategy = new ButtonStrategy.NoPressAnyButtons();

    @Override
    protected DynamicSourcesPresenter createPresenter() {
        return new DynamicSourcesPresenter();
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.setSelectMode(OptionsButtonsView.SelectMode.MULTI);
        view.addOption(R.string.dynamic_map_sources_geojson_source);
        view.addOption(R.string.dynamic_map_sources_image_source);
    }

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {
        lateInitButtonPressStrategy();
        buttonPressStrategy.manageButtons(oldValues, newValues);
    }

    private void lateInitButtonPressStrategy() {
        if (buttonPressStrategy instanceof ButtonStrategy.NoPressAnyButtons) {
            buttonPressStrategy = new DynamicSourcesButtonsStrategy(presenter);
        }
    }

    @Override
    public boolean isMapRestored() {
        final boolean[] previousState = new boolean[]{
                optionsView.isSelected(0),
                optionsView.isSelected(1)
        };
        onChange(previousState, previousState);
        return super.isMapRestored();
    }

}
