package com.yousef.sampleVehiclesOnMap.ui.vehicleListFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yousef.sampleVehiclesOnMap.data.model.VehiclesPOJO
import com.yousef.sampleVehiclesOnMap.databinding.ItemVehicleListViewBinding
import com.yousef.sampleVehiclesOnMap.ui.base.BaseAdapter
import com.yousef.sampleVehiclesOnMap.ui.base.BaseViewHolder

class VehicleListAdapter(cardListItemViewModelList: List<VehiclesPOJO.Vehicle?>?, callback: VehicleListFragmentCallback) :
    BaseAdapter<VehiclesPOJO.Vehicle?>() {
    var callback: VehicleListFragmentCallback? = callback

    override fun onBindViewHolderBase(holder: BaseViewHolder?, position: Int) {
        holder!!.onBind(position)
    }

    override fun onCreateViewHolderBase(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        val mCardListViewBinding = ItemVehicleListViewBinding
            .inflate(LayoutInflater.from(parent!!.context), parent, false)
        return CardListViewHolder(mCardListViewBinding)
    }

    inner class CardListViewHolder(var mBinding: ItemVehicleListViewBinding) : BaseViewHolder(mBinding.root),
        VehicleListItemViewModel.VehicleListItemViewModelListener {
        private var vehicleListItemViewModel: VehicleListItemViewModel? = null
        override fun onBind(position: Int) {
            val card = dataList!![position]!!
            vehicleListItemViewModel = VehicleListItemViewModel(card, this)
            mBinding.viewModel = vehicleListItemViewModel
            mBinding.executePendingBindings()
        }

        override fun onClick() {
            dataList!![layoutPosition]!!.let {
                callback!!.showMap(
                    it
                )
            }
        }

//        override fun setFleetImage(status: String?) {
//            mBinding.cvLabel.setCardBackgroundColor(
//                if(status == "تایید شده")
//                    Color.parseColor("#73FF94")
//                else
//                    Color.parseColor("#FD9C95"))
//        }

    }

    init {
        dataList = cardListItemViewModelList
    }
}
