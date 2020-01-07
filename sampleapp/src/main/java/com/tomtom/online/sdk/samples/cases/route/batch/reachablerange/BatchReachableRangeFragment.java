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
package com.tomtom.online.sdk.samples.cases.route.batch.reachablerange;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.MultiRoutesRoutingUiListener;
import com.tomtom.online.sdk.samples.cases.RoutePlannerFragment;

public class BatchReachableRangeFragment extends RoutePlannerFragment<BatchReachableRangePresenter> implements MultiRoutesRoutingUiListener {

    @Override
    protected BatchReachableRangePresenter createPresenter() {
        return new BatchReachableRangePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfoBarView().show();
        getInfoBarView().setLeftText(getString(R.string.label_init_batch_reachablerange_info));
        getInfoBarView().setLeftIcon(R.drawable.warning_white);
    }

    @Override
    public void updateTextOnCurrentRouteBar(String left, String right) {
        getInfoBarView().setLeftIcon(R.drawable.info);
        getInfoBarView().setLeftText(left);
        getInfoBarView().setRightText(right);
    }

    @Override
    public void repeatRequestWhenNotFinished() {
        // Not needed for this Example.
    }
}
