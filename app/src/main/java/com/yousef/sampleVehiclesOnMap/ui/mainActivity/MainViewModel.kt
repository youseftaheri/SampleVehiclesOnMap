package com.yousef.sampleVehiclesOnMap.ui.mainActivity

import com.yousef.sampleVehiclesOnMap.data.DataManager
import com.yousef.sampleVehiclesOnMap.ui.base.BaseViewModel
import com.yousef.sampleVehiclesOnMap.utils.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) : BaseViewModel<MainNavigator?>(dataManager!!, schedulerProvider!!) {

    val entry: Unit
        get() {
            navigator!!.setUp()
        }
}
