package com.ilham.taspesialisbangunan.data.model.harga

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.alamat.DataAlamat


data class ResponseHargaList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("harga") val dataHarga: List<DataHarga>
)