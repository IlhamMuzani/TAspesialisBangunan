package com.ilham.taspesialisbangunan.data.model.pengajuan

import com.google.gson.annotations.SerializedName

class ResponsePengajuanList1 (

    @SerializedName("status") val status : Boolean,
    @SerializedName("pengajuan") val pengajuan : List<DataPengajuan>
)