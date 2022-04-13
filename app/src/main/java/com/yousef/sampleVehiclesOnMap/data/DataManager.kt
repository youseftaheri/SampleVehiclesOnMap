package com.yousef.sampleVehiclesOnMap.data

import androidx.lifecycle.MediatorLiveData
import com.yousef.sampleVehiclesOnMap.data.local.prefs.PreferencesHelper
import com.yousef.sampleVehiclesOnMap.data.remote.ApiHelper

interface DataManager : PreferencesHelper, ApiHelper {

    companion object {
        val mData = MediatorLiveData<String>()
    }
}