package com.ilham.taspesialisbangunan.data.model.material

import com.google.gson.annotations.SerializedName

data class DataMaterial (

    @SerializedName( "kd_material") val kd_material: Long?,
    @SerializedName( "nama_toko") val nama_toko: String?,
    @SerializedName( "jenis_material") val jenis_material: String?,
    @SerializedName( "alamat") val alamat: String?,
    @SerializedName( "latitude") val latitude: String?,
    @SerializedName( "longitude") val longitude: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName( "harga") val harga: String?,
    @SerializedName( "deskripsi") val deskripsi: String?,
    @SerializedName( "gambar") val gambar: String?
)