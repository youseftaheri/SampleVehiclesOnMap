package com.yousef.sampleVehiclesOnMap.ui.mainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.yousef.sampleVehiclesOnMap.BR
import com.yousef.sampleVehiclesOnMap.R
import com.yousef.sampleVehiclesOnMap.databinding.ActivityMainBinding
import com.yousef.sampleVehiclesOnMap.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator{
    private var mActivityMainBinding: ActivityMainBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun getViewModelClass() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
        mActivityMainBinding = viewDataBinding
        viewModel.entry
    }

    override fun onResume() {
        super.onResume()
        viewModel.entry
    }
    override fun setUp() {
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
