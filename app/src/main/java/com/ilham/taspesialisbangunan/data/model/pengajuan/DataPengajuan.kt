package com.ilham.taspesialisbangunan.data.model.pengajuan

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.user.DataUser

data class DataPengajuan(
    @SerializedName("id") val id: Long?,
    @SerializedName("kd_produk") val kd_produk: String?,
    @SerializedName("kd_user") val kd_user: String?,
    @SerializedName("gambar") val gambar: String,
    @SerializedName("deskripsi") val deskripsi: String,
    @SerializedName("harga") val harga: String,
    @SerializedName("bukti") val bukti: String,
    @SerializedName("status") val status: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("user") val user: DataUser,
    @SerializedName("produk") val produk: DataProduk,
)