package com.yousef.sampleVehiclesOnMap.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingUtils {
    @JvmStatic
    @BindingAdapter("android:srcCompat")
    fun bindSrcCompat(imageView: ImageView, drawable: Int) {
        imageView.setImageResource(drawable)
    }
    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, value: Boolean) {
        view.visibility = if(value) VISIBLE else GONE
    }
}