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
import com.tomtom.online.sdk.samples.cases.search.CategoriesSearchFragment;
import com.tomtom.online.sdk.samples.cases.search.FuzzySearchFragment;
import com.tomtom.online.sdk.samples.cases.search.LanguageSelectorSearchFragment;
import com.tomtom.online.sdk.samples.cases.search.ReverseGeocodingFragment;
import com.tomtom.online.sdk.samples.cases.search.SearchAlongRouteFragment;
import com.tomtom.online.sdk.samples.cases.search.SearchFragment;
import com.tomtom.online.sdk.samples.cases.search.TypeAheadSearchFragment;
import com.tomtom.online.sdk.samples.cases.search.adp.AdditionalDataSearchFragment;
import com.tomtom.online.sdk.samples.cases.search.batch.BatchSearchFragment;
import com.tomtom.online.sdk.samples.cases.search.entrypoints.EntryPointsSearchFragment;
import com.tomtom.online.sdk.samples.cases.search.geometry.GeometrySearchFragment;
import com.tomtom.online.sdk.samples.cases.search.revgeo.polygon.ReverseGeoPolygonFragment;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;

class SearchExampleFactory implements ExampleFactory {

    public FunctionalExampleFragment create(@IdRes int itemId) {
        switch (itemId) {
            case R.id.address_search:
                return new SearchFragment();

            case R.id.category_search:
                return new CategoriesSearchFragment();

            case R.id.language_selector_search:
                return new LanguageSelectorSearchFragment();

            case R.id.fuzzy_search:
                return new FuzzySearchFragment();

            case R.id.typeahead_search:
                return new TypeAheadSearchFragment();

            case R.id.reverse_geocoding:
                return new ReverseGeocodingFragment();

            case R.id.batch_search:
                return new BatchSearchFragment();

            case R.id.search_along_route:
                return new SearchAlongRouteFragment();

            case R.id.geometry_search:
                return new GeometrySearchFragment();

            case R.id.entry_points:
                return new EntryPointsSearchFragment();

            case R.id.additional_data:
                return new AdditionalDataSearchFragment();

            case R.id.polygon_reverse_geocoding:
                return new ReverseGeoPolygonFragment();
        }
        return null;
    }
}
