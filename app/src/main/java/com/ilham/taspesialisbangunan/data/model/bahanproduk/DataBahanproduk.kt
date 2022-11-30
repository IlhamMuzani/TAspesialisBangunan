package com.ilham.taspesialisbangunan.data.model.bahanproduk

import com.google.gson.annotations.SerializedName

data class DataBahanproduk (

    @SerializedName( "id") val id: Long?,
    @SerializedName( "status") val status: String?,
    @SerializedName( "jenis") val jenis: String?
)