package com.example.recibodigitalalexventurini.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.recibodigitalalexventurini.model.User
import com.example.recibodigitalalexventurini.screens.HomeScreenActivity
import com.example.recibodigitalalexventurini.screens.LoginScreenActivity
import com.example.recibodigitalalexventurini.utils.ConstantsUtils.Companion.EMPTY_STRING
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.io.StringReader

class ApiServiceUtils {
    private val TAG = ConstantsUtils.LOGTAG + "ApiServiceUtils"
    private var mGson = Gson()
    private var mActivity: Activity? = null

    fun getRequestUserLogin(activity: Activity, url: String, user: User) {
        mActivity = activity
        val body: RequestBody =
            RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                java.lang.String.valueOf(mGson.toJson(user))
            )
        post(body, url, EMPTY_STRING, ConstantsUtils.typePost.USER)
    }

    fun getRequestReceipt(activity: Activity, url: String, token: String) {
        mActivity = activity
        post(null, url, token, ConstantsUtils.typePost.RECEIPT)
    }

    private fun post(
        body: RequestBody?,
        url: String,
        token: String,
        type: ConstantsUtils.typePost
    ) {
        val okHttpClient = OkHttpClient()
        val request = if (body != null)
            Request.Builder()
                .addHeader("Authorization", token)
                .url(url)
                .post(body)
                .build()
        else {
            Request.Builder()
                .addHeader("Authorization", "Bearer $token")
                .url(url)
                .build()
        }

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "onFailure: $e")
                notifyRequestUserLoginFailure()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(
                    TAG, "onResponse: " + response.message + " code: " + response.code +
                            " type: " + type
                )
                when (type) {
                    ConstantsUtils.typePost.USER -> {
                        if (response.isSuccessful) {
                            notifyRequestUserLoginSuccessful(response)
                        } else {
                            notifyRequestUserLoginFailure()
                        }
                    }
                    ConstantsUtils.typePost.RECEIPT -> {
                        if (response.isSuccessful) {
                            notifyRequestReceiptSuccessful(response)
                        } else {
                            notifyRequestReceiptFailure()
                        }
                    }
                    ConstantsUtils.typePost.CATEGORY -> {}
                }
            }
        })
    }

    fun notifyRequestUserLoginSuccessful(response: Response) {
        Log.i(TAG, "notifyRequestUserLoginSuccessful")
        val reader = StringReader(response.body!!.string())
        val userInfo = mGson.fromJson(reader, User.UserInfo::class.java)

        val message =
            "Login executado. Nome: " + userInfo.name + " email: " + userInfo.email
        Log.i(TAG, message)

        val homeScreen = Intent(mActivity, HomeScreenActivity::class.java)
        homeScreen.putExtra(ConstantsUtils.USER_INFO_EXTRAS, userInfo)
        mActivity?.startActivity(homeScreen)

        // TODO tentar ver pq não está rolando por esse forma, crasha a aplicação
        // LoginScreenActivity().updateLoginSuccess(mActivity, message)
    }

    fun notifyRequestUserLoginFailure() {
        Log.i(TAG, "notifyRequestUserLoginFailure")
        LoginScreenActivity().updateLoginMessageError(false)
    }

    fun notifyRequestReceiptSuccessful(response: Response) {
        Log.i(TAG, "notifyRequestReceiptSuccessful")
        //HomeScreenActivity().runOnUiThread(java.lang.Runnable {
        //    HomeScreenActivity().updateReceipt()
        //})
    }

    fun notifyRequestReceiptFailure() {
        Log.i(TAG, "notifyRequestReceiptFailure")
    }
}