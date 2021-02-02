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

public class FuzzySearchFragmentPresenter extends SearchFragmentPresenter {

    public FuzzySearchFragmentPresenter(final SearchView presenterListener) {
        super(presenterListener);
    }

    @Override
    public void performSearch(final String text) {
        // No such action.
    }

    public void performFuzzySearch(String query, int maxLevel) {
        LatLng position = getLastKnownPosition();
        final FuzzySearchSpecification searchSpecification = getFuzzySearchSpecification(query, maxLevel, position);
        performSearch(searchSpecification);
    }

    @VisibleForTesting
    FuzzySearchSpecification getFuzzySearchSpecification(String term, int maxLevel, LatLng position) {
        //tag::doc_create_fuzzy_search_specification[]
        FuzzySearchEngineDescriptor fuzzySearchEngineDescriptor = new FuzzySearchEngineDescriptor.Builder()
                .minFuzzyLevel(1)
                .maxFuzzyLevel(maxLevel)
                .build();

        FuzzyLocationDescriptor fuzzyLocationDescriptor = new FuzzyLocationDescriptor.Builder()
                .positionBias(new LatLngBias(position))
                .build();

        return new FuzzySearchSpecification.Builder(term)
                .searchEngineDescriptor(fuzzySearchEngineDescriptor)
                .locationDescriptor(fuzzyLocationDescriptor)
                .build();
        //end::doc_create_fuzzy_search_specification[]
    }

    public void performNonFuzzySearch(String term) {
        LatLng position = getLastKnownPosition();
        final FuzzySearchSpecification searchSpecification = getSearchSpecificationForNonFuzzySearch(term, position);
        performSearch(searchSpecification);
    }

    @VisibleForTesting
    FuzzySearchSpecification getSearchSpecificationForNonFuzzySearch(String query, LatLng position) {
        FuzzyLocationDescriptor fuzzyLocationDescriptor = new FuzzyLocationDescriptor.Builder()
                .positionBias(new LatLngBias(position))
                .build();
        return
                //tag::doc_create_standard_search_query[]
                new FuzzySearchSpecification.Builder(query)
                        .locationDescriptor(fuzzyLocationDescriptor)
                        .build();
        //end::doc_create_standard_search_query[]
    }
}