package com.ilham.taspesialisbangunan.data.model.saran

import com.google.gson.annotations.SerializedName
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek

class ResponseSaranList (

    @SerializedName("status") val status : Boolean,
    @SerializedName("msg") val msg: String,
    @SerializedName("saran") val dataSaran: List<DataSaran>
)