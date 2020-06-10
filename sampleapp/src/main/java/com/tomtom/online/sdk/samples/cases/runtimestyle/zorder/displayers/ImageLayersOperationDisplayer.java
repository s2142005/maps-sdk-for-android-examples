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

import android.content.Context;

import androidx.annotation.DrawableRes;

import com.google.common.collect.ImmutableList;
import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.helpers.LayersFactoryHelper;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.style.layers.Layer;
import com.tomtom.online.sdk.map.style.layers.LayerFactory;
import com.tomtom.online.sdk.map.style.sources.ImageSource;
import com.tomtom.online.sdk.map.style.sources.SourceFactory;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.utils.Locations;

import java.util.ArrayList;
import java.util.List;

public class ImageLayersOperationDisplayer {

    private static final String IMAGE_SOURCE_ID = "image-source-id";
    private static final String IMAGE_LAYER_ID = "layer-image-id";

    private final Context context;
    private final TomtomMap tomtomMap;
    private final LayersFactoryHelper layersFactoryHelper;

    private List<Layer> layersImages = new ArrayList<>();

    public ImageLayersOperationDisplayer(Context context, TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
        this.context = context;
        this.layersFactoryHelper = new LayersFactoryHelper();
    }

    public void moveLayersToFront() {
        FuncUtils.forEach(layersImages, this::proceedImageLayer);
    }

    private void proceedImageLayer(Layer imgLayer) {
        //tag::doc_map_move_img_layer[]
        // layersImages = tomtomMap.getStyleSettings().findLayersById(IMAGE_LAYER_ID + "[0-9]");
        // Layer imgLayer = layersImages.get(...)
        tomtomMap.getStyleSettings().moveLayerBehind(imgLayer.getId(), "");
        //end::doc_map_move_img_layer[]
    }

    public void addAllImages() {
        addImage("1", Locations.SAN_JOSE_IMG1, R.drawable.img1);
        addImage("2", Locations.SAN_JOSE_IMG2, R.drawable.img2);
        addImage("3", Locations.SAN_JOSE_IMG3, R.drawable.img3);
    }

    public void restore() {
        initAllImageLayers();
    }

    private void initAllImageLayers() {
        layersImages = tomtomMap.getStyleSettings().findLayersById(IMAGE_LAYER_ID + "[0-9]");
    }

    private void addImage(String imageId, LatLng coordinate, @DrawableRes int imageResId) {
        createImageSource(imageId, coordinate, imageResId);
        createImageLayer(imageId);
        initAllImageLayers();
    }

    private void createImageLayer(String imageId) {
        final String jsonLayer = layersFactoryHelper.createImageLayer(
                IMAGE_LAYER_ID + imageId,
                IMAGE_SOURCE_ID + imageId);
        Layer layer = LayerFactory.createLayer(jsonLayer);
        tomtomMap.getStyleSettings().addLayer(layer);
    }

    private void createImageSource(String imageId, LatLng coordinate, @DrawableRes int imageResId) {
        final String sourceId = IMAGE_SOURCE_ID + imageId;
        ImageSource source = SourceFactory.createImageSource(sourceId, quadWithDelta(coordinate, 0.25));
        source.setImage(context.getResources().getDrawable(imageResId));
        tomtomMap.getStyleSettings().addSource(source);
    }

    private List<LatLng> quadWithDelta(LatLng coordinate, double delta) {
        return ImmutableList.of(
                new LatLng(coordinate.getLatitude() + delta / 2, coordinate.getLongitude() - delta),
                new LatLng(coordinate.getLatitude() + delta / 2, coordinate.getLongitude() + delta),
                new LatLng(coordinate.getLatitude() - delta / 2, coordinate.getLongitude() + delta),
                new LatLng(coordinate.getLatitude() - delta / 2, coordinate.getLongitude() - delta)
        );
    }
}
