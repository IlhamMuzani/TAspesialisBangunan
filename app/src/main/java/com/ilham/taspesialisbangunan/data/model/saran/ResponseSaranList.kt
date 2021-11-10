package com.ilham.taspesialisbangunan.data.model.saran

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan

class ResponseSaranList (

    @SerializedName("status") val status : Boolean,
    @SerializedName("data") val dataSaran : List<DataSaran>
)