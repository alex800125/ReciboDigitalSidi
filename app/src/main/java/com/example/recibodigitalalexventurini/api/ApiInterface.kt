package com.example.recibodigitalalexventurini.api

import com.example.recibodigitalalexventurini.model.LoginResponse
import com.example.recibodigitalalexventurini.utils.ConstantsUtils.Companion.URL_USER_LOGIN
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST(URL_USER_LOGIN)
    fun userLogin(
        @Field("email") email: String,
        @Field("pass") pass: String
    ): Call<LoginResponse>
}