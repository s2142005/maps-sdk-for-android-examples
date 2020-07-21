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

import com.tomtom.online.sdk.routing.reachablerange.ReachableRangeBudgetDescriptor
import com.tomtom.online.sdk.routing.reachablerange.ReachableRangeSpecification
import com.tomtom.online.sdk.routing.route.CombustionVehicleDescriptor
import com.tomtom.online.sdk.routing.route.ElectricVehicleDescriptor
import com.tomtom.online.sdk.routing.route.vehicle.CombustionVehicleConsumption
import com.tomtom.online.sdk.routing.route.vehicle.ElectricVehicleConsumption
import com.tomtom.online.sdk.routing.route.vehicle.VehicleDimensions
import com.tomtom.online.sdk.routing.route.vehicle.VehicleEfficiency
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations

class ReachableRangeSpecificationFactory {

    fun createReachableRangeSpecificationForElectric(): ReachableRangeSpecification {
        //tag::doc_reachable_range_common_params_electric[]
        val reachableRangeBudgetDescriptor = ReachableRangeBudgetDescriptor.Builder()
            .energyBudgetInKWh(VEHICLE_ENERGY_BUDGET)
            .build()
        //end::doc_reachable_range_common_params_electric[]

        //tag::doc_reachable_range_specification_electric[]
        return ReachableRangeSpecification.Builder(Locations.AMSTERDAM_CENTER)
            .reachableRangeBudgetDescriptor(reachableRangeBudgetDescriptor)
            .electricVehicleDescriptor(createElectricVehicleDescriptor())
            .build()
        //end::doc_reachable_range_specification_electric[]
    }

    fun createReachableRangeSpecificationForElectricLimitTo2Hours(): ReachableRangeSpecification {
        val reachableRangeBudgetDescriptor = ReachableRangeBudgetDescriptor.Builder()
            .timeBudgetInSeconds(TIME_LIMIT)
            .build()

        return ReachableRangeSpecification.Builder(Locations.AMSTERDAM_CENTER)
            .electricVehicleDescriptor(createElectricVehicleDescriptor())
            .reachableRangeBudgetDescriptor(reachableRangeBudgetDescriptor)
            .build()
    }

    fun createReachableRangeSpecificationForCombustion(): ReachableRangeSpecification {
        //tag::doc_reachable_range_common_params_combustion[]
        val reachableRangeBudgetDescriptor = ReachableRangeBudgetDescriptor.Builder()
            .fuelBudgetInLiters(VEHICLE_FUEL_BUDGET)
            .build()
        //end::doc_reachable_range_common_params_combustion[]

        //tag::doc_reachable_range_specification_combustion[]
        return ReachableRangeSpecification.Builder(Locations.AMSTERDAM_CENTER)
            .reachableRangeBudgetDescriptor(reachableRangeBudgetDescriptor)
            .combustionVehicleDescriptor(createCombustionVehicleDescriptor())
            .build()
        //end::doc_reachable_range_specification_combustion[]
    }

    private fun createVehicleEfficiency(): VehicleEfficiency {
        return VehicleEfficiency.Builder()
            .accelerationEfficiency(VEHICLE_EFFICIENCY)
            .decelerationEfficiency(VEHICLE_EFFICIENCY)
            .downhillEfficiency(VEHICLE_EFFICIENCY)
            .uphillEfficiency(VEHICLE_EFFICIENCY)
            .build()
    }

    private fun createVehicleDimensions(): VehicleDimensions {
        return VehicleDimensions.Builder()
            .vehicleWeightInKg(VEHICLE_WEIGHT)
            .build()
    }

    private fun createElectricVehicleDescriptor(): ElectricVehicleDescriptor {
        //tag::doc_reachable_range_common_params_electric[]
        val consumption = mapOf(Pair(SPEED, CONSUMPTION_AT_SPEED))
        val electricVehicleConsumption = ElectricVehicleConsumption(
            VEHICLE_MAX_CHARGE, VEHICLE_CURRENT_CHARGE, VEHICLE_AUXILIARY_POWER, consumption
        )

        return ElectricVehicleDescriptor.Builder(electricVehicleConsumption)
            .vehicleEfficiency(createVehicleEfficiency())
            .vehicleDimensions(createVehicleDimensions())
            .build()
        //end::doc_reachable_range_common_params_electric[]
    }

    private fun createCombustionVehicleDescriptor(): CombustionVehicleDescriptor {
        //tag::doc_reachable_range_common_params_combustion[]
        val consumption = mapOf(Pair(SPEED, CONSUMPTION_AT_SPEED))
        val combustionVehicleConsumption = CombustionVehicleConsumption(
            VEHICLE_CURRENT_FUEL, VEHICLE_AUXILIARY_POWER, FUEL_ENERGY_DENSITY, consumption
        )

        return CombustionVehicleDescriptor.Builder()
            .vehicleConsumption(combustionVehicleConsumption)
            .vehicleEfficiency(createVehicleEfficiency())
            .vehicleDimensions(createVehicleDimensions())
            .build()
        //end::doc_reachable_range_common_params_combustion[]
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
        private const val SPEED = 50.0
        private const val CONSUMPTION_AT_SPEED = 6.3
    }
}