package com.ilham.taspesialisbangunan.data.model.loginuser

import com.google.gson.annotations.SerializedName

data class DataLoginuser (
    @SerializedName("id") val id : String?,
    @SerializedName("username") val username : String?,
    @SerializedName("email") val email : String?,
    @SerializedName("password") val password : String?,
    @SerializedName("alamat") val alamat : String?,
    @SerializedName("phone") val phone : String?,
)