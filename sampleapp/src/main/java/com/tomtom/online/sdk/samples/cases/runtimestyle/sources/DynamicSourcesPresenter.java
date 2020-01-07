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
package com.tomtom.online.sdk.samples.cases.runtimestyle.sources;

import android.graphics.PointF;
import android.graphics.RectF;

import androidx.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;
import com.tomtom.core.maps.OnMapTapListener;
import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.common.geojson.Feature;
import com.tomtom.online.sdk.common.geojson.FeatureCollection;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.helpers.AssetsHelper;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.style.layers.Layer;
import com.tomtom.online.sdk.map.style.layers.LayerFactory;
import com.tomtom.online.sdk.map.style.sources.GeoJsonSource;
import com.tomtom.online.sdk.map.style.sources.ImageSource;
import com.tomtom.online.sdk.map.style.sources.SourceFactory;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

import java.util.List;

public class DynamicSourcesPresenter extends BaseFunctionalExamplePresenter {

    private static final String IMAGE_LAYER_JSON_PATH = "layers/raster_layer.json";
    private static final String IMAGE_SOURCE_ID = "image-source-id";
    private static final String IMAGE_LAYER_ID = "layer-image-id";
    private static final List<LatLng> IMAGE_CORNERS = ImmutableList.of(
            new LatLng(51.502088, -0.142457), //top left
            new LatLng(51.500520, -0.140731), //top right
            new LatLng(51.499731, -0.142518), //bottom right
            new LatLng(51.501392, -0.144229) //bottom left
    );

    private static final String GEOJSON_DATA_SOURCE_PATH = "layers/geojson_data.json";
    private static final String GEOJSON_LAYER_JSON_PATH = "layers/fill_layer.json";
    private static final String GEOJSON_SOURCE_ID = "geojson-source-id";
    private static final String GEOJSON_LAYER_ID = "layer-line-id";

    private static final double DEFAULT_MAP_ZOOM_FOR_EXAMPLE = 15.0;
    private static final int TOAST_DURATION = 2000; //milliseconds

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        if (!view.isMapRestored()) {
            centerOnBuckinghamPalace();
        }
        tomtomMap.getGestureDetector().addOnMapTapListener(onMapTapListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        tomtomMap.getGestureDetector().removeOnMapTapListener(onMapTapListener);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new DynamicSourcesFunctionalExample();
    }

    @Override
    public void cleanup() {
        removeCustomImageFromStyle();
        removeCustomGeoJsonFromStyle();
    }

    public void toggleCustomGeoJsonInStyle(boolean visible) {
        if (visible) {
            addCustomGeoJsonToStyle();
        } else {
            removeCustomGeoJsonFromStyle();
        }
    }

    public void toggleCustomImageInStyle(boolean visible) {
        if (visible) {
            addCustomImageToStyle();
        } else {
            removeCustomImageFromStyle();
        }
    }

    private void addCustomGeoJsonToStyle() {
        String geoJsonData = AssetsHelper.getAssetFileContent(getContext(), GEOJSON_DATA_SOURCE_PATH);
        //tag::add_geo_json_source[]
        //GEOJSON_SOURCE_ID = unique_source_id
        //GeoJsonData = JSON representing source data
        GeoJsonSource source = SourceFactory.createGeoJsonSource(GEOJSON_SOURCE_ID);
        source.setGeoJson(geoJsonData);
        tomtomMap.getStyleSettings().addSource(source);
        //end::add_geo_json_source[]

        String layerJson = AssetsHelper.getAssetFileContent(getContext(), GEOJSON_LAYER_JSON_PATH);
        //tag::add_custom_layer[]
        Layer layer = LayerFactory.createLayer(layerJson);
        tomtomMap.getStyleSettings().addLayer(layer);
        //end::add_custom_layer[]
    }

    private void removeCustomGeoJsonFromStyle() {
        //tag::remove_layer_and_source[]
        tomtomMap.getStyleSettings().removeLayer(GEOJSON_LAYER_ID);
        tomtomMap.getStyleSettings().removeSource(GEOJSON_SOURCE_ID);
        //end::remove_layer_and_source[]
    }

    private void addCustomImageToStyle() {
        //tag::add_image_source[]
        //IMAGE_SOURCE_ID = unique_source_id
        //IMAGE_CORNERS = Four corners of the image (top left, top right, bottom right, bottom left)
        ImageSource source = SourceFactory.createImageSource(IMAGE_SOURCE_ID, IMAGE_CORNERS);
        source.setImage(getContext().getResources().getDrawable(R.drawable.buckingham_palace_plan));
        tomtomMap.getStyleSettings().addSource(source);
        //end::add_image_source[]

        String layerJson = AssetsHelper.getAssetFileContent(getContext(), IMAGE_LAYER_JSON_PATH);
        Layer layer = LayerFactory.createLayer(layerJson);
        tomtomMap.getStyleSettings().addLayer(layer);
    }

    private void removeCustomImageFromStyle() {
        tomtomMap.getStyleSettings().removeLayer(IMAGE_LAYER_ID);
        tomtomMap.getStyleSettings().removeSource(IMAGE_SOURCE_ID);
    }

    private void centerOnBuckinghamPalace() {
        tomtomMap.centerOn(
                Locations.BUCKINGHAM_PALACE_LOCATION.getLatitude(),
                Locations.BUCKINGHAM_PALACE_LOCATION.getLongitude(),
                DEFAULT_MAP_ZOOM_FOR_EXAMPLE,
                MapConstants.ORIENTATION_NORTH);
    }

    private OnMapTapListener onMapTapListener = new OnMapTapListener() {
        @Override
        public void onMapTap(Float x, Float y) {
            PointF point = new PointF(x, y);
            //tag::query_style_for_features[]
            List<String> layerIds = ImmutableList.of(GEOJSON_LAYER_ID);
            FeatureCollection featureCollection = tomtomMap.getDisplaySettings().featuresAtPoint(point, layerIds);
            //end::query_style_for_features[]
            processFeatureCollection(featureCollection);
        }

        @Override
        public void onMapLongTap(Float x, Float y) {
            //not used right now
        }
    };

    private void processFeatureCollection(FeatureCollection featureCollection) {
        //tag::process_feature_list[]
        List<Feature> featureList = featureCollection.getFeatures();
        FuncUtils.forEach(featureList, feature -> FuncUtils.apply(feature.getId(), this::displayToast));
        //end::process_feature_list[]
    }

    private void displayToast(String id) {
        view.showInfoText(getContext().getString(R.string.interactive_layers_polygon, id), TOAST_DURATION);
    }

    @VisibleForTesting
    @SuppressWarnings("unused")
    void queryStyleForFeaturesInViewport() {
        List<String> layerIds = ImmutableList.of(GEOJSON_LAYER_ID);
        PointF topLeft = new PointF(2.0f, 3.0f);
        PointF bottomRight = new PointF(2.0f, 4.0f);
        RectF mapViewPort = new RectF(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);

        //tag::query_style_for_features_in_viewport[]
        FeatureCollection featureCollection = tomtomMap.getDisplaySettings().featuresInScreenRect(mapViewPort, layerIds);
        //end::query_style_for_features_in_viewport[]
    }

}
