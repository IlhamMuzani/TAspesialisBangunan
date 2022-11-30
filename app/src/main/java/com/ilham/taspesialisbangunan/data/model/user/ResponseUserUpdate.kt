package com.ilham.taspesialisbangunan.data.model.user

import com.google.gson.annotations.SerializedName

data class ResponseUserUpdate (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
)