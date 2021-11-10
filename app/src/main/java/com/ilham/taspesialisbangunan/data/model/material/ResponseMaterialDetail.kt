package com.ilham.taspesialisbangunan.data.model.material

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk

data class ResponseMaterialDetail (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("data") val dataMaterial: DataMaterial
)