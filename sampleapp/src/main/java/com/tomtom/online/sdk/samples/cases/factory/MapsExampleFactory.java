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
import com.tomtom.online.sdk.samples.cases.driving.tracking.ChevronTrackingFragment;
import com.tomtom.online.sdk.samples.cases.map.geopoliticalview.MapGeopoliticalViewFragment;
import com.tomtom.online.sdk.samples.cases.map.langparam.MapLanguageParamFragment;
import com.tomtom.online.sdk.samples.cases.map.layers.layerstypes.MapLayersTypesFragment;
import com.tomtom.online.sdk.samples.cases.map.layers.shapes.ShapesCustomFragment;
import com.tomtom.online.sdk.samples.cases.map.layers.styles.CustomMapStyleFragment;
import com.tomtom.online.sdk.samples.cases.map.layers.tilestypes.MapTilesTypesFragment;
import com.tomtom.online.sdk.samples.cases.map.layers.traffic.TrafficLayersFragment;
import com.tomtom.online.sdk.samples.cases.map.layers.traffic.VectorTrafficLayersFragment;
import com.tomtom.online.sdk.samples.cases.map.manipulation.centering.MapCenteringFragment;
import com.tomtom.online.sdk.samples.cases.map.manipulation.events.MapManipulationEventsFragment;
import com.tomtom.online.sdk.samples.cases.map.manipulation.perspective.MapViewPerspectiveFragment;
import com.tomtom.online.sdk.samples.cases.map.manipulation.uiextensions.MapUiExtensionsFragment;
import com.tomtom.online.sdk.samples.cases.map.markers.MarkerCustomFragment;
import com.tomtom.online.sdk.samples.cases.map.markers.advanced.AdvancedMarkersFragment;
import com.tomtom.online.sdk.samples.cases.map.markers.balloons.BalloonCustomFragment;
import com.tomtom.online.sdk.samples.cases.map.markers.clustering.MarkersClusteringFragment;
import com.tomtom.online.sdk.samples.cases.map.multiplemaps.MultipleMapsFragment;
import com.tomtom.online.sdk.samples.cases.map.staticimage.StaticImageFragment;
import com.tomtom.online.sdk.samples.cases.map.traffic.incident.TrafficIncidentListFragment;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;

public class MapsExampleFactory implements ExampleFactory {

    public FunctionalExampleFragment create(@IdRes int itemId) {

        switch (itemId) {
            case R.id.map_types:
                return new MapTilesTypesFragment();

            case R.id.mapcentering:
                return new MapCenteringFragment();

            case R.id.maplanguageparam:
                return new MapLanguageParamFragment();

            case R.id.mapgeopoliticalview:
                return new MapGeopoliticalViewFragment();

            case R.id.map_custom_style:
                return new CustomMapStyleFragment();

            case R.id.mapmode:
                return new MapViewPerspectiveFragment();

            case R.id.map_events:
                return new MapManipulationEventsFragment();

            case R.id.markers_custom:
                return new MarkerCustomFragment();

            case R.id.advanced_markers:
                return new AdvancedMarkersFragment();

            case R.id.map_layers_types:
                return new MapLayersTypesFragment();

            case R.id.markers_balloons:
                return new BalloonCustomFragment();

            case R.id.custom_shapes:
                return new ShapesCustomFragment();

            case R.id.map_ui_extensions:
                return new MapUiExtensionsFragment();

            case R.id.map_static_image:
                return new StaticImageFragment();

            case R.id.traffic_vector_layer:
                return new VectorTrafficLayersFragment();

            case R.id.traffic_raster_layer:
                return new TrafficLayersFragment();

            case R.id.markers_clustering:
                return new MarkersClusteringFragment();

            case R.id.multiple_chevrons:
                return new ChevronTrackingFragment();

            case R.id.traffic_incident:
                return new TrafficIncidentListFragment();

            case R.id.map_multiple_maps:
                return new MultipleMapsFragment();

        }
        return null;
    }
}
