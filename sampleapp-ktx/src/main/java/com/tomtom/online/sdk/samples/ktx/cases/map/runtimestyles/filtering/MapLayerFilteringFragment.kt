/*
 * Copyright (c) 2015-2020 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.ktx.cases.map.runtimestyles.filtering

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.online.sdk.map.LayerSetConfiguration
import com.tomtom.online.sdk.map.style.expression.ComparisonExpression
import com.tomtom.online.sdk.map.style.expression.Expression
import com.tomtom.online.sdk.map.style.expression.GetExpression
import com.tomtom.online.sdk.map.style.expression.LiteralExpression
import com.tomtom.online.sdk.map.style.layers.Layer
import com.tomtom.online.sdk.samples.ktx.MapAction
import com.tomtom.online.sdk.samples.ktx.cases.ExampleFragment
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations
import com.tomtom.sdk.examples.R
import kotlinx.android.synthetic.main.fragment_map_layer_filtering.*

class MapLayerFilteringFragment : ExampleFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_layer_filtering, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confViewActions()
    }

    override fun onExampleStarted() {
        centerOnLocation(location = Locations.AMSTERDAM, zoomLevel = DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE)
        registerOnMapChangedListener()
        //note: we load custom style that contains layers to filter in this example
        loadCustomStyle()
    }

    override fun onExampleEnded() {
        unregisterOnMapChangedListener()
        loadBaseStyle()
    }

    private fun confViewActions() {
        map_layer_filtering_road_motorways.setOnClickListener {
            applyFilterOnLayers {
                //tag::doc_create_filtering_expression[]
                //ROAD_TYPE = "road_type"
                //ROAD_TYPE_MOTORWAY = "Motorway"
                val filteringExpression = ComparisonExpression.eq(
                    GetExpression.get(ROAD_TYPE),
                    LiteralExpression.string(ROAD_TYPE_MOTORWAY)
                )
                //end::doc_create_filtering_expression[]
                filteringExpression
            }
        }

        map_layer_filtering_road_major.setOnClickListener {
            applyFilterOnLayers {
                ComparisonExpression.eq(
                    GetExpression.get(ROAD_TYPE),
                    LiteralExpression.string(ROAD_TYPE_MAJOR_ROAD)
                )
            }
        }

        map_layer_filtering_road_all.setOnClickListener {
            resetFilterOnLayers()
        }
    }

    private fun executeOnLayer(call: (Layer) -> Unit) {
        mainViewModel.applyOnMap(MapAction {
            let { tomtomMap ->
                //tag::find_layers_by_source_layer_id[]
                //LAYERS_REGEX = "Traffic flow"
                val layers = tomtomMap.styleSettings.findLayersBySourceLayerId(LAYERS_REGEX)
                //end::find_layers_by_source_layer_id[]
                layers.forEach { layer -> call(layer) }
            }
        })
    }

    private fun applyFilterOnLayers(filterCreator: () -> Expression) {
        executeOnLayer { layer ->
            val filteringExpression = filterCreator()
            //tag::apply_filter[]
            layer.setFilter(filteringExpression)
            //end::apply_filter[]
        }
    }

    private fun resetFilterOnLayers() {
        executeOnLayer { layer ->
            //tag::reset_filter[]
            layer.resetFilter()
            //end::reset_filter[]
        }
    }

    private fun registerOnMapChangedListener() {
        mainViewModel.applyOnMap(MapAction {
            addOnMapChangedListener(showTrafficWhenCustomStyleLoaded)
        })
    }

    private fun unregisterOnMapChangedListener() {
        mainViewModel.applyOnMap(MapAction {
            removeOnMapChangedListener(showTrafficWhenCustomStyleLoaded)
        })
    }

    private val showTrafficWhenCustomStyleLoaded = object : AbstractOnMapChangedListener() {
        override fun onDidFinishLoadingStyle() {
            val handler = Handler()
            mainViewModel.applyOnMap(MapAction {
                handler.post {
                    trafficSettings.turnOnTrafficFlowTiles()
                }
            })
        }
    }

    private fun loadCustomStyle() {
        //Custom-traffic is a style with custom traffic layers,
        //so we do not need so complicated expressions
        mainViewModel.applyOnMap(MapAction {
            uiSettings.setStyleUrl(
                "asset://styles/custom-traffic.json",
                LayerSetConfiguration.Builder()
                    .trafficFlowTilesConfiguration(STYLE_TRAFFIC_FLOW_SOURCE_ID)
                    .build()
            )
        })
    }

    private fun loadBaseStyle() {
        mainViewModel.applyOnMap(MapAction {
            uiSettings.loadDefaultStyle()
        })
    }

    companion object {
        private const val DEFAULT_ZOOM_LEVEL_FOR_EXAMPLE = 9.0

        private const val ROAD_TYPE = "road_type"
        private const val ROAD_TYPE_MOTORWAY = "Motorway"
        private const val ROAD_TYPE_MAJOR_ROAD = "Major road"
        private const val STYLE_TRAFFIC_FLOW_SOURCE_ID = "tomtom-flow-vector-reduced-sensitivity"

        private const val LAYERS_REGEX = "Traffic flow"
    }
}