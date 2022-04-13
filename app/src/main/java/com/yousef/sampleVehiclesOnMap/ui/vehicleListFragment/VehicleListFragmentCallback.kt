package com.yousef.sampleVehiclesOnMap.ui.vehicleListFragment

import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO

interface VehicleListFragmentCallback {
    fun showMap(selectedVehicle: VehiclesPOJO.Vehicle?)
}
