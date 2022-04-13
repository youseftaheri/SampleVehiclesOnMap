package com.yousef.sampleVehiclesOnMap.data.remote

import com.yousef.sampleVehiclesOnMap.data.model.*
import retrofit2.http.*

interface Apis {

    @GET("?")
    suspend fun requestVehicleList(@Query("p1Lat") hamburgFirstLat: Double,
                                   @Query("p1Lon") hamburgFirstLong: Double,
                                   @Query("p2Lat") hamburgSecondLat: Double,
                                   @Query("p2Lon") hamburgSecondLong: Double): VehiclesPOJO?
}