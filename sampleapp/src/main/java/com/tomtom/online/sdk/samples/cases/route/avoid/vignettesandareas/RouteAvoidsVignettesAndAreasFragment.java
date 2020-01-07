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
package com.tomtom.online.sdk.samples.cases.route.avoid.vignettesandareas;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.MultiRoutesRoutingUiListener;
import com.tomtom.online.sdk.samples.cases.RoutePlannerFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

public class RouteAvoidsVignettesAndAreasFragment extends RoutePlannerFragment<RouteAvoidsVignettesAndAreasPresenter> implements MultiRoutesRoutingUiListener {

    @Override
    protected RouteAvoidsVignettesAndAreasPresenter createPresenter() {
        return new RouteAvoidsVignettesAndAreasPresenter(this);
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.addOption(R.string.btn_text_no_avoids);
        view.addOption(R.string.btn_text_avoid_vignettes);
        view.addOption(R.string.btn_text_avoid_area);
    }

    @Override
    public void onChange(final boolean[] oldValues, final boolean[] newValues) {
        presenter.centerOnDefaultLocation();
        if (newValues[0]) {
            presenter.startRoutingNoAvoids();
        } else if (newValues[1]) {
            presenter.startRoutingAvoidVignettes();
        } else if (newValues[2]) {
            presenter.startRoutingAvoidArea();
        }
    }

    @Override
    public void updateTextOnCurrentRouteBar(String left, String right) {
        getInfoBarView().show();
        getInfoBarView().setLeftText(left);
        getInfoBarView().setRightText(right);
    }

}
