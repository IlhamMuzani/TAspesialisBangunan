package com.ilham.taspesialisbangunan.data.model.saran

import com.google.gson.annotations.SerializedName

class DataSaran (

    @SerializedName("kd_saran") val kd_saran: Long?,
    @SerializedName("deskripsi") val deskripsi: String
)