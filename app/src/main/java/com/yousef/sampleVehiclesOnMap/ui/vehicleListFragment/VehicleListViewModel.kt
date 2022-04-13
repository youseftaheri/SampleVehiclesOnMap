package com.yousef.sampleVehiclesOnMap.ui.vehicleListFragment

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yousef.sampleVehiclesOnMap.data.DataManager
import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO
import com.yousef.sampleVehiclesOnMap.ui.base.BaseViewModel
import com.yousef.sampleVehiclesOnMap.utils.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class VehicleListViewModel
@Inject
constructor(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) : BaseViewModel<VehicleListNavigator?>(dataManager!!, schedulerProvider!!) {
    private val fruitLisItemsLiveData: MutableLiveData<MutableList<VehiclesPOJO.Vehicle>?> = MutableLiveData()

    @JvmField
    val emptyMessageVisibility: ObservableField<Boolean> = ObservableField(false)

    @JvmField
    val refreshVisibility: ObservableField<Boolean> = ObservableField(false)

    init {
        fruitLisItemsLiveData.value = mutableListOf()
    }
    
    fun fetchVehiclesData(hamburgFirstLat:Double, hamburgFirstLong:Double,
                          hamburgSecondLat:Double, hamburgSecondLong:Double) {
            navigator!!.showLoading()
            viewModelScope.launch(Dispatchers.Main) { // launches coroutine in main thread
                try {
                    val vehiclesPOJO =
                        dataManager.requestVehicleList(hamburgFirstLat, hamburgFirstLong,
                            hamburgSecondLat, hamburgSecondLong)
                    navigator!!.hideLoading()
                    if (vehiclesPOJO != null)
                        if (vehiclesPOJO.poiList!!.isEmpty())
                            emptyMessageVisibility.set(true)
                        else {
                            emptyMessageVisibility.set(false)
                            dataManager.vehicleList = vehiclesPOJO
                            fruitLisItemsLiveData.value = vehiclesPOJO.poiList as MutableList<VehiclesPOJO.Vehicle>?
                            navigator!!.setCards()
                        }
                } catch (e: Exception) {
                    refreshVisibility.set(true)
                    navigator!!.hideLoading()
                    navigator!!.handleError(e.message)
                }
            }
    }

    fun getCardItemsLiveData(): LiveData<MutableList<VehiclesPOJO.Vehicle>?> {
        return fruitLisItemsLiveData
    }

    fun refresh() {
        navigator!!.refresh()
        refreshVisibility.set(false)
    }
}