package com.ilham.taspesialisbangunan.data.model.aduanjasa

import com.google.gson.annotations.SerializedName

class ResponseAduanInsert (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String
)