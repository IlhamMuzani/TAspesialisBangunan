package com.ilham.taspesialisbangunan.data.model.alamat

import com.google.gson.annotations.SerializedName

data class DataAlamat (

    @SerializedName( "id") val id: Long?,
    @SerializedName( "status") val status: String?,
    @SerializedName( "kelurahan") val kelurahan: String?
)