package com.example.recibodigitalalexventurini.screens

import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recibodigitalalexventurini.R
import com.example.recibodigitalalexventurini.model.User
import com.example.recibodigitalalexventurini.utils.ConstantsUtils

class HomeScreenActivity : AppCompatActivity() {
    private val TAG = ConstantsUtils.LOGTAG + "HomeScreenActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        setContentView(R.layout.home_screen)

        val user = intent.extras?.get(ConstantsUtils.USER_INFO_EXTRAS) as User.UserInfo

        Log.i(TAG, "onCreate() User Extra: " + user.name + " email: " + user.email)
    }
}