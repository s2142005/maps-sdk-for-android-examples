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
package com.tomtom.online.sdk.samples.activities;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.common.base.Optional;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.ApiKeyType;
import com.tomtom.online.sdk.map.BaseGpsPositionIndicator;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.GpsIndicator;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.MapProperties;
import com.tomtom.online.sdk.map.MapView;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.BuildConfig;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.SampleApp;
import com.tomtom.online.sdk.samples.fragments.CurrentLocationFragment;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.BackButtonDelegate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

//tag::doc_implement_tt_interfaces[]
public class FunctionalExamplesActivity extends AppCompatActivity
        implements BackButtonDelegate.BackButtonDelegateCallback, ActionBarModel {
    //end::doc_implement_tt_interfaces[]

    public static final String CURRENT_EXAMPLE_KEY = "CURRENT_EXAMPLE";

    public static final int EMPTY_EXAM = 1000;
    private int currentExampleId = EMPTY_EXAM;

    private BackButtonDelegate backButtonDelegate;
    private FunctionalExamplesNavigationManager functionalExamplesNavigationManager;
    private MapView mapView;

    //tag::doc_implement_on_map_ready_callback[]
    private final OnMapReadyCallback onMapReadyCallback =
            new OnMapReadyCallback() {
                @Override
                public void onMapReady(TomtomMap map) {
                    //Map is ready here
                    tomtomMap = map;
                    tomtomMap.setMyLocationEnabled(true);
                    tomtomMap.collectLogsToFile(SampleApp.LOG_FILE_PATH);
                }
            };
    //end::doc_implement_on_map_ready_callback[]

    //Just for documentation - this callback is not registered.
    @SuppressWarnings("unused")
    private final OnMapReadyCallback onMapReadyCallbackSaveLogs = new OnMapReadyCallback() {
        //tag::doc_collect_logs_to_file_in_onready_callback[]
        @Override
        public void onMapReady(@NonNull TomtomMap tomtomMap) {
            tomtomMap.collectLogsToFile(SampleApp.LOG_FILE_PATH);
        }
        //end::doc_collect_logs_to_file_in_onready_callback[]
    };

    private TomtomMap tomtomMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate()");
        super.onCreate(savedInstanceState);
        inflateActivity();

        mapView = new MapView(getApplicationContext());
        mapView.addOnMapReadyCallback(onMapReadyCallback);
        mapView.setId(R.id.map_view);

        FrameLayout frameLayout = findViewById(R.id.map_container);
        frameLayout.addView(mapView);

        Timber.d("Phone language " + Locale.getDefault().getLanguage());
        restoreState(savedInstanceState);
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setCurrentExample(new CurrentLocationFragment(), EMPTY_EXAM);
            return;
        }

        currentExampleId = savedInstanceState.getInt(CURRENT_EXAMPLE_KEY);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.functional_example_control_container);
        if (fragment != null) {
            setCurrentExample((FunctionalExampleFragment) fragment, currentExampleId);
        }
    }


    private void inflateActivity() {
        setContentView(R.layout.activity_main);
        initManagers();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        mapView.addOnMapReadyCallback(map -> map.getUiSettings().setCopyrightsViewAdapter(() -> this));
        mapView.addOnMapReadyCallback(map -> map.getUiSettings().getCurrentLocationView().setCurrentLocationViewAdapter(() -> this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_EXAMPLE_KEY, currentExampleId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (!backButtonDelegate.onBackPressed(drawer)) {
            // The event was not consumed by the delegate
            // Then proceed with standard procedure.
            super.onBackPressed();
        }
    }

    @Override
    public boolean exitFromFunctionalExample(FunctionalExampleFragment newFragment, int newExampleId) {
        if (!closePreviousFunctionalExample()) {
            return false;
        }

        if (currentExampleId == EMPTY_EXAM) {
            super.onBackPressed();
        }

        setCurrentExample(newFragment, EMPTY_EXAM);
        return true;
    }

    public boolean closePreviousFunctionalExample() {
        FunctionalExampleFragment currentFragment = (FunctionalExampleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.functional_example_control_container);
        return currentFragment.onBackPressed();
    }

    @Override
    public boolean isManeuversOrSearchFragmentOnTop() {
        return false;
    }

    private void initManagers() {
        functionalExamplesNavigationManager = new FunctionalExamplesNavigationManager(this);
        backButtonDelegate = new BackButtonDelegate(this);
    }

    private void initViews() {
        initDrawerAndToolbar();
        initNavigationView();
    }

    private void initNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(functionalExamplesNavigationManager);
        navigationView.setKeepScreenOn(true);
        updateAppVersion(navigationView);
    }

    private void initDrawerAndToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = initToggleForNavigationDrawer(toolbar, drawer);
        drawer.addDrawerListener(toggle);
    }

    @NonNull
    private ActionBarDrawerToggle initToggleForNavigationDrawer(Toolbar toolbar, final DrawerLayout drawer) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        };

        toggle.setDrawerIndicatorEnabled(true);
        toggle.setToolbarNavigationClickListener(new ToolbarNavigationClickListener(drawer));

        toggle.syncState();
        return toggle;
    }

    static class ToolbarNavigationClickListener implements View.OnClickListener {
        private final DrawerLayout drawer;

        public ToolbarNavigationClickListener(DrawerLayout drawer) {

            this.drawer = drawer;
        }

        @Override
        public void onClick(View view) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        }
    }

    @Override
    public void setActionBarTitle(int titleId) {
        getSupportActionBar().setTitle(getString(titleId));
    }

    @Override
    public void setActionBarSubtitle(int subtitleId) {
        getSupportActionBar().setSubtitle(getString(subtitleId));
    }

    public void setCurrentExample(FunctionalExampleFragment currentExample, int itemId) {
        currentExampleId = itemId;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.functional_example_control_container, (Fragment) currentExample, currentExample.getFragmentTag())
                .commit();
    }

    private void updateAppVersion(NavigationView navigationView) {
        TextView appVersionTextView = navigationView.getHeaderView(0).findViewById(R.id.app_version);
        appVersionTextView.setText(BuildConfig.VERSION_NAME);
    }

    //tag::doc_map_permissions[]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //end::doc_map_permissions[]

    @SuppressWarnings("unused")
    private void initMap() {
        int mapFragmentId = 0;
        //tag::doc_obtain_fragment_reference[]
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(mapFragmentId);
        //end::doc_obtain_fragment_reference[]
        //tag::doc_initialise_map[]
        mapFragment.getAsyncMap(onMapReadyCallback);
        //end::doc_initialise_map[]
    }

    @SuppressWarnings("unused")
    private void initMapProperties() {
        //tag::doc_initial_map_properties[]
        Map<ApiKeyType, String> keysMap = new HashMap<>();
        keysMap.put(ApiKeyType.MAPS_API_KEY, "online-maps-key");
        CameraPosition cameraPosition = CameraPosition.builder()
                .focusPosition(new LatLng(12.34, 23.45))
                .zoom(10.0)
                .bearing(24.0)
                .build();
        MapProperties mapProperties = new MapProperties.Builder()
                .customStyleUri("asset://styles/style.json")
                .backgroundColor(Color.BLUE)
                .keys(keysMap)
                .cameraPosition(cameraPosition)
                .build();
        //end::doc_initial_map_properties[]
    }

    /**
     * Custom GPS position indicator that forces accuracy to 0.
     */
    //tag::doc_custom_gps_position_indicator
    static class CustomGpsPositionIndicator extends BaseGpsPositionIndicator {
        private static final long serialVersionUID = -2164040010108911434L;

        //To use this class, call setCustomGpsPositionIndicator on TomtomMap:
        //tomtomMap.setGpsPositionIndicator(new CustomGpsPositionIndicator());

        @Override
        public void setLocation(Location location) {
            setLocation(new LatLng(location), 0.0, 0.0, location.getTime());
        }

        @Override
        public void setLocation(LatLng latLng, double bearingInDegrees, double accuracyInMeters, long timeInMillis) {
            super.show();
            super.setDimmed(false);
            super.setLocation(latLng, 0.0, 0.0, timeInMillis);
        }
    }
    //end::doc_custom_gps_position_indicator

    @SuppressWarnings("unused")
    private void changeGPSIndicatorRadiusColor() {
        new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull TomtomMap tomtomMap) {
                final int COLOR_RGBA = Color.argb(128, 128, 128, 128);
                GpsIndicator gpsIndicator = tomtomMap.getGpsPositionIndicator().get();
                //tag::doc_obtain_gps_indicator[]
                Optional<GpsIndicator> indicatorOptional = tomtomMap.getGpsPositionIndicator();
                if (indicatorOptional.isPresent()) {
                    gpsIndicator = indicatorOptional.get();
                }
                //end::doc_obtain_gps_indicator[]

                //tag::doc_set_gps_indicator_active_radius[]
                gpsIndicator.setInaccuracyAreaColor(COLOR_RGBA);
                //end::doc_set_gps_indicator_active_radius[]

                //tag::doc_set_gps_indicator_inactive_radius[]
                gpsIndicator.setDimmedInaccuracyAreaColor(COLOR_RGBA);
                //end::doc_set_gps_indicator_inactive_radius[]
            }
        };
    }
}
