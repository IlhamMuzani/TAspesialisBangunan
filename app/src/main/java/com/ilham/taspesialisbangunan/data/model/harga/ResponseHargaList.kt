package com.ilham.taspesialisbangunan.data.model.bahanproduk

import com.google.gson.annotations.SerializedName


data class ResponseBahanprodukList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("bahanproduk") val bahanproduk: List<DataBahanproduk>
)