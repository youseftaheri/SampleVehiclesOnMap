package com.yousef.sampleVehiclesOnMap.data.remote

import com.yousef.sampleVehiclesOnMap.data.model.*

interface ApiHelper {
    suspend fun requestVehicleList(hamburgFirstLat:Double, hamburgFirstLong:Double,
                                 hamburgSecondLat:Double, hamburgSecondLong:Double): VehiclesPOJO?
}