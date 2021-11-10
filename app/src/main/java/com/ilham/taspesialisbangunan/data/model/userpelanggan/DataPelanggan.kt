package com.ilham.taspesialisbangunan.data.model.userpelanggan

import com.google.gson.annotations.SerializedName

class DataPelanggan (

    @SerializedName( "id") val id: Long?,
    @SerializedName( "username") val username: String?,
    @SerializedName( "email") val email: String?,
    @SerializedName( "alamat") val alamat: String?,
    @SerializedName("phone") val phone: String?
)