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
package com.tomtom.online.sdk.samples.cases.search.entrypoints;

import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchDetails;
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcome;
import com.tomtom.online.sdk.search.information.EntryPoint;

class EntryPointsMarkerDrawer {

    private final String markerBalloonText;
    private TomtomMap tomtomMap;

    EntryPointsMarkerDrawer(TomtomMap tomtomMap, String markerBalloonText) {
        this.tomtomMap = tomtomMap;
        this.markerBalloonText = markerBalloonText;
    }

    private void addMarker(FuzzySearchDetails fuzzySearchDetails) {
        tomtomMap.addMarker(new MarkerBuilder(fuzzySearchDetails.getPosition())
                .markerBalloon(new SimpleMarkerBalloon(fuzzySearchDetails.getPoi().getName()))
        );
    }

    //tag::doc_entry_points_add_marker[]
    private void addMarkerWithIcon(EntryPoint entryPoint, SimpleMarkerBalloon balloon, Icon icon) {
        tomtomMap.addMarker(new MarkerBuilder(entryPoint.getPosition())
                .markerBalloon(balloon)
                .icon(icon));
    }
    //end::doc_entry_points_add_marker[]


    void handleResultsFromFuzzy(FuzzyOutcome fuzzyOutcome, Icon icon) {
        FuzzySearchDetails fuzzySearchDetails = fuzzyOutcome.getFuzzyDetailsList().get(0);

        addMarker(fuzzySearchDetails);

        //tag::doc_entry_points_search_request[]
        for (EntryPoint entryPoint : fuzzySearchDetails.getEntryPoints()) {
            SimpleMarkerBalloon markerBalloon = new SimpleMarkerBalloon(
                    String.format(markerBalloonText, entryPoint.getType().toString().toLowerCase()));

            addMarkerWithIcon(entryPoint, markerBalloon, icon);
        }
        //end::doc_entry_points_search_request[]

        tomtomMap.getMarkerSettings().zoomToAllMarkers();
    }
}
