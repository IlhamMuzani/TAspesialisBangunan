package com.ilham.taspesialisbangunan.data.model.saran

import com.google.gson.annotations.SerializedName

class ResponseSaranInsert (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String
)