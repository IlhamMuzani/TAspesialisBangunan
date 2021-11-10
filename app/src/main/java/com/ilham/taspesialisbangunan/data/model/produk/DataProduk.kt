package com.ilham.taspesialisbangunan.data.model.produk

import com.google.gson.annotations.SerializedName

data class DataProduk (

    @SerializedName( "kd_produkjasa") val kd_produkjasa: Long?,
    @SerializedName( "jasausers_id") val jasausers_id: String?,
    @SerializedName( "nama_toko") val nama_toko: String?,
    @SerializedName( "jenis_pembuatan") val jenis_pembuatan: String?,
    @SerializedName( "alamat") val alamat: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName( "latitude") val latitude: String?,
    @SerializedName( "longitude") val longitude: String?,
    @SerializedName( "harga") val harga: String?,
    @SerializedName( "deskripsi") val deskripsi: String?,
    @SerializedName( "gambar") val gambar: String?
)