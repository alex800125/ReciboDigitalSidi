package com.example.recibodigitalalexventurini.utils

import android.app.Activity
import android.util.Log
import com.example.recibodigitalalexventurini.model.User
import com.example.recibodigitalalexventurini.screens.MainActivity
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.io.StringReader

class ApiServiceUtils {
    private val TAG = "ApiServiceUtils"
    private var gson = Gson()

    fun getRequestUserLogin(activity: Activity, url: String, user: User) {
        val body: RequestBody =
            RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                java.lang.String.valueOf(gson.toJson(user))
            )
        post(activity, body, url)
    }

    fun notifyOnResponse(response: Response) {
        val reader = StringReader(response.body!!.string())
        val userInfo = gson.fromJson(reader, User.UserInfo::class.java)

        val message =
            "Login executado. Nome: " + userInfo.first_name + " " + userInfo.last_name
        MainActivity().updateLoginSuccess(message)
    }

    fun notifyOnFailure() {
        MainActivity().updateLoginMessageError(false)
    }

    private fun post(activity: Activity, body: RequestBody, url: String) {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity.runOnUiThread(java.lang.Runnable {
                    Log.i(TAG, "onFailure: $e")
                    notifyOnFailure()
                })
            }


            override fun onResponse(call: Call, response: Response) {
                activity.runOnUiThread(java.lang.Runnable {
                    Log.i(TAG, "onResponse: " + response.message + " code: " + response.code)
                    if (response.isSuccessful) {
                        notifyOnResponse(response)
                    } else {
                        notifyOnFailure()
                    }
                })
            }
        })
    }
}