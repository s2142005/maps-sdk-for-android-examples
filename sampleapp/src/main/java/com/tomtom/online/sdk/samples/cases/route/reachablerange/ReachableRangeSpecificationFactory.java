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
package com.tomtom.online.sdk.samples.cases.route.reachablerange;

import com.tomtom.online.sdk.routing.reachablerange.ReachableRangeBudgetDescriptor;
import com.tomtom.online.sdk.routing.reachablerange.ReachableRangeSpecification;
import com.tomtom.online.sdk.routing.route.CombustionVehicleDescriptor;
import com.tomtom.online.sdk.routing.route.ElectricVehicleDescriptor;
import com.tomtom.online.sdk.routing.route.vehicle.CombustionVehicleConsumption;
import com.tomtom.online.sdk.routing.route.vehicle.ElectricVehicleConsumption;
import com.tomtom.online.sdk.routing.route.vehicle.VehicleDimensions;
import com.tomtom.online.sdk.routing.route.vehicle.VehicleEfficiency;
import com.tomtom.online.sdk.samples.utils.Locations;

import java.util.HashMap;
import java.util.Map;

public class ReachableRangeSpecificationFactory {

    public ReachableRangeSpecification createReachableRangeSpecificationForElectric() {
        //tag::doc_reachable_range_budget_descriptor[]
        ReachableRangeBudgetDescriptor reachableRangeBudgetDescriptor =
                new ReachableRangeBudgetDescriptor.Builder()
                        .energyBudgetInKWh(5.0)
                        .build();

        //end::doc_reachable_range_budget_descriptor[]
        return createReachableRangeSpecificationForElectric(reachableRangeBudgetDescriptor);
    }

    public ReachableRangeSpecification createReachableRangeSpecificationForElectricLimitTo2Hours() {
        ReachableRangeBudgetDescriptor reachableRangeBudgetDescriptor =
                new ReachableRangeBudgetDescriptor.Builder()
                        .timeBudgetInSeconds(7200.0)
                        .build();
        return createReachableRangeSpecificationForElectric(reachableRangeBudgetDescriptor);
    }

    public ReachableRangeSpecification createReachableRangeSpecificationForCombustion() {
        //tag::doc_reachable_range_common_params_combustion[]
        ReachableRangeBudgetDescriptor reachableRangeBudgetDescriptor =
                new ReachableRangeBudgetDescriptor.Builder()
                        .fuelBudgetInLiters(5.0)
                        .build();

        Map<Double, Double> consumptionMap = new HashMap<>();
        consumptionMap.put(50.0, 6.3);

        CombustionVehicleConsumption combustionVehicleConsumption = new CombustionVehicleConsumption(
                43.0, 1.7, 34.2, consumptionMap
        );

        CombustionVehicleDescriptor combustionVehicleDescriptor = new CombustionVehicleDescriptor.Builder()
                .vehicleConsumption(combustionVehicleConsumption)
                .vehicleDimensions(new VehicleDimensions.Builder()
                        .vehicleWeightInKg(1600)
                        .build())
                .vehicleEfficiency(new VehicleEfficiency.Builder()
                        .uphillEfficiency(0.33)
                        .downhillEfficiency(0.33)
                        .decelerationEfficiency(0.33)
                        .accelerationEfficiency(0.33)
                        .build())
                .build();
        //end::doc_reachable_range_common_params_combustion[]

        //tag::doc_reachable_range_specification_combustion[]
        return new ReachableRangeSpecification.Builder(Locations.AMSTERDAM_CENTER_LOCATION)
                .reachableRangeBudgetDescriptor(reachableRangeBudgetDescriptor)
                .combustionVehicleDescriptor(combustionVehicleDescriptor)
                .build();
        //end::doc_reachable_range_specification_combustion[]
    }

    private ReachableRangeSpecification createReachableRangeSpecificationForElectric(ReachableRangeBudgetDescriptor reachableRangeBudgetDescriptor) {
        //tag::doc_reachable_range_common_params_electric[]
        Map<Double, Double> consumptionMap = new HashMap<>();
        consumptionMap.put(50.0, 6.3);

        ElectricVehicleConsumption electricVehicleConsumption =
                new ElectricVehicleConsumption(85.0, 43.0, 1.7, consumptionMap);

        ElectricVehicleDescriptor electricVehicleDescriptor = new ElectricVehicleDescriptor.Builder(electricVehicleConsumption)
                .vehicleDimensions(new VehicleDimensions.Builder()
                        .vehicleWeightInKg(1600)
                        .build())
                .vehicleEfficiency(new VehicleEfficiency.Builder()
                        .uphillEfficiency(0.33)
                        .downhillEfficiency(0.33)
                        .decelerationEfficiency(0.33)
                        .accelerationEfficiency(0.33)
                        .build())
                .build();
        //end::doc_reachable_range_common_params_electric[]

        //tag::doc_reachable_range_specification_electric[]
        return new ReachableRangeSpecification.Builder(Locations.AMSTERDAM_CENTER_LOCATION)
                .reachableRangeBudgetDescriptor(reachableRangeBudgetDescriptor)
                .electricVehicleDescriptor(electricVehicleDescriptor)
                .build();
        //end::doc_reachable_range_specification_electric[]
    }
}
