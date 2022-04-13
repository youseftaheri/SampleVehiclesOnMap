package com.yousef.sampleVehiclesOnMap.ui.mapFragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.yousef.sampleVehiclesOnMap.BR
import com.yousef.sampleVehiclesOnMap.R
import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO
import com.yousef.sampleVehiclesOnMap.databinding.FragmentMapBinding
import com.yousef.sampleVehiclesOnMap.ui.base.BaseFragment
import com.yousef.sampleVehiclesOnMap.utils.Const.DEFAULT_ZOOM
import com.yousef.sampleVehiclesOnMap.utils.Const.ERROR_DIALOG_REQUEST
import com.yousef.sampleVehiclesOnMap.utils.MyToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(), MapNavigator,
    OnMapReadyCallback, GoogleMap.OnCameraIdleListener{

    private var locationPermissionGranted: Boolean = false
    private lateinit var map: GoogleMap
    private var isFirstLoading = true
    private val mapMarkerToData: HashMap<Marker, VehiclesPOJO.Vehicle> = HashMap()
    private lateinit var currentLatLng: LatLng
    private lateinit var selectedVehicle: VehiclesPOJO.Vehicle
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)
    }

    private var mFragmentMapBinding: FragmentMapBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_map

    override fun getViewModelClass() = MapViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentMapBinding = viewDataBinding
        initMap()
        val args: MapFragmentArgs? = arguments?.let{
            MapFragmentArgs.fromBundle(it)
        }
        selectedVehicle = args!!.selectedVehicle
    }

    override fun back() {
        requireActivity().onBackPressed()
    }

    override fun setMarkers(vehicles: List<VehiclesPOJO.Vehicle>?) {
        if (vehicles!!.isNotEmpty()) {
            map.clear()
            for (i in vehicles.indices) {
                val responseLatLng: LatLng =
                    LatLng(vehicles[i].coordinate!!.latitude!!,
                        vehicles[i].coordinate!!.longitude!!)
                val marker = MarkerOptions()
                    .position(responseLatLng)
                    .icon(if(vehicles[i].fleetType == "POOLING")
                        BitmapDescriptorFactory.fromResource(R.drawable.pooling_marker_icon)
                    else
                        BitmapDescriptorFactory.fromResource(R.drawable.taxi_marker_icon))
                mapMarkerToData[map.addMarker(marker)!!] = vehicles[i]
            }
        } else if (vehicles.isNullOrEmpty()) {
            Log.d(TAG, "An empty or null list was returned.")
        }
    }

    override fun onCameraIdle() {
        if(!isFirstLoading) {
            val currentScreen: LatLngBounds = map.projection.visibleRegion.latLngBounds
            val northeast = currentScreen.northeast
            val southwest = currentScreen.southwest
            viewModel.searchByBounds(
                northeast.latitude, northeast.longitude,
                southwest.latitude, southwest.longitude
            )
        }else
            isFirstLoading = false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setListeners()
        viewModel.setFirstMarkers()
        val coordinate =
            LatLng(selectedVehicle.coordinate!!.latitude!!,
                selectedVehicle.coordinate!!.longitude!!) //Store these lat lng values somewhere. These should be constant.
        val location = CameraUpdateFactory.newLatLngZoom(coordinate, DEFAULT_ZOOM)
        map.animateCamera(location)
//        isFirstLoading = false
    }

    /**
     *  Gets the SupportMapFragment and request notification when map is ready to be used
     */
    private fun initMap() {
        Log.d(TAG, "initMap: called")
        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun checkMap(): Boolean {
        if (isServicesOK()) {
            return true
        }
        return false
    }

    private fun setListeners() {
        map.setOnCameraIdleListener(this)
    }

    /**
     *  Makes sure that google services is installed on the device
     */
    private fun isServicesOK(): Boolean {

        Log.d(TAG, "isServicesOK: checking google services version")

        val available: Int =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireContext())

        when {
            available == ConnectionResult.SUCCESS -> {
                Log.d(TAG, "isServicesOK: Google Play Services is working")
                return true
            }
            GoogleApiAvailability.getInstance().isUserResolvableError(available) -> {
                Log.d(TAG, "isServicesOK: an error occurred")

                val dialog: Dialog? = GoogleApiAvailability.getInstance()
                    .getErrorDialog(requireActivity(), available, ERROR_DIALOG_REQUEST)
                dialog!!.show()
            }
            else -> {
                MyToast.show(activity, "You can't make map requests",false)
            }
        }
        return false
    }

    companion object {
        private const val TAG = "MapFragment"
        fun newInstance(): MapFragment {
            val args = Bundle()
            val fragment = MapFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


