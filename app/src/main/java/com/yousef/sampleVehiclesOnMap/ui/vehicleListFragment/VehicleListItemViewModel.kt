package com.yousef.sampleVehiclesOnMap.ui.vehicleListFragment

import androidx.databinding.ObservableField
import com.yousef.sampleVehiclesOnMap.R
import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO

class VehicleListItemViewModel (private val mVehicle: VehiclesPOJO.Vehicle,
                                private val mListener: VehicleListItemViewModelListener) {

    @JvmField
    val fleetType: ObservableField<String?> = ObservableField(mVehicle.fleetType)

    @JvmField
    val fleetImage: ObservableField<Int?> = ObservableField(
        if(mVehicle.fleetType == "POOLING")
            R.drawable.pooling_image
        else
            R.drawable.taxi_image
            )

    @JvmField
    val latitude: ObservableField<String?> = ObservableField(mVehicle.coordinate!!.latitude.toString())

    @JvmField
    val longitude: ObservableField<String?> = ObservableField(mVehicle.coordinate!!.longitude.toString())

    fun onClick() {
        mListener.onClick()
    }

    interface VehicleListItemViewModelListener {
        fun onClick()
    }

}
