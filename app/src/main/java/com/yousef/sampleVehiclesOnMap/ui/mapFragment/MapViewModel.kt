package com.yousef.sampleVehiclesOnMap.ui.mapFragment

import androidx.lifecycle.viewModelScope
import com.yousef.sampleVehiclesOnMap.data.DataManager
import com.yousef.sampleVehiclesOnMap.ui.base.BaseViewModel
import com.yousef.sampleVehiclesOnMap.utils.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel
@Inject
constructor(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) : BaseViewModel<MapNavigator?>(dataManager!!, schedulerProvider!!) {
    companion object {
        const val TAG = "MapViewModel"
    }

    fun setFirstMarkers() {
        navigator!!.setMarkers(dataManager.vehicleList!!.poiList)
    }

    fun searchByBounds(firstLat:Double, firstLng:Double,
                       secondLat:Double, secondLng:Double){
        viewModelScope.launch (Dispatchers.Main) { // launches coroutine in main thread
            try {
                val locationsPOJO = dataManager.requestVehicleList(
                    firstLat, firstLng, secondLat, secondLng)
                if (locationsPOJO != null)
                        navigator!!.setMarkers(locationsPOJO.poiList)
            } catch (e: Exception) {
                navigator!!.handleError(e.message)
            }
        }
    }

    fun back() {
        navigator!!.back()
    }
}