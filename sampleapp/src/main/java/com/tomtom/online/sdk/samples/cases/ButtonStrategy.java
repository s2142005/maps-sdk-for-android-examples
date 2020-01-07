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
package com.tomtom.online.sdk.samples.cases;

import timber.log.Timber;

public abstract class ButtonStrategy {

    public abstract void manageButtons(boolean[] oldValues, boolean[] newValues);

    protected int getChangedButtonId(boolean[] oldValues, boolean[] newValues) {
        for (int i = 0; i < oldValues.length; i++) {
            if (oldValues[i] != newValues[i]) {
                return i;
            }
        }
        return -1;
    }

    public static class NoPressAnyButtons extends ButtonStrategy {
        @Override
        public void manageButtons(boolean[] oldValues, boolean[] newValues) {
            Timber.w("Buttons don't works because default strategy is used for manage pressing buttons.");
        }
    }

}
