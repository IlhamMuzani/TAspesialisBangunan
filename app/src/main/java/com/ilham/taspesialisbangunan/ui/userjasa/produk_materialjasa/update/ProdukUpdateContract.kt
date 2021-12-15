package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.update

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import java.io.File

interface ProdukUpdateContract {

    interface Presenter {
        fun getDetail(id: Long)
        fun updateProduk(kd_produkjasa: Long, nama_toko: String, jenis_pembuatan: String, alamat: String, phone: String, harga: String,
                         latitude: String, longitude: String, gambar: File?, deskripsi: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResultDetail(responseProdukDetail: ResponseProdukDetail)
        fun onResultUpdate(responseProdukUpdate: ResponseProdukUpdate)
        fun showMessage(message: String)
    }
}