package com.ilham.taspesialisbangunan.data.model.tambahrek

import com.google.gson.annotations.SerializedName

data class ResponseTambahrekUpdate (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String
        )