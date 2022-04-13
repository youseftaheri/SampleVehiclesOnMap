package com.yousef.sampleVehiclesOnMap.data.remote

import android.content.Context
import com.yousef.sampleVehiclesOnMap.data.local.prefs.PreferencesHelper
import com.yousef.sampleVehiclesOnMap.data.model.*
import com.yousef.sampleVehiclesOnMap.utils.CommonUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper
@Inject constructor(
    private val apis: Apis,
    private val prefrenceHepler: PreferencesHelper,
    private val mContext: Context,
    private val commonUtils: CommonUtils
) : ApiHelper {

    override suspend fun requestVehicleList(hamburgFirstLat:Double, hamburgFirstLong:Double,
                                            hamburgSecondLat:Double, hamburgSecondLong:Double): VehiclesPOJO? {
        return apis.requestVehicleList(hamburgFirstLat, hamburgFirstLong,
            hamburgSecondLat, hamburgSecondLong)
    }
}