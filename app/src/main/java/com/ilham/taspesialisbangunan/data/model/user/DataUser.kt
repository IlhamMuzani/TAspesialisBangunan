package com.ilham.taspesialisbangunan.data.model.user

import com.google.gson.annotations.SerializedName

class DataUser (

    @SerializedName( "id") val id: Long?,
    @SerializedName( "username") val username: String?,
    @SerializedName( "email") val email: String?,
    @SerializedName( "password") val password: String?,
    @SerializedName( "nama_toko") val nama_toko: String?,
    @SerializedName( "kecamatan") val kecamatan: String?,
    @SerializedName( "kelurahan") val kelurahan: String?,
    @SerializedName( "alamat") val alamat: String?,
    @SerializedName( "latitude") val latitude: String?,
    @SerializedName( "longitude") val longitude: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("deskripsi") val deskripsi: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("verifikasi") val verifikasi: String?,
    @SerializedName("gambar") val gambar: String?
)