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
package com.tomtom.online.sdk.samples.cases.runtimestyle.zorder.displayers;

import com.google.common.collect.ImmutableList;
import com.tomtom.online.sdk.common.geojson.Feature;
import com.tomtom.online.sdk.common.geojson.FeatureCollection;
import com.tomtom.online.sdk.common.geojson.geometry.LineString;
import com.tomtom.online.sdk.helpers.LayersFactoryHelper;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.style.layers.Layer;
import com.tomtom.online.sdk.map.style.layers.LayerFactory;
import com.tomtom.online.sdk.map.style.sources.GeoJsonSource;
import com.tomtom.online.sdk.map.style.sources.SourceFactory;
import com.tomtom.online.sdk.routing.data.FullRoute;

public class GeoJsonLayerOperationDisplayer {

    private static final String GEOJSON_SOURCE_ID = "geojson-source-id";
    private static final String GEOJSON_LAYER_ID = "layer-line-id";

    private final TomtomMap tomtomMap;
    private Layer layerGeoJson;
    private final LayersFactoryHelper layersFactoryHelper;

    public GeoJsonLayerOperationDisplayer(TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
        this.layersFactoryHelper = new LayersFactoryHelper();
    }

    public void moveLayerToFront() {
        //tag::doc_map_move_geoJSON_layer[]
        // layerGeoJson = tomtomMap.getStyleSettings().findLayerById("layer-line-id").orNull();
        tomtomMap.getStyleSettings().moveLayerBehind(layerGeoJson.getId(), "");
        //end::doc_map_move_geoJSON_layer[]
    }

    public void addGeoJsonLayer() {
        final String jsonLayer = layersFactoryHelper.createGeoJsonLineLayer(GEOJSON_LAYER_ID, GEOJSON_SOURCE_ID);

        Layer layer = LayerFactory.createLayer(jsonLayer);
        tomtomMap.getStyleSettings().addLayer(layer);
        initAllGeoJsonLayers();
    }

    private void initAllGeoJsonLayers() {
        layerGeoJson = tomtomMap.getStyleSettings().findLayerById(GEOJSON_LAYER_ID).orNull();
    }

    public void createGeoJsonSource(FullRoute route) {
        LineString lineString = LineString.builder()
                .coordinates(route.getCoordinates())
                .build();

        Feature feature = Feature.builder()
                .geometry(lineString)
                .build();

        FeatureCollection featureCollection = FeatureCollection.builder()
                .features(ImmutableList.of(feature))
                .build();

        GeoJsonSource source = SourceFactory.createGeoJsonSource(GEOJSON_SOURCE_ID);
        source.setGeoJson(featureCollection);

        tomtomMap.getStyleSettings().addSource(source);
    }

    public void restore() {
        initAllGeoJsonLayers();
    }
}
