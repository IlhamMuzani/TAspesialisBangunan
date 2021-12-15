package com.ilham.taspesialisbangunan.data.model.loginuser

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("user") val user: DataLogin?
)