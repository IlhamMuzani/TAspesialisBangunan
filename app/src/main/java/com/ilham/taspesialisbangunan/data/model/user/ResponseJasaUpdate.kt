package com.ilham.taspesialisbangunan.data.model.user

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.user.DataUser

data class ResponseJasaUpdate (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
//    @SerializedName("user") val user: DataUser?
)