package com.ilham.taspesialisbangunan.data.model.saran

import com.google.gson.annotations.SerializedName

data class ResponseSarandetail (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("saran") val user: DataSaran
)