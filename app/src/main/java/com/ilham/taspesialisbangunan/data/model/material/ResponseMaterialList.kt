package com.ilham.taspesialisbangunan.data.model.material

import com.google.gson.annotations.SerializedName


data class ResponseMaterialList (

    @SerializedName("status") val status : Boolean,
    @SerializedName("data") val dataMaterial : List<DataMaterial>
)