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

package com.tomtom.online.sdk.samples.ktx.cases.route.consumption

import com.google.common.collect.ImmutableMap
import com.tomtom.online.sdk.data.SpeedToConsumptionMap
import com.tomtom.online.sdk.routing.data.RouteQuery
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder
import com.tomtom.online.sdk.routing.data.VehicleEngineType
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToUtrechtRouteConfig

class RouteConsumptionQueryFactory {

    fun prepareCombustionQuery(): RouteQuery {
        val origin = AmsterdamToUtrechtRouteConfig().origin
        val destination = AmsterdamToUtrechtRouteConfig().destination
        //tag::doc_consumption_model_combustion[]
        val routeQuery = RouteQueryBuilder.create(origin, destination)
                .withConsiderTraffic(false)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withVehicleWeightInKg(VEHICLE_WEIGHT)
                .withCurrentFuelInLiters(CURRENT_FUEL)
                .withAuxiliaryPowerInLitersPerHour(AUXILIARY_POWER_COMBUSTION)
                .withFuelEnergyDensityInMJoulesPerLiter(FUEL_ENERGY_DENSITY)
                .withAccelerationEfficiency(EFFICIENCY) //e.g. KineticEnergyGained/ChemicalEnergyConsumed
                .withDecelerationEfficiency(EFFICIENCY) //e.g. ChemicalEnergySaved/KineticEnergyLost
                .withUphillEfficiency(EFFICIENCY) //e.g. PotentialEnergyGained/ChemicalEnergyConsumed
                .withDownhillEfficiency(EFFICIENCY) //e.g. ChemicalEnergySaved/PotentialEnergyLost
                .withVehicleEngineType(VehicleEngineType.COMBUSTION)
                .withConstantSpeedConsumptionInLitersPerHundredKm(SpeedToConsumptionMap.create(ImmutableMap.builder<Int, Double>()
                        //vehicle specific consumption model <speed, consumption in liters>
                        .put(10, 6.5)
                        .put(30, 7.0)
                        .put(50, 8.0)
                        .put(70, 8.4)
                        .put(90, 7.7)
                        .put(120, 7.5)
                        .put(150, 9.0)
                        .build())
                )
                .build()
        //end::doc_consumption_model_combustion[]
        return routeQuery
    }

    fun prepareElectricQuery(): RouteQuery {
        val origin = AmsterdamToUtrechtRouteConfig().origin
        val destination = AmsterdamToUtrechtRouteConfig().destination
        //tag::doc_consumption_model_electric[]
        val routeQuery = RouteQueryBuilder.create(origin, destination)
                .withConsiderTraffic(false)
                .withMaxAlternatives(MAX_ALTERNATIVES)
                .withVehicleWeightInKg(VEHICLE_WEIGHT)
                .withCurrentChargeInKWh(CURRENT_ELECTRIC_CHARGE)
                .withMaxChargeInKWh(MAX_ELECTRIC_CHARGE)
                .withAuxiliaryPowerInKW(AUXILIARY_POWER_ELECTRIC)
                .withAccelerationEfficiency(EFFICIENCY) //e.g. KineticEnergyGained/ChemicalEnergyConsumed
                .withDecelerationEfficiency(EFFICIENCY) //e.g. ChemicalEnergySaved/KineticEnergyLost
                .withUphillEfficiency(EFFICIENCY) //e.g. PotentialEnergyGained/ChemicalEnergyConsumed
                .withDownhillEfficiency(EFFICIENCY) //e.g. ChemicalEnergySaved/PotentialEnergyLost
                .withVehicleEngineType(VehicleEngineType.ELECTRIC)
                .withConstantSpeedConsumptionInKWhPerHundredKm(SpeedToConsumptionMap.create(ImmutableMap.builder<Int, Double>()
                        //vehicle specific consumption model <speed, consumption in kWh>
                        .put(10, 5.0)
                        .put(30, 10.0)
                        .put(50, 15.0)
                        .put(70, 20.0)
                        .put(90, 25.0)
                        .put(120, 30.0)
                        .build())
                )
                .build()
        //end::doc_consumption_model_electric[]
        return routeQuery
    }

    companion object {
        private const val MAX_ALTERNATIVES = 2
        private const val VEHICLE_WEIGHT = 1600 // in kilograms
        private const val CURRENT_ELECTRIC_CHARGE = 43.0 //in KWh
        private const val MAX_ELECTRIC_CHARGE = 85.0 //in KWh
        private const val AUXILIARY_POWER_ELECTRIC = 1.7 //in KW
        private const val AUXILIARY_POWER_COMBUSTION = 0.2 //liters per hour
        private const val EFFICIENCY = 0.33
        private const val CURRENT_FUEL = 50.0 //in liters
        private const val FUEL_ENERGY_DENSITY = 34.2
    }

}