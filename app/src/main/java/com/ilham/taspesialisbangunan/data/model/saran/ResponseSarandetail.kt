package com.ilham.taspesialisbangunan.data.model.user

import com.google.gson.annotations.SerializedName

data class ResponseUserdetail (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("user") val user: DataUser
)