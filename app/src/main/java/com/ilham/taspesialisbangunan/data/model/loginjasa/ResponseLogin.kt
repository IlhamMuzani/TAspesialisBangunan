package com.ilham.taspesialisbangunan.data.model.loginjasa

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("jasauser") val jasauser: DataLogin?
)