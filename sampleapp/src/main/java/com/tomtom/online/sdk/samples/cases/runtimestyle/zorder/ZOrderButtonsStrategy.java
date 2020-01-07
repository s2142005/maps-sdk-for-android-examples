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

import com.tomtom.online.sdk.samples.cases.ButtonStrategy;

public class ZOrderButtonsStrategy extends ButtonStrategy {

    private final ZOrderPresenter presenter;

    ZOrderButtonsStrategy(ZOrderPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void manageButtons(boolean[] oldValues, boolean[] newValues) {
        if (newValues[0]) {
            presenter.moveImagesLayersToFront();
        } else if (newValues[1]) {
            presenter.moveGeoJsonLayerToFront();
        } else if (newValues[2]) {
            presenter.moveRoadsLayerToFront();
        }
    }
}
