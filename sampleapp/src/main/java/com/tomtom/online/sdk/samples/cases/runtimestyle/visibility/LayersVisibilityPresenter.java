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
package com.tomtom.online.sdk.samples.cases.runtimestyle.visibility;

import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.style.layers.Layer;
import com.tomtom.online.sdk.map.style.layers.Visibility;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

import java.util.List;

public class LayersVisibilityPresenter extends BaseFunctionalExamplePresenter {

    private static final String ROADS_NETWORK_LAYER_REGEX = "[mM]otorway.*|.*[rR]oad.*";
    private static final String WOODLAND_LAYER_REGEX = "[wW]oodland";
    private static final String BUILT_UP_AREA_LAYER_REGEX = "Built-up area";

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        if (!view.isMapRestored()) {
            centerOnBerlin();
            setAllChosenLayersVisibility(Visibility.NONE);
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new LayersVisibilityFunctionalExample();
    }

    @Override
    public void cleanup() {
        setAllChosenLayersVisibility(Visibility.VISIBLE);
    }

    private void centerOnBerlin() {
        centerOn(Locations.BERLIN_LOCATION);
    }

    void setRoadNetworkLayerVisibility(Visibility visibility) {
        //tag::find_layers_by_source_layer_id[]
        //ROADS_NETWORK_LAYER_REGEX = "[mM]otorway.*|.*[rR]oad.*"
        List<Layer> layers = tomtomMap.getStyleSettings().findLayersBySourceLayerId(ROADS_NETWORK_LAYER_REGEX);
        //end::find_layers_by_source_layer_id[]

        //tag::modify_layers_visibility[]
        for (Layer layer : layers) {
            layer.setVisibility(visibility);
        }
        //end::modify_layers_visibility[]
    }

    void setWoodlandLayerVisibility(Visibility visibility) {
        List<Layer> layers = tomtomMap.getStyleSettings().findLayersBySourceLayerId(WOODLAND_LAYER_REGEX);
        FuncUtils.forEach(layers, layer -> layer.setVisibility(visibility));
    }

    void setBuiltUpLayerVisibility(Visibility visibility) {
        List<Layer> layers = tomtomMap.getStyleSettings().findLayersBySourceLayerId(BUILT_UP_AREA_LAYER_REGEX);
        FuncUtils.forEach(layers, layer -> layer.setVisibility(visibility));
    }

    private void setAllChosenLayersVisibility(Visibility visibility) {
        final String combinedRegExForAllChosenLayers = String.format("%s|%s|%s",
                ROADS_NETWORK_LAYER_REGEX,
                WOODLAND_LAYER_REGEX,
                BUILT_UP_AREA_LAYER_REGEX);

        FuncUtils.forEach(tomtomMap.getStyleSettings().findLayersBySourceLayerId(combinedRegExForAllChosenLayers),
                layer -> layer.setVisibility(visibility));

    }
}
