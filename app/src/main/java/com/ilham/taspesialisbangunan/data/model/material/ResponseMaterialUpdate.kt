package com.ilham.taspesialisbangunan.data.model.material

import com.google.gson.annotations.SerializedName

data class ResponseMaterialUpdate (
    @SerializedName("status") val status: Boolean,
    @SerializedName("msg") val msg: String
        )