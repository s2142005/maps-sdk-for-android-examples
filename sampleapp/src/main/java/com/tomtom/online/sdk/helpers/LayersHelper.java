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
package com.tomtom.online.sdk.helpers;

import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.style.layers.Layer;

/**
 * Provides helper methods which extends the SDK's API functionality.
 */
public class LayersHelper {

    private final TomtomMap tomtomMap;
    private final LayersFactoryHelper layersFactoryHelper = new LayersFactoryHelper();

    public LayersHelper(TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
    }

    /**
     * Obtains the layers factory helper which allows to create chosen layer in json string format.
     *
     * @return the layers factory helper object
     */
    public LayersFactoryHelper getLayersFactoryHelper() {
        return layersFactoryHelper;
    }

    /**
     * Moves layer to the front position (before all other layers)
     *
     * @param layer the layer which will be moved to front
     */
    public void moveLayerToFront(Layer layer) {
        tomtomMap.getStyleSettings().moveLayerBehind(layer.getId(), "");
    }

    /**
     * Moves layer to the front position (before all other layers)
     *
     * @param layerId the if of the layer which will be moved to front
     */
    public void moveLayerToFrontWithId(String layerId) {
        tomtomMap.getStyleSettings().moveLayerBehind(layerId, "");
    }
}
