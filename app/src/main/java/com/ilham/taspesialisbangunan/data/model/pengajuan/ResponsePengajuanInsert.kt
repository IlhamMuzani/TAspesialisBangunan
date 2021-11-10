package com.ilham.taspesialisbangunan.data.model.pengajuan

import com.google.gson.annotations.SerializedName

data class ResponsePengajuanInsert (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String
)