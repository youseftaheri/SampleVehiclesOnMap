package com.yousef.sampleVehiclesOnMap.ui.splashFragment

interface SplashNavigator {
    fun handleError(exception: String?)
    fun showLoading()
    fun hideLoading()
}