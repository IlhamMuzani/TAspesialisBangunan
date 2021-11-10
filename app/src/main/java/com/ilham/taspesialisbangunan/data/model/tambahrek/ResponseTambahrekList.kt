package com.ilham.taspesialisbangunan.data.model.tambahrek

import com.google.gson.annotations.SerializedName


data class ResponseTambahrekList (

    @SerializedName("status") val status : Boolean,
    @SerializedName("data") val dataTambahrek : List<DataTambahrek>
)