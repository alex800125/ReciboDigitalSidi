package com.example.recibodigitalalexventurini.utils

import android.app.Activity
import android.util.Log
import com.example.recibodigitalalexventurini.model.User
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.io.StringReader

class ApiServiceUtils {
    private val TAG = "ApiServiceUtils"

    var gson = Gson()

    fun getRequestLogin(activity: Activity, url: String, user: User) {
        val body: RequestBody =
            RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                java.lang.String.valueOf(gson.toJson(user))
            )

        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "onFailure: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(TAG, "onResponse: " + response.body.toString() + " code: " + response.code)

                if (response.isSuccessful) {
                    val reader = StringReader(response.body!!.string())
                    var userEntity = gson.fromJson(reader, User.UserInfo::class.java)
                    val text =
                        "Login executado. Nome: " + userEntity.first_name + " " + userEntity.last_name
                    Log.i(TAG, text)
                }
            }
        })
    }
}