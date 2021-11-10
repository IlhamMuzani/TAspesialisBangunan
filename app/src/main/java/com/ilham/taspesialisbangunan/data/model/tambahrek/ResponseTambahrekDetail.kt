package com.ilham.taspesialisbangunan.data.model.tambahrek

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk

data class ResponseTambahrekDetail (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("data") val dataTambahrek: DataTambahrek
)