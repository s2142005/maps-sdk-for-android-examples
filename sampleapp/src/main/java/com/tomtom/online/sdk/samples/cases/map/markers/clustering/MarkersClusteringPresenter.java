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
package com.tomtom.online.sdk.samples.cases.map.markers.clustering;

import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.map.markers.MarkerDrawer;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

public class MarkersClusteringPresenter extends BaseFunctionalExamplePresenter {

    private static final double ZOOM_LEVEL_FOR_EXAMPLE = 7.5;
    private static final int DEFAULT_MAP_PADDING = 0;

    private MarkerDrawer markerDrawer;

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        markerDrawer = new MarkerDrawer(view.getContext(), tomtomMap);

        if (!view.isMapRestored()) {
            createMarkers();
            centerOnMarkers();
        }
        confMapPadding();

    }

    @Override
    public FunctionalExampleModel getModel() {
        return new MarkersClusteringFunctionalExample();
    }

    @Override
    public void cleanup() {
        if (!isMapReady()) {
            return;
        }
        tomtomMap.removeMarkers();
        tomtomMap.getMarkerSettings().setMarkersClustering(false);
        tomtomMap.setPadding(DEFAULT_MAP_PADDING, DEFAULT_MAP_PADDING,
                DEFAULT_MAP_PADDING, DEFAULT_MAP_PADDING);
    }

    private boolean isMapReady() {
        return tomtomMap != null;
    }

    @Override
    protected void confMapPadding() {
        int offsetBig = view.getContext().getResources().getDimensionPixelSize(R.dimen.offset_super_big);

        int actionBarHeight = view.getContext().getResources().getDimensionPixelSize(
                R.dimen.abc_action_bar_default_height_material);

        int padding = actionBarHeight + offsetBig;
        tomtomMap.setPadding(padding, padding, padding, padding);
    }

    private void createMarkers() {

        //tag::doc_enable_markers_clustering[]
        tomtomMap.getMarkerSettings().setMarkersClustering(true);
        //end::doc_enable_markers_clustering[]

        markerDrawer.createRandomMarkersWithClustering(Locations.AMSTERDAM_LOCATION, 90, 0.5f);
        markerDrawer.createRandomMarkersWithClustering(Locations.ROTTERDAM_LOCATION, 150, 0.1f);
    }

    public void centerOnMarkers() {
        tomtomMap.zoomToAllMarkers();
    }

}
