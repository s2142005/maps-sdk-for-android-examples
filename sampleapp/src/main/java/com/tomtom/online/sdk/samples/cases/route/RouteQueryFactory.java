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
package com.tomtom.online.sdk.samples.cases.route;

import com.google.common.collect.ImmutableMap;
import com.tomtom.online.sdk.common.location.BoundingBox;
import com.tomtom.online.sdk.data.SpeedToConsumptionMap;
import com.tomtom.online.sdk.routing.data.Avoid;
import com.tomtom.online.sdk.routing.data.InstructionsType;
import com.tomtom.online.sdk.routing.data.Report;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.routing.data.RouteType;
import com.tomtom.online.sdk.routing.data.TravelMode;
import com.tomtom.online.sdk.routing.data.VehicleEngineType;
import com.tomtom.online.sdk.samples.routes.RouteConfigExample;

import java.util.Date;
import java.util.List;

public class RouteQueryFactory {

    public static RouteQuery createAvoidRouteQuery(Avoid routeAvoid, RouteConfigExample routeConfig) {
        //tag::doc_route_avoids[]
        RouteQuery queryBuilder = RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withMaxAlternatives(0)
                .withReport(Report.NONE)
                .withInstructionsType(InstructionsType.NONE)
                .withAvoidType(routeAvoid)
                .withConsiderTraffic(false).build();
        //end::doc_route_avoids[]
        return queryBuilder;
    }

    public static RouteQuery createRouteConsumptionElectricQuery(RouteConfigExample routeConfig) {
        //tag::doc_consumption_model_electric[]
        RouteQuery queryBuilder = RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withConsiderTraffic(false)
                .withMaxAlternatives(2)
                .withVehicleWeightInKg(1600) //vehicle weight in kilograms
                .withCurrentChargeInKWh(43.0)
                .withMaxChargeInKWh(85.0)
                .withAuxiliaryPowerInKW(1.7)
                .withAccelerationEfficiency(0.33) //e.g. KineticEnergyGained/ChemicalEnergyConsumed
                .withDecelerationEfficiency(0.33) //e.g. ChemicalEnergySaved/KineticEnergyLost
                .withUphillEfficiency(0.33) //e.g. PotentialEnergyGained/ChemicalEnergyConsumed
                .withDownhillEfficiency(0.33) //e.g. ChemicalEnergySaved/PotentialEnergyLost
                .withVehicleEngineType(VehicleEngineType.ELECTRIC)
                .withConstantSpeedConsumptionInKWhPerHundredKm(SpeedToConsumptionMap.create(ImmutableMap.<Integer, Double>builder()
                        //vehicle specific consumption model <speed, consumption in kWh>
                        .put(10, 5.0)
                        .put(30, 10.0)
                        .put(50, 15.0)
                        .put(70, 20.0)
                        .put(90, 25.0)
                        .put(120, 30.0)
                        .build())
                ).build();
        //end::doc_consumption_model_electric[]
        return queryBuilder;
    }

    public static RouteQuery createRouteConsumptionCombustionQuery(RouteConfigExample routeConfig) {
        //tag::doc_consumption_model_combustion[]
        RouteQuery queryBuilder = RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withConsiderTraffic(false)
                .withMaxAlternatives(2)
                .withVehicleWeightInKg(1600) //vehicle weight in kilograms
                .withCurrentFuelInLiters(50.0)
                .withAuxiliaryPowerInLitersPerHour(0.2)
                .withFuelEnergyDensityInMJoulesPerLiter(34.2)
                .withAccelerationEfficiency(0.33) //e.g. KineticEnergyGained/ChemicalEnergyConsumed
                .withDecelerationEfficiency(0.33) //e.g. ChemicalEnergySaved/KineticEnergyLost
                .withUphillEfficiency(0.33) //e.g. PotentialEnergyGained/ChemicalEnergyConsumed
                .withDownhillEfficiency(0.33) //e.g. ChemicalEnergySaved/PotentialEnergyLost
                .withVehicleEngineType(VehicleEngineType.COMBUSTION)
                .withConstantSpeedConsumptionInLitersPerHundredKm(SpeedToConsumptionMap.create(ImmutableMap.<Integer, Double>builder()
                        //vehicle specific consumption model <speed, consumption in liters>
                        .put(10, 6.5)
                        .put(30, 7.0)
                        .put(50, 8.0)
                        .put(70, 8.4)
                        .put(90, 7.7)
                        .put(120, 7.5)
                        .put(150, 9.0)
                        .build())
                ).build();
        //end::doc_consumption_model_combustion[]
        return queryBuilder;
    }

    public static RouteQuery createRouteManeuversQuery(String language, RouteConfigExample routeConfig) {
        //tag::doc_route_language[]
        RouteQuery queryBuilder = RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withLanguage(language)
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withConsiderTraffic(false).build();
        //end::doc_route_language[]
        return queryBuilder;
    }

    public static RouteQuery createRouteTravelModesQuery(TravelMode travelMode, RouteConfigExample routeConfig) {
        //tag::doc_route_travel_mode[]
        RouteQuery queryBuilder = RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withMaxAlternatives(0)
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withTravelMode(travelMode)
                .withConsiderTraffic(false).build();
        //end::doc_route_travel_mode[]
        return queryBuilder;
    }

    public static RouteQuery createRouteTypesQuery(RouteType routeType, RouteConfigExample routeConfig) {
        //tag::doc_route_type[]
        RouteQuery queryBuilder = RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withMaxAlternatives(0)
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withRouteType(routeType)
                .withConsiderTraffic(false).build();
        //end::doc_route_type[]
        return queryBuilder;
    }

    public static RouteQuery createArrivalRouteQuery(Date arrivalTime, RouteConfigExample routeConfig) {
        //tag::doc_route_arrival_time[]
        RouteQuery queryBuilder = RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withArriveAt(arrivalTime)
                .withConsiderTraffic(false).build();
        //end::doc_route_arrival_time[]
        return queryBuilder;
    }

    public static RouteQuery createDepartureRouteQuery(Date departureTime, RouteConfigExample routeConfig) {
        //tag::doc_route_departure_time[]
        return RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withDepartAt(departureTime)
                .withConsiderTraffic(false).build();
        //end::doc_route_departure_time[]
    }

    public static RouteQuery createRouteAlternativesQuery(int maxAlternatives, RouteConfigExample routeConfig) {
        //tag::doc_route_alternatives[]
        RouteQuery queryBuilder = RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withMaxAlternatives(maxAlternatives)
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withConsiderTraffic(false).build();
        //end::doc_route_alternatives[]
        return queryBuilder;
    }

    public static RouteQuery createRouteForAlongRouteSearch(RouteConfigExample routeConfig) {
        return RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withMaxAlternatives(0)
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withRouteType(RouteType.FASTEST).build();
    }

    public static RouteQuery createBaseRouteForAvoidsVignettesAndAreas(RouteConfigExample routeConfig) {
        return RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withMaxAlternatives(0)
                .withReport(Report.NONE)
                .withInstructionsType(InstructionsType.NONE)
                .withConsiderTraffic(false)
                .build();

    }

    public static RouteQuery createRouteForAvoidsVignettesAndAreas(List<String> avoidVignettesList, RouteConfigExample routeConfig) {
        return RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withMaxAlternatives(0)
                .withReport(Report.NONE)
                .withInstructionsType(InstructionsType.NONE)
                .withConsiderTraffic(false)
                .withAvoidVignettes(avoidVignettesList)
                .build();
    }

    public static RouteQuery createRouteForAvoidsArea(BoundingBox avoidArea, RouteConfigExample routeConfig) {
        return RouteQueryBuilder.create(routeConfig.getOrigin(), routeConfig.getDestination())
                .withMaxAlternatives(0)
                .withReport(Report.NONE)
                .withInstructionsType(InstructionsType.NONE)
                .withConsiderTraffic(false)
                .withAvoidArea(avoidArea)
                .build();
    }
}
