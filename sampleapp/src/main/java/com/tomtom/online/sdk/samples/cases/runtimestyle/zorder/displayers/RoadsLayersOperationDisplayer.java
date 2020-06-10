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

import com.tomtom.online.sdk.common.func.FuncUtils;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.style.layers.Layer;

import java.util.List;

public class RoadsLayersOperationDisplayer {

    private final TomtomMap tomtomMap;
    private List<Layer> layersRoads;

    public RoadsLayersOperationDisplayer(TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
        initAllRoadsLayers();
    }

    public void moveLayersToFront() {
        FuncUtils.forEach(layersRoads, this::proceedRoadsLayer);
    }

    private void initAllRoadsLayers() {
        layersRoads = tomtomMap.getStyleSettings().findLayersById(".*[rR]oad.*|.*[mM]otorway.*");
    }

    private void proceedRoadsLayer(Layer roadLayer) {
        //tag::doc_map_move_roads_layer[]
        // layersRoads = tomtomMap.getStyleSettings().findLayersById(".*[rR]oad.*|.*[mM]otorway.*");
        // Layer roadLayer = layersRoads.get(...)
        tomtomMap.getStyleSettings().moveLayerBehind(roadLayer.getId(), "");
        //end::doc_map_move_roads_layer[]
    }

    public void restore() {
        initAllRoadsLayers();
    }
}
