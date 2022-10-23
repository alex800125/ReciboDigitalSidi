package com.example.recibodigitalalexventurini.utils

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

class Utils {
    companion object {
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

        fun getImageFromUrl(url: URL): Bitmap? {
            return BitmapFactory.decodeStream(url.openConnection().getInputStream())
        }
    }
}