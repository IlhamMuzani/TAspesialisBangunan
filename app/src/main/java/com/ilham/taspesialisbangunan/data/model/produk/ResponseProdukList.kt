package com.ilham.taspesialisbangunan.data.model.produk

import com.google.gson.annotations.SerializedName


data class ResponseProdukList (
    @SerializedName("status") val status : Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("produk") val dataProduk: List<DataProduk>
)