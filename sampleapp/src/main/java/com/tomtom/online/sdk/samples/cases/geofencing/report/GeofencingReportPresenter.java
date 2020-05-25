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
package com.tomtom.online.sdk.samples.cases.geofencing.report;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.geofencing.GeofencingException;
import com.tomtom.online.sdk.geofencing.report.Report;
import com.tomtom.online.sdk.geofencing.report.ReportCallback;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.Marker;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;

import java.util.UUID;

public class GeofencingReportPresenter extends BaseFunctionalExamplePresenter implements TomtomMapCallback.OnMarkerDragListener {

    /**
     * This project ID's are related to the API key that you are using.
     * To make this example working, you must create a proper structure for your API Key by running
     * TomTomGeofencingProjectGenerator.sh script which is located in the sampleapp/scripts folder.
     * Script takes an API Key and Admin Key that you generated from
     * https://developer.tomtom.com/geofencing-api-public-preview/geofencing-documentation-configuration-service/register-admin-key
     * and creates two projects with fences like in this example. Use project ID's returned by the
     * script and update this two fields.
     */

    //tag::doc_projects_ID[]
    private static final UUID PROJECT_UUID_TWO_FENCES = UUID.fromString("fcf6d609-550d-49ff-bcdf-02bba08baa28");
    private static final UUID PROJECT_UUID_ONE_FENCE = UUID.fromString("57287023-a968-492c-8473-7e049a606425");
    //end::doc_projects_ID[]

    private static final LatLng DEFAULT_MAP_POSITION = new LatLng(52.372144, 4.899115);
    private static final double DEFAULT_ZOOM_LEVEL = 12D;

    private UUID currentProjectId;

    private GeofencingFencesDrawer fenceDrawer;
    private GeofencingMarkersDrawer markerDrawer;
    private GeofencingReportRequester reportRequester;

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);

        if (!view.isMapRestored()) {
            centerOnDefaultWithOrientationNorth();
        }

        tomtomMap.addOnMarkerDragListener(this);
        fenceDrawer = new GeofencingFencesDrawer(tomtomMap);
        markerDrawer = new GeofencingMarkersDrawer(getContext(), tomtomMap);
        reportRequester = new GeofencingReportRequester(getContext(), resultListener);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        clearMap();

        tomtomMap.removeOnMarkerDragListener(this);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new GeofencingReportFunctionalExample();
    }

    //tag::doc_register_report_listener[]
    private ReportCallback resultListener = new ReportCallback() {

        @Override
        public void onSuccess(@NonNull Report report) {
            markerDrawer.removeFenceMarkers();
            markerDrawer.updateMarkersFromResponse(report);
        }

        @Override
        public void onError(@NonNull GeofencingException error) {
            Toast.makeText(getContext(), R.string.report_service_request_error, Toast.LENGTH_LONG).show();
        }
    };
    //end::doc_register_report_listener[]

    public void drawOneFence() {

        //Clear and set state
        this.currentProjectId = PROJECT_UUID_ONE_FENCE;
        clearMapAndCenterOnDefault();

        //Update fences and markers
        markerDrawer.drawDraggableMarkerAtDefaultPosition();
        fenceDrawer.drawPolygonFence();

        //Request report
        reportRequester.requestReport(DEFAULT_MAP_POSITION, currentProjectId);
    }

    public void drawTwoFences() {

        //Clear and set state
        this.currentProjectId = PROJECT_UUID_TWO_FENCES;
        clearMapAndCenterOnDefault();

        //Update fences and markers
        markerDrawer.drawDraggableMarkerAtDefaultPosition();
        fenceDrawer.drawPolygonFence();
        fenceDrawer.drawCircularFence();

        //Request report
        reportRequester.requestReport(DEFAULT_MAP_POSITION, currentProjectId);
    }

    @Override
    public void onStartDragging(@NonNull Marker marker) {
        markerDrawer.deselectDraggableMarker();
    }

    @Override
    public void onStopDragging(@NonNull Marker marker) {
        reportRequester.requestReport(marker.getPosition(), currentProjectId);
    }

    @Override
    public void onDragging(@NonNull Marker marker) {

    }

    public void setCurrentProjectId(UUID newProjectId) {
        currentProjectId = newProjectId;
    }

    public UUID getCurrentProjectId() {
        return currentProjectId;
    }

    private void clearMap() {
        tomtomMap.getOverlaySettings().removeOverlays();
        tomtomMap.getMarkerSettings().removeMarkers();
    }

    private void clearMapAndCenterOnDefault() {
        clearMap();
        centerOnDefaultWithOrientationNorth();
    }

    private void centerOnDefaultWithOrientationNorth() {
        tomtomMap.centerOn(DEFAULT_MAP_POSITION.getLatitude(), DEFAULT_MAP_POSITION.getLongitude(),
                DEFAULT_ZOOM_LEVEL, MapConstants.ORIENTATION_NORTH);
    }
}
