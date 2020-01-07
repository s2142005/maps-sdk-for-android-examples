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

import com.tomtom.online.sdk.map.style.layers.Visibility;
import com.tomtom.online.sdk.samples.cases.ButtonStrategy;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class LayersVisibilityButtonsStrategy extends ButtonStrategy {

    private final LayersVisibilityPresenter presenter;

    LayersVisibilityButtonsStrategy(LayersVisibilityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void manageButtons(boolean[] oldValues, boolean[] newValues) {
        int changedButtonId = getChangedButtonId(oldValues, newValues);
        switch (changedButtonId) {
            case 0:
                presenter.setRoadNetworkLayerVisibility(isLayerVisible(changedButtonId, newValues));
                break;
            case 1:
                presenter.setWoodlandLayerVisibility(isLayerVisible(changedButtonId, newValues));
                break;
            case 2:
                presenter.setBuiltUpLayerVisibility(isLayerVisible(changedButtonId, newValues));
            default:
                Timber.d("Unregistered button pressed.");
        }
    }

    @NotNull
    private Visibility isLayerVisible(int i, boolean[] newValues) {
        return newValues[i] ? Visibility.VISIBLE : Visibility.NONE;
    }

}