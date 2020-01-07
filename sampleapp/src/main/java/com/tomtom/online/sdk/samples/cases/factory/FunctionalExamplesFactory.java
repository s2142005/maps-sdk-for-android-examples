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
package com.tomtom.online.sdk.samples.cases.factory;

import androidx.annotation.IdRes;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.fragments.CurrentLocationFragment;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.license.AboutFragment;

import java.util.ArrayList;
import java.util.List;

public class FunctionalExamplesFactory implements ExampleFactory {

    List<ExampleFactory> factories = new ArrayList<ExampleFactory>();

    public FunctionalExamplesFactory() {
        factories.add(new MapsExampleFactory());
        factories.add(new SearchExampleFactory());
        factories.add(new RoutingExampleFactory());
        factories.add(new GeofencingExampleFactory());
        factories.add(new RuntimeStyleExampleFactory());
    }

    public FunctionalExampleFragment create(@IdRes int itemId) {
        for (ExampleFactory factory : factories) {
            FunctionalExampleFragment example = factory.create(itemId);
            if (example != null) {
                return example;
            }
        }
        //Other cases and default case.
        switch (itemId) {
            case R.id.license:
                return new AboutFragment();
            default:
                return new CurrentLocationFragment();
        }
    }


}