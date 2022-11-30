package com.ilham.taspesialisbangunan.data.model.tambahrek

import com.google.gson.annotations.SerializedName


data class ResponseTambahrekList (

    @SerializedName("status") val status : Boolean,
    @SerializedName("msg") val msg : String,
    @SerializedName("rek") val dataTambahrek : List<DataTambahrek>
)