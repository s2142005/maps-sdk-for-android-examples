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

import com.tomtom.online.sdk.samples.cases.ButtonStrategy;

import timber.log.Timber;

public class DynamicSourcesButtonsStrategy extends ButtonStrategy {

    private final DynamicSourcesPresenter presenter;

    DynamicSourcesButtonsStrategy(DynamicSourcesPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void manageButtons(boolean[] oldValues, boolean[] newValues) {
        int changedButtonId = getChangedButtonId(oldValues, newValues);
        switch (changedButtonId) {
            case 0:
                presenter.toggleCustomGeoJsonInStyle(newValues[changedButtonId]);
                break;
            case 1:
                presenter.toggleCustomImageInStyle(newValues[changedButtonId]);
                break;
            default:
                Timber.d("Unregistered button pressed.");
        }
    }

}