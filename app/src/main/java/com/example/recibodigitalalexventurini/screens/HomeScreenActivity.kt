package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.model.LoginResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils


class HomeScreenActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "HomeScreenActivity"

//    val mActivity: HomeScreenActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.home_screen)

        val user = intent.extras?.get(ConstantsUtils.USER_INFO_EXTRAS) as LoginResponse

        // ApiServiceUtils().getRequestReceipt(ConstantsUtils.URL_RECEIPT, user.token)
        Log.i(TAG, "onCreate() user.token: " + user.token)
    }

    fun updateReceipt() {
//        this.runOnUiThread(Runnable() {
//            Log.i(TAG, "updateReceipt()")
//        })

//        mActivity.runOnUiThread(Runnable {
//            Log.i(TAG, "updateReceipt()")
//        })

    }
}