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

package com.tomtom.online.sdk.samples.ktx.cases.route.ev

import android.app.Application
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.ev.EvRouteCallback
import com.tomtom.online.sdk.routing.ev.EvRoutePlan
import com.tomtom.online.sdk.routing.ev.EvRouteSpecification
import com.tomtom.online.sdk.routing.ev.charging.*
import com.tomtom.online.sdk.routing.ev.route.EvFullRoute
import com.tomtom.online.sdk.routing.route.ElectricVehicleDescriptor
import com.tomtom.online.sdk.routing.route.vehicle.ElectricVehicleConsumption
import com.tomtom.online.sdk.samples.ktx.cases.route.RouteViewModel
import com.tomtom.online.sdk.samples.ktx.cases.route.RoutingRequester
import com.tomtom.online.sdk.samples.ktx.utils.arch.Resource
import com.tomtom.online.sdk.samples.ktx.utils.arch.ResourceLiveData
import com.tomtom.online.sdk.samples.ktx.utils.routes.Locations

class LongEvViewModel(application: Application) : RouteViewModel(application) {

    internal val evRoutes = ResourceLiveData<List<EvFullRoute>>()
    internal var chargingWaypoints: List<ChargingWaypoint> = emptyList()
    internal var availableCharge: Double = 1.0

    internal fun planEvRouteShortRange() {
        planEvRoute(createShortRangeVehicle())
    }

    internal fun planEvRouteLongRange() {
        planEvRoute(createLongRangeVehicle())
    }

    private fun planEvRoute(evRouteDescriptors: Pair<ElectricVehicleDescriptor, ChargingDescriptor>) {
        val (evDescriptor, chargingDescriptor) = evRouteDescriptors

        //tag::doc_plan_ev_route[]
        val evRouteSpecification =
            EvRouteSpecification.Builder(Locations.AMSTERDAM, Locations.BERLIN, evDescriptor, chargingDescriptor)
                .build()
        //end::doc_plan_ev_route[]
        planRoute(evRouteSpecification, routeCallback)
    }

    private fun createShortRangeVehicle(): Pair<ElectricVehicleDescriptor, ChargingDescriptor> {
        availableCharge = 40.0
        //tag::doc_ev_short_range[]
        // SPEED_CONSUMPTION_IN_KWH_PER_100_KM = mapOf(77.0 to 32.0, 18.01 to 10.87)
        // AUXILIARY_POWER_IN_KW = 1.0
        val evDescriptor = ElectricVehicleDescriptor.Builder(
            ElectricVehicleConsumption(
                50.0,
                40.0,
                AUXILIARY_POWER_IN_KW,
                SPEED_CONSUMPTION_IN_KWH_PER_100_KM
            )
        ).build()

        val chargingDescriptor = ChargingDescriptor(
            minChargeAtDestinationInkWh = 2.0,
            minChargeAtChargingStopsInkWh = 4.0,
            chargingModes = listOf(
                ChargingMode(
                    listOf(
                        ChargingConnection(
                            FacilityType.CHARGE_200_TO_240V_1_PHASE_AT_16A,
                            PlugType.IEC_62196_TYPE_2_OUTLET
                        ),
                        ChargingConnection(
                            FacilityType.CHARGE_200_TO_240V_3_PHASE_AT_32A,
                            PlugType.IEC_62196_TYPE_2_OUTLET
                        ),
                        ChargingConnection(
                            FacilityType.CHARGE_380_TO_480V_3_PHASE_AT_32A,
                            PlugType.IEC_62196_TYPE_2_OUTLET
                        )
                    ),
                    listOf(
                        ChargingCurveSupportPoint(6.0, 360),
                        ChargingCurveSupportPoint(50.0, 4680)
                    )
                )
            )
        )
        //end::doc_ev_short_range[]
        return Pair(evDescriptor, chargingDescriptor)
    }

    private fun createLongRangeVehicle(): Pair<ElectricVehicleDescriptor, ChargingDescriptor> {
        availableCharge = 100.0
        //tag::doc_ev_long_range[]
        // SPEED_CONSUMPTION_IN_KWH_PER_100_KM = mapOf(77.0 to 32.0, 18.01 to 10.87)
        // AUXILIARY_POWER_IN_KW = 1.0
        val evDescriptor = ElectricVehicleDescriptor.Builder(
            ElectricVehicleConsumption(
                100.0,
                40.0,
                AUXILIARY_POWER_IN_KW,
                SPEED_CONSUMPTION_IN_KWH_PER_100_KM
            )
        ).build()

        val chargingDescriptor = ChargingDescriptor(
            minChargeAtDestinationInkWh = 4.0,
            minChargeAtChargingStopsInkWh = 8.0,
            chargingModes = listOf(
                ChargingMode(
                    listOf(
                        ChargingConnection(
                            FacilityType.CHARGE_200_TO_240V_1_PHASE_AT_16A,
                            PlugType.IEC_62196_TYPE_2_OUTLET
                        ),
                        ChargingConnection(
                            FacilityType.CHARGE_200_TO_240V_3_PHASE_AT_32A,
                            PlugType.IEC_62196_TYPE_2_OUTLET
                        ),
                        ChargingConnection(
                            FacilityType.CHARGE_380_TO_480V_3_PHASE_AT_32A,
                            PlugType.IEC_62196_TYPE_2_OUTLET
                        )
                    ),
                    listOf(
                        ChargingCurveSupportPoint(6.0, 360),
                        ChargingCurveSupportPoint(100.0, 6680)
                    )
                )
            )
        )
        //end::doc_ev_long_range[]
        return Pair(evDescriptor, chargingDescriptor)
    }

    private fun planRoute(evRouteSpecification: EvRouteSpecification, evRouteCallback: EvRouteCallback) {
        evRoutes.value = Resource.loading(null)
        RoutingRequester(getApplication()).planRoute(evRouteSpecification, evRouteCallback)
    }

    private val routeCallback = object : EvRouteCallback {
        override fun onSuccess(routePlan: EvRoutePlan) {
            chargingWaypoints = getChargingPoints(routePlan.routes)
            availableCharge = getAvailableCharge(routePlan.routes)
            evRoutes.value = Resource.success(routePlan.routes)
        }

        override fun onError(error: RoutingException) {
            evRoutes.value = Resource.error(null, Error(error.message))
        }
    }

    private fun getAvailableCharge(routes: List<EvFullRoute>): Double {
        val remainingCharge = routes.first().routeSummary?.summary?.remainingChargeAtArrivalInkWh ?: 0.0
        return (remainingCharge / availableCharge) * 100
    }

    private fun getChargingPoints(evRoutes: List<EvFullRoute>): List<ChargingWaypoint> {
        val legs = evRoutes.first().legs
        return legs.subList(0, legs.size - 1)
            .map {
                val chargingTime = it.legSummary?.chargingInformationAtEndOfLeg?.chargingTime ?: 0
                val chargingTimeInMinutes = chargingTime / SECONDS_IN_MINUTES
                ChargingWaypoint(it.points.last(), chargingTimeInMinutes)
            }
    }

    companion object {
        private const val SECONDS_IN_MINUTES = 60
        private const val AUXILIARY_POWER_IN_KW = 1.0
        private val SPEED_CONSUMPTION_IN_KWH_PER_100_KM = mapOf(77.0 to 32.0, 18.01 to 10.87)
    }
}