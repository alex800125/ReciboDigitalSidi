package com.example.recibodigitalalexventurini.model

import com.google.gson.annotations.SerializedName

// Example Category:
// "categories": [...]

data class UserCreateResponse(
    @SerializedName("code")
    var code: String,
    @SerializedName("resultMessage")
    var resultMessage: String
)