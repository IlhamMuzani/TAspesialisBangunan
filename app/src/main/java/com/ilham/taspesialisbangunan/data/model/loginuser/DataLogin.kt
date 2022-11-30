package com.ilham.taspesialisbangunan.data.model.loginuser

import com.google.gson.annotations.SerializedName

data class DataLogin (

    @SerializedName("id") val id : String?,
    @SerializedName("username") val username : String?,
    @SerializedName("email") val email : String?,
    @SerializedName("password") val password : String?,
    @SerializedName("kecamatan") val kecamatan : String?,
    @SerializedName("kelurahan") val kelurahan : String?,
    @SerializedName("alamat") val alamat : String?,
    @SerializedName("phone") val phone : String?,
)