/**
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.cases.search;

import androidx.annotation.VisibleForTesting;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.common.location.LatLngBias;
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification;
import com.tomtom.online.sdk.search.fuzzy.FuzzyLocationDescriptor;
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchEngineDescriptor;

public class CategoriesSearchFragmentPresenter extends SearchFragmentPresenter {

    public CategoriesSearchFragmentPresenter(final SearchView presenterListener) {
        super(presenterListener);
    }

    @Override
    public void performSearch(final String text) {

        searchView.disableToggleButtons();

        LatLng position = getLastKnownPosition();
        final FuzzySearchSpecification searchSpecification = getSearchQuery(text, position);
        performSearch(searchSpecification);
    }

    @VisibleForTesting
    FuzzySearchSpecification getSearchQuery(String text, LatLng position) {
        //tag::doc_create_category_specification_plain_text[]
        FuzzySearchEngineDescriptor fuzzySearchEngineDescriptor = new FuzzySearchEngineDescriptor.Builder()
                .typeAhead(true)
                .category(true)
                .build();
        FuzzyLocationDescriptor fuzzyLocationDescriptor = new FuzzyLocationDescriptor.Builder()
                .positionBias(new LatLngBias(position, STANDARD_RADIUS))
                .build();
        return new FuzzySearchSpecification.Builder(text)
                .searchEngineDescriptor(fuzzySearchEngineDescriptor)
                .locationDescriptor(fuzzyLocationDescriptor)
                .build();
        //end::doc_create_category_specification_plain_text[]
    }
}
