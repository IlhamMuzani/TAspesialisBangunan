package com.ilham.taspesialisbangunan.data.model.harga

import com.google.gson.annotations.SerializedName

data class DataHarga (

    @SerializedName( "id") val id: Long?,
    @SerializedName( "status") val status: String?,
    @SerializedName( "jenis_pembuatan") val jenis_pembuatan: String?,
    @SerializedName( "ukuran") val ukuran: String?,
    @SerializedName( "harga") val harga: String?
)