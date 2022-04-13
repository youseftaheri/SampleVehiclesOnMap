package com.yousef.sampleVehiclesOnMap.ui.vehicleListFragment

interface VehicleListNavigator {
    fun handleError(exception: String?)
    fun showLoading()
    fun hideLoading()
    fun setCards()
    fun refresh()
}