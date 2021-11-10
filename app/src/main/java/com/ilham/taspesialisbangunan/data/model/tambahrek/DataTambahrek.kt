package com.ilham.taspesialisbangunan.data.model.tambahrek

import com.google.gson.annotations.SerializedName

data class DataTambahrek (

    @SerializedName( "kd_rekening") val kd_rekening: Long?,
    @SerializedName( "jenis_bank") val jenis_bank: String?,
    @SerializedName( "norek") val norek: String?,
    @SerializedName( "nama") val nama: String?
)