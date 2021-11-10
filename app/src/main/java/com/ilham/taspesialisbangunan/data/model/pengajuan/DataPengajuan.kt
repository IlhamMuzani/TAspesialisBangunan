package com.ilham.taspesialisbangunan.data.model.pengajuan

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.loginjasa.DataLogin
import com.ilham.taspesialisbangunan.data.model.loginuser.DataLoginuser

data class DataPengajuan(
    @SerializedName("kd_pengajuan") val kd_pengajuan: Long?,
    @SerializedName("kd_jasa") val kd_jasa: String?,
    @SerializedName("kd_userpelanggan") val kd_userpelanggan: String?,
    @SerializedName("gambar") val gambar: String,
    @SerializedName("deskripsi") val deskripsi: String,
    @SerializedName("harga") val harga: String,
    @SerializedName("bukti") val bukti: String,
    @SerializedName("status") val status: String,
    @SerializedName("jasa") val jasa: DataLogin,
    @SerializedName("userpelanggan") val userpelanggan: DataLoginuser,

)