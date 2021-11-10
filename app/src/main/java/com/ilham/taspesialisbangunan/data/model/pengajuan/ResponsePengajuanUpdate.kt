package com.ilham.taspesialisbangunan.data.model.pengajuan

import com.google.gson.annotations.SerializedName

class ResponsePengajuanUpdate (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String
)