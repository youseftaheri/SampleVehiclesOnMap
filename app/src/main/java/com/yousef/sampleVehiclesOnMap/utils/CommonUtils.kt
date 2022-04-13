package com.yousef.sampleVehiclesOnMap.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import com.yousef.sampleVehiclesOnMap.R
import java.util.*

object CommonUtils {
    @JvmStatic
    fun showLoadingDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val wlmp = Objects.requireNonNull(progressDialog.window)?.attributes
            if (wlmp != null) {
                wlmp.gravity = Gravity.CENTER_HORIZONTAL
            }
            progressDialog.window!!.attributes = wlmp
        }
        progressDialog.setContentView(R.layout.progress_spinner)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    /**
     * Show alert dialog
     *
     * @param mContext         Context of activity o fragment
     * @param message          Message that shows on Dialog
     * // * @param title            Title for dialog
     * @param positiveText     Set positive text
     * @param positiveListener Set functionality on positive button click
     * @param negativeListener Set functionality on negative button click
     * @param negativeText     Negative button text
     * @param neutralText      Neturat button text
     * @param neutralListener  Set Netural button listener
     * @param isCancelable     true -> Cancelable True ,false -> Cancelable False
     * @return dialog
     */
//    @JvmStatic
    fun showDialog(
        mContext: Context?, message: String?, positiveText: String?,
        negativeText: String?, neutralText: String?,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?,
        neutralListener: DialogInterface.OnClickListener?,
        isCancelable: Boolean
    ): AlertDialog.Builder {
        val alert = AlertDialog.Builder(mContext!!)
        //alert.setTitle(mContext.getString(R.string.app_name) + " Says :");
        alert.setMessage(message)
        alert.setNegativeButton(negativeText, negativeListener)
        alert.setPositiveButton(positiveText, positiveListener)
        alert.setNeutralButton(neutralText, neutralListener)
        alert.setCancelable(isCancelable)
        alert.show()
        return alert
    }
}