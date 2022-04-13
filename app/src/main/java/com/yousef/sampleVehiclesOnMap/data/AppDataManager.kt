package com.yousef.sampleVehiclesOnMap.data

import android.content.Context
import com.google.gson.Gson
import com.yousef.sampleVehiclesOnMap.data.local.prefs.PreferencesHelper
import com.yousef.sampleVehiclesOnMap.data.model.*
import com.yousef.sampleVehiclesOnMap.data.remote.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager
@Inject constructor(
    private val mContext: Context,
    private val mPreferencesHelper: PreferencesHelper,
    private val mApiHelper: ApiHelper,
    private val mGson: Gson) : DataManager {

    companion object {
        private const val TAG = "AppDataManager"
    }

    override fun clear() {
        mPreferencesHelper.clear()
    }

    override var vehicleList: VehiclesPOJO?
        get() = mPreferencesHelper.vehicleList
        set(data) {
            mPreferencesHelper.vehicleList = data
        }

    override suspend fun requestVehicleList(hamburgFirstLat:Double, hamburgFirstLong:Double,
                                            hamburgSecondLat:Double, hamburgSecondLong:Double): VehiclesPOJO? {
        return mApiHelper.requestVehicleList(hamburgFirstLat, hamburgFirstLong,
            hamburgSecondLat, hamburgSecondLong)
    }
}