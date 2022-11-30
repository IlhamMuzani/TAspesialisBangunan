package com.ilham.taspesialisbangunan.data.model.saran

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.user.DataUser

class DataSaran (

    @SerializedName("id") val id: Long?,
    @SerializedName("kd_produk") val kd_produk: String?,
    @SerializedName("kd_user") val kd_user: String?,
    @SerializedName("kd_pengajuan") val kd_pengajuan: String?,
    @SerializedName("deskripsi") val deskripsi: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("status") val status: String,
    @SerializedName("user") val user: DataUser
)