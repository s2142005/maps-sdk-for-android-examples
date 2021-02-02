/*
 * Copyright (c) 2015-2021 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */

package com.tomtom.online.sdk.samples.ktx.cases.route.consumption

import com.tomtom.online.sdk.routing.route.*
import com.tomtom.online.sdk.routing.route.vehicle.CombustionVehicleConsumption
import com.tomtom.online.sdk.routing.route.vehicle.ElectricVehicleConsumption
import com.tomtom.online.sdk.routing.route.vehicle.VehicleDimensions
import com.tomtom.online.sdk.routing.route.vehicle.VehicleEfficiency
import com.tomtom.online.sdk.samples.ktx.utils.routes.AmsterdamToUtrechtRouteConfig

class RouteConsumptionSpecificationFactory {

    fun prepareCombustionRouteSpecification(): RouteSpecification {
        val origin = AmsterdamToUtrechtRouteConfig().origin
        val destination = AmsterdamToUtrechtRouteConfig().destination
        //tag::doc_consumption_model_combustion[]
        val vehicleEfficiency = VehicleEfficiency.Builder()
            .accelerationEfficiency(EFFICIENCY) //e.g. KineticEnergyGained/ChemicalEnergyConsumed
            .decelerationEfficiency(EFFICIENCY) //e.g. ChemicalEnergySaved/KineticEnergyLost
            .downhillEfficiency(EFFICIENCY) //e.g. ChemicalEnergySaved/PotentialEnergyLost
            .uphillEfficiency(EFFICIENCY) //e.g. PotentialEnergyGained/ChemicalEnergyConsumed
            .build()

        val vehicleDimensions = VehicleDimensions.Builder()
            .vehicleWeightInKg(VEHICLE_WEIGHT)
            .build()

        //vehicle specific consumption model <speed, consumption in liters>
        val combustionVehicleConsumption = CombustionVehicleConsumption(
            CURRENT_FUEL,
            AUXILIARY_POWER_COMBUSTION,
            FUEL_ENERGY_DENSITY,
            mapOf(
                Pair(10.0, 6.5),
                Pair(30.0, 7.0),
                Pair(50.0, 8.0),
                Pair(70.0, 8.4),
                Pair(90.0, 7.7),
                Pair(120.0, 7.5),
                Pair(150.0, 9.0)
            )
        )

        val combustionVehicleDescriptor = CombustionVehicleDescriptor.Builder()
            .vehicleConsumption(combustionVehicleConsumption)
            .vehicleEfficiency(vehicleEfficiency)
            .vehicleDimensions(vehicleDimensions)
            .build()

        val routeDescriptor = RouteDescriptor.Builder()
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .maxAlternatives(MAX_ALTERNATIVES)
            .build()

        val routeSpecification = RouteSpecification.Builder(origin, destination)
            .combustionVehicleDescriptor(combustionVehicleDescriptor)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        //end::doc_consumption_model_combustion[]
        return routeSpecification
    }

    fun prepareElectricRouteSpecification(): RouteSpecification {
        val origin = AmsterdamToUtrechtRouteConfig().origin
        val destination = AmsterdamToUtrechtRouteConfig().destination
        //tag::doc_consumption_model_electric[]
        val vehicleEfficiency = VehicleEfficiency.Builder()
            .accelerationEfficiency(EFFICIENCY) //e.g. KineticEnergyGained/ChemicalEnergyConsumed
            .decelerationEfficiency(EFFICIENCY) //e.g. ChemicalEnergySaved/KineticEnergyLost
            .downhillEfficiency(EFFICIENCY) //e.g. ChemicalEnergySaved/PotentialEnergyLost
            .uphillEfficiency(EFFICIENCY) //e.g. PotentialEnergyGained/ChemicalEnergyConsumed
            .build()

        val vehicleDimensions = VehicleDimensions.Builder()
            .vehicleWeightInKg(VEHICLE_WEIGHT)
            .build()

        //vehicle specific consumption model <speed, consumption in liters>
        val electricVehicleConsumption = ElectricVehicleConsumption(
            MAX_ELECTRIC_CHARGE,
            CURRENT_ELECTRIC_CHARGE,
            AUXILIARY_POWER_ELECTRIC,
            mapOf(
                Pair(10.0, 5.0),
                Pair(30.0, 10.0),
                Pair(50.0, 15.0),
                Pair(70.0, 20.0),
                Pair(90.0, 25.0),
                Pair(120.0, 30.0)
            )
        )

        val electricVehicleDescriptor = ElectricVehicleDescriptor.Builder(electricVehicleConsumption)
            .vehicleEfficiency(vehicleEfficiency)
            .vehicleDimensions(vehicleDimensions)
            .build()

        val routeDescriptor = RouteDescriptor.Builder()
            .considerTraffic(false)
            .build()

        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor)
            .maxAlternatives(MAX_ALTERNATIVES)
            .build()

        val routeSpecification = RouteSpecification.Builder(origin, destination)
            .electricVehicleDescriptor(electricVehicleDescriptor)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        //end::doc_consumption_model_electric[]
        return routeSpecification
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