package com.example.recibodigitalalexventurini.utils

import android.app.Activity
import android.app.ProgressDialog

class Utils {

    private var dialog: ProgressDialog? = null

    // TODO implement loading screen

    fun startLoadingScreen(activity: Activity) {
        dialog = ProgressDialog(activity)
        dialog!!.setMessage("loading...")
        dialog!!.setCancelable(false)
        dialog!!.setInverseBackgroundForced(false)
        dialog!!.show()
    }

    fun removeLoadingScreen() {
        dialog!!.hide()
    }
}