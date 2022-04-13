package com.yousef.sampleVehiclesOnMap.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class VehiclesPOJO : Parcelable {
    @JvmField
    @Expose
    @SerializedName("poiList")
    var poiList: List<Vehicle>? = null

    @Parcelize
    class Vehicle : Parcelable {
        @JvmField
        @Expose
        @SerializedName("id")
        var id: Int? = null

        @JvmField
        @Expose
        @SerializedName("coordinate")
        var coordinate: Coordinate? = null

        @JvmField
        @Expose
        @SerializedName("fleetType")
        var fleetType: String? = null

        @JvmField
        @Expose
        @SerializedName("heading")
        var heading: Double? = null
    }

    @Parcelize
    class Coordinate : Parcelable {
        @JvmField
        @Expose
        @SerializedName("latitude")
        var latitude: Double? = null

        @JvmField
        @Expose
        @SerializedName("longitude")
        var longitude: Double? = null
    }
}