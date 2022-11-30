package com.ilham.taspesialisbangunan.ui.pelanggan.pengajuan

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import java.io.File

interface PengajuanContract {

    interface Presenter {
        fun insertPengajuan(
            kd_produk: String,
            kd_user: String,
            gambar: File,
            deskripsi: String,
            status: String,
            alamat: String,
            categori_pesanan: String,
            phone: String,
            latitude: String,
            longitude: String,
            category: String
        )

        fun insertPengajuanjasadanmaterial(
            kd_produk: String,
            kd_user: String,
            gambar: File,
            deskripsi: String,
            status: String,
            alamat: String,
            categori_pesanan: String,
            phone: String,
            latitude: String,
            longitude: String,
            category: String
        )

        fun getProdukdetailpengajuan(id: Long)

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultPengajuan(responsePengajuanInsert: ResponsePengajuanInsert)
        fun onResultProdukdetailpengajuan(responseProdukDetail: ResponseProdukDetail)
        fun showMessage(message: String)
    }
}