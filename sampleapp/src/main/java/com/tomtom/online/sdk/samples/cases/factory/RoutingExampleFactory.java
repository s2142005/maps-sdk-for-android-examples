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
import com.tomtom.online.sdk.samples.cases.driving.matching.map.MapMatchingFragment;
import com.tomtom.online.sdk.samples.cases.driving.matching.route.RouteMatchingFragment;
import com.tomtom.online.sdk.samples.cases.map.route.RouteCustomizationFragment;
import com.tomtom.online.sdk.samples.cases.route.alternatives.RouteAlternativesFragment;
import com.tomtom.online.sdk.samples.cases.route.avoid.RouteAvoidsFragment;
import com.tomtom.online.sdk.samples.cases.route.avoid.vignettesandareas.RouteAvoidsVignettesAndAreasFragment;
import com.tomtom.online.sdk.samples.cases.route.batch.BatchRouteFragment;
import com.tomtom.online.sdk.samples.cases.route.batch.reachablerange.BatchReachableRangeFragment;
import com.tomtom.online.sdk.samples.cases.route.consumption.RouteConsumptionModelFragment;
import com.tomtom.online.sdk.samples.cases.route.departureandarrivaltime.DepartureAndArrivalTimeFragment;
import com.tomtom.online.sdk.samples.cases.route.maneuvers.ManeuversFragment;
import com.tomtom.online.sdk.samples.cases.route.matrix.MatrixRouteFragment;
import com.tomtom.online.sdk.samples.cases.route.modes.RouteTravelModesFragment;
import com.tomtom.online.sdk.samples.cases.route.reachablerange.ReachableRangeFragment;
import com.tomtom.online.sdk.samples.cases.route.supportingpoints.RouteSupportingPointsFragment;
import com.tomtom.online.sdk.samples.cases.route.types.RouteTypesFragment;
import com.tomtom.online.sdk.samples.cases.route.waypoints.RouteWaypointsFragment;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;

public class RoutingExampleFactory implements ExampleFactory {

    public FunctionalExampleFragment create(@IdRes int itemId) {
        switch (itemId) {
            case R.id.maneuvers_list:
                return new ManeuversFragment();

            case R.id.route_avoids:
                return new RouteAvoidsFragment();

            case R.id.route_avoid_vignettes_and_areas:
                return new RouteAvoidsVignettesAndAreasFragment();

            case R.id.departure_and_arrival_time:
                return new DepartureAndArrivalTimeFragment();

            case R.id.route_travel_modes:
                return new RouteTravelModesFragment();

            case R.id.route_types:
                return new RouteTypesFragment();

            case R.id.route_waypoints:
                return new RouteWaypointsFragment();

            case R.id.route_alternatives:
                return new RouteAlternativesFragment();

            case R.id.consumption_model:
                return new RouteConsumptionModelFragment();

            case R.id.supporting_points:
                return new RouteSupportingPointsFragment();

            case R.id.route_reachable_range:
                return new ReachableRangeFragment();

            case R.id.batch_routing:
                return new BatchRouteFragment();

            case R.id.batch_reachable_range:
                return new BatchReachableRangeFragment();

            case R.id.route_matching:
                return new RouteMatchingFragment();

            case R.id.map_matching:
                return new MapMatchingFragment();

            case R.id.matrix_routing:
                return new MatrixRouteFragment();

            case R.id.route_customization:
                return new RouteCustomizationFragment();

        }
        return null;
    }
}
