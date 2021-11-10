package com.ilham.taspesialisbangunan.data.model.pengajuan

import com.google.gson.annotations.SerializedName

class ResponsePengajuanList (

    @SerializedName("status") val status : Boolean,
    @SerializedName("data") val dataPengajuan : List<DataPengajuan>
)