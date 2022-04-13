package com.yousef.sampleVehiclesOnMap.ui.splashFragment

import android.annotation.SuppressLint
import com.yousef.sampleVehiclesOnMap.data.DataManager
import com.yousef.sampleVehiclesOnMap.ui.base.BaseViewModel
import com.yousef.sampleVehiclesOnMap.utils.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) : BaseViewModel<SplashNavigator?>(dataManager!!, schedulerProvider!!) {
    @SuppressLint("CheckResult")
    fun fetchSplash() {
    }
}