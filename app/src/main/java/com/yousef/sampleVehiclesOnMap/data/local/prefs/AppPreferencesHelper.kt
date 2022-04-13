package com.yousef.sampleVehiclesOnMap.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO
import com.yousef.sampleVehiclesOnMap.di.PreferenceInfo
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(
    context: Context,
    @PreferenceInfo prefFileName: String?) : PreferencesHelper {
    private val mPrefs: SharedPreferences

    companion object {
        private const val VEHICLE_lIST = "VEHICLE_lIST"
    }

    override fun clear() {
        mPrefs.edit().clear().apply()
    }

    init {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }


    //<------------------------------------------------------------------------------->\\
    //Store & retrieve vehicle list
    //<-------------------------------------------------------------------------------->\\
    override var vehicleList: VehiclesPOJO?
        get() = Gson().fromJson(mPrefs.getString(VEHICLE_lIST, null), VehiclesPOJO::class.java)
        set(data) {
            mPrefs.edit().putString(VEHICLE_lIST, Gson().toJson(data)).apply()
        }
}