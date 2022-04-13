package com.yousef.sampleVehiclesOnMap.ui.vehicleListFragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yousef.sampleVehiclesOnMap.BR
import com.yousef.sampleVehiclesOnMap.R
import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO
import com.yousef.sampleVehiclesOnMap.databinding.FragmentVehicleListBinding
import com.yousef.sampleVehiclesOnMap.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleListFragment : BaseFragment<FragmentVehicleListBinding, VehicleListViewModel>(),
    VehicleListNavigator, VehicleListFragmentCallback {
    private var mFragmentVehicleListBinding: FragmentVehicleListBinding? = null
    var mLayoutManager: LinearLayoutManager? = null
    var hamburgFirstLat:Double = 53.694865
    var hamburgFirstLong:Double = 9.757589
    var hamburgSecondLat:Double = 53.394655
    var hamburgSecondLong:Double = 10.099891

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_vehicle_list
    override fun getViewModelClass() = VehicleListViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFragmentVehicleListBinding = viewDataBinding
        mLayoutManager = LinearLayoutManager(this.context)
        viewModel.fetchVehiclesData(hamburgFirstLat, hamburgFirstLong,
            hamburgSecondLat, hamburgSecondLong)
    }

    override fun refresh() {
        viewModel.fetchVehiclesData(hamburgFirstLat, hamburgFirstLong,
            hamburgSecondLat, hamburgSecondLong)
    }

    override fun setCards() {
        mLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        mFragmentVehicleListBinding!!.recyclerView.layoutManager = mLayoutManager
        mFragmentVehicleListBinding!!.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
        }
        mFragmentVehicleListBinding!!.recyclerView.itemAnimator = DefaultItemAnimator()
        mFragmentVehicleListBinding!!.recyclerView.adapter = VehicleListAdapter(viewModel.getCardItemsLiveData().value, this)
        //to prevent animation lag
        mFragmentVehicleListBinding!!.clRoot.setLayerType(View.LAYER_TYPE_HARDWARE, null)
    }

    override fun showMap(selectedVehicle: VehiclesPOJO.Vehicle?){
        utils!!.showDialog(context,"Do you want to see it on map?",
            "yes", "no", null,
            { _: DialogInterface?, _: Int ->
                selectedVehicle?.let {
                    findNavController().navigate(
                        VehicleListFragmentDirections.actionVehicleListFragmentToMapFragment(it))
                }
            },
            { dialog: DialogInterface, _: Int -> dialog.dismiss() },
            null, true)
    }

    companion object {
        fun newInstance(): VehicleListFragment {
            val args = Bundle()
            val fragment = VehicleListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}