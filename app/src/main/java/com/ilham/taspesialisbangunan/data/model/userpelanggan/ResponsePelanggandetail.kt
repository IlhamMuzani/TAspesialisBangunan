package com.ilham.taspesialisbangunan.data.model.userpelanggan

import com.google.gson.annotations.SerializedName

data class ResponsePelanggandetail (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("data") val dataPelanggan: DataPelanggan
)