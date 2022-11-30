package com.ilham.taspesialisbangunan.data.model.alamat

import com.google.gson.annotations.SerializedName


data class ResponseALamatList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("alamat") val dataAlamat: List<DataAlamat>
)