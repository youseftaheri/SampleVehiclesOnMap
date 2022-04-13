package com.yousef.sampleVehiclesOnMap.data.local.prefs

import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO

interface PreferencesHelper {
    fun clear()
    var vehicleList: VehiclesPOJO?
}