package com.ilham.taspesialisbangunan.data.model.produk

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.user.DataUser

data class DataProduk (

    @SerializedName( "id") val id: Long?,
    @SerializedName( "kd_user") val kd_user: String?,
    @SerializedName( "category") val category: String?,
    @SerializedName("jenis_pembuatan") val jenis_pembuatan: String?,
    @SerializedName("model") val model: String?,
    @SerializedName("bahan") val bahan: String?,
    @SerializedName("ukuran") val ukuran: String?,
    @SerializedName("berat") val berat: String?,
    @SerializedName("alamat") val alamat: String?,
    @SerializedName("kelurahan") val kelurahan: String?,
    @SerializedName("kecamatan") val kecamatan: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName( "latitude") val latitude: String?,
    @SerializedName( "longitude") val longitude: String?,
    @SerializedName( "harga") val harga: String?,
    @SerializedName( "deskripsi") val deskripsi: String?,
    @SerializedName( "gambar") val gambar: String?,
    @SerializedName( "rating") val rating: String?,
    @SerializedName( "status") val status: String?,
    @SerializedName("user") val user: DataUser
    )