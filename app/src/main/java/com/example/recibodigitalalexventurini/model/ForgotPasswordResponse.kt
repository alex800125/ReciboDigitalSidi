package com.example.recibodigitalalexventurini.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("code")
    var code: String,
    @SerializedName("resultMessage")
    var resultMessage: String
)