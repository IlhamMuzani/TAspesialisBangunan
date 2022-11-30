package com.ilham.taspesialisbangunan.data.model.tambahrek

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan


data class ResponseTambahrekTampilrekening (

    @SerializedName("status") val status : Boolean,
    @SerializedName("msg") val msg : String,
    @SerializedName("rek") val dataTambahrek : List<DataTambahrek>
)