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

package com.tomtom.online.sdk.samples.ktx.cases.route.reachablerange

import com.tomtom.online.sdk.data.SpeedToConsumptionMap
import com.tomtom.online.sdk.data.reachablerange.ReachableRangeQuery
import com.tomtom.online.sdk.data.reachablerange.ReachableRangeQueryBuilder
import com.tomtom.online.sdk.routing.data.VehicleEngineType
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations

class ReachableRangeQueryFactory {

    fun createReachableRangeQueryForElectric(): ReachableRangeQuery {
        val consumption = SpeedToConsumptionMap()
        consumption.addSpeedToConsumptionValue(SPEED, CONSUMPTION_AT_SPEED)
        return ReachableRangeQueryBuilder.create(Locations.AMSTERDAM_CENTER)
                .withEnergyBudgetInKWh(VEHICLE_ENERGY_BUDGET)
                .withVehicleWeightInKg(VEHICLE_WEIGHT)
                .withCurrentChargeInKWh(VEHICLE_CURRENT_CHARGE)
                .withMaxChargeInKWh(VEHICLE_MAX_CHARGE)
                .withAuxiliaryPowerInKW(VEHICLE_AUXILIARY_POWER)
                .withAccelerationEfficiency(VEHICLE_EFFICIENCY)
                .withDecelerationEfficiency(VEHICLE_EFFICIENCY)
                .withUphillEfficiency(VEHICLE_EFFICIENCY)
                .withDownhillEfficiency(VEHICLE_EFFICIENCY)
                .withConstantSpeedConsumptionInKWhPerHundredKm(consumption)
                .withVehicleEngineType(VehicleEngineType.ELECTRIC)
                .build()
    }

    fun createReachableRangeQueryForElectricLimitTo2Hours(): ReachableRangeQuery {
        val consumption = SpeedToConsumptionMap()
        consumption.addSpeedToConsumptionValue(SPEED, CONSUMPTION_AT_SPEED)
        return ReachableRangeQueryBuilder.create(Locations.AMSTERDAM_CENTER)
                .withTimeBudgetInSeconds(TIME_LIMIT)
                .withVehicleWeightInKg(VEHICLE_WEIGHT)
                .withCurrentChargeInKWh(VEHICLE_CURRENT_CHARGE)
                .withMaxChargeInKWh(VEHICLE_MAX_CHARGE)
                .withAuxiliaryPowerInKW(VEHICLE_AUXILIARY_POWER)
                .withAccelerationEfficiency(VEHICLE_EFFICIENCY)
                .withDecelerationEfficiency(VEHICLE_EFFICIENCY)
                .withUphillEfficiency(VEHICLE_EFFICIENCY)
                .withDownhillEfficiency(VEHICLE_EFFICIENCY)
                .withConstantSpeedConsumptionInKWhPerHundredKm(consumption)
                .withVehicleEngineType(VehicleEngineType.ELECTRIC)
                .build()
    }

    fun createReachableRangeQueryForCombustion(): ReachableRangeQuery {
        val consumption = SpeedToConsumptionMap()
        consumption.addSpeedToConsumptionValue(SPEED, CONSUMPTION_AT_SPEED)
        //tag::doc_reachable_range_query_combustion[]
        return ReachableRangeQueryBuilder.create(Locations.AMSTERDAM_CENTER)
                .withFuelBudgetInLiters(VEHICLE_FUEL_BUDGET)
                //end::doc_reachable_range_query_combustion[]
                //tag::doc_reachable_range_common_params_combustion[]
                .withVehicleWeightInKg(VEHICLE_WEIGHT)
                .withCurrentFuelInLiters(VEHICLE_CURRENT_FUEL)
                .withFuelEnergyDensityInMJoulesPerLiter(FUEL_ENERGY_DENSITY)
                .withAuxiliaryPowerInLitersPerHour(VEHICLE_AUXILIARY_POWER)
                .withAccelerationEfficiency(VEHICLE_EFFICIENCY)
                .withDecelerationEfficiency(VEHICLE_EFFICIENCY)
                .withUphillEfficiency(VEHICLE_EFFICIENCY)
                .withDownhillEfficiency(VEHICLE_EFFICIENCY)
                .withConstantSpeedConsumptionInLitersPerHundredKm(consumption)
                .withVehicleEngineType(VehicleEngineType.COMBUSTION)
                //end::doc_reachable_range_common_params_combustion[]
                .build()
    }

    companion object {
        private const val VEHICLE_WEIGHT = 1600 // in kg
        private const val VEHICLE_AUXILIARY_POWER = 1.7 // in kW or liters/hour
        private const val VEHICLE_EFFICIENCY = 0.33
        private const val VEHICLE_FUEL_BUDGET = 5.0 // in liters
        private const val VEHICLE_CURRENT_FUEL = 43.0 // in liters
        private const val VEHICLE_ENERGY_BUDGET = 5.0 // kWh
        private const val VEHICLE_MAX_CHARGE = 85.0 // in kWh
        private const val VEHICLE_CURRENT_CHARGE = 43.0 // in kWh
        private const val FUEL_ENERGY_DENSITY = 34.2 // MJoules/liters
        private const val TIME_LIMIT = 7200.0 // in seconds
        private const val SPEED = 50
        private const val CONSUMPTION_AT_SPEED = 6.3
    }
}