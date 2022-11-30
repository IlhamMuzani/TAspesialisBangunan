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
    @SerializedName("categori_pesanan") val categori_pesanan: String,
    @SerializedName("alamat") val alamat: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("status_dp") val status_dp: String,
    @SerializedName( "deskripsi_kesepakatan") val deskripsi_kesepakatan: String?,
    @SerializedName( "pesan") val pesan: String?,
    @SerializedName( "panjang") val panjang: String?,
    @SerializedName( "lebar") val lebar: String?,
    @SerializedName("category") val category: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("user") val user: DataUser,
    @SerializedName("produk") val produk: DataProduk,
)