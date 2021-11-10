package com.ilham.taspesialisbangunan.data.model.produk

import com.google.gson.annotations.SerializedName


data class ResponseProdukList (

    @SerializedName("status") val status : Boolean,
    @SerializedName("data") val dataProduk : List<DataProduk>
)