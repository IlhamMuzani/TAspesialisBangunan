package com.ilham.taspesialisbangunan.data.model.loginuser

import com.google.gson.annotations.SerializedName

data class ResponseLoginuser(
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("userpelanggan") val userpelanggan: DataLoginuser?
)