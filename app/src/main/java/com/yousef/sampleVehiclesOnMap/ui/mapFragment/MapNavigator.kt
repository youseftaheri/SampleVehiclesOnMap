package com.yousef.sampleVehiclesOnMap.ui.mapFragment

import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO

interface MapNavigator {
    fun handleError(exception: String?)
    fun setMarkers(vehicles: List<VehiclesPOJO.Vehicle>?)
    fun back()
}