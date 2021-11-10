package com.ilham.taspesialisbangunan.data.model.produk

import com.google.gson.annotations.SerializedName


data class ResponseProdukDetail (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("data") val dataProduk: DataProduk
)