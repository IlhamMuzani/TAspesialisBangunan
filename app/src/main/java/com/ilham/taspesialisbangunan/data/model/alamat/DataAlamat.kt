package com.ilham.taspesialisbangunan.data.model.produk

import com.google.gson.annotations.SerializedName

data class DataProduk (

    @SerializedName( "id") val id: Long?,
    @SerializedName( "kd_user") val kd_user: String?,
    @SerializedName( "nama_toko") val nama_toko: String?,
    @SerializedName( "category") val category: String?,
    @SerializedName("jenis_pembuatan") val jenis_pembuatan: String?,
    @SerializedName("alamat") val alamat: String?,
    @SerializedName("kelurahan") val kelurahan: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName( "latitude") val latitude: String?,
    @SerializedName( "longitude") val longitude: String?,
    @SerializedName( "harga") val harga: String?,
    @SerializedName( "deskripsi") val deskripsi: String?,
    @SerializedName( "gambar") val gambar: String?,
    @SerializedName( "status") val status: String?,
)