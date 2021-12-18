package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import java.io.File

interface ProdukCreateContract {

    interface Presenter {
        fun insertProduk(kd_user: String,nama_toko: String, jenis_pembuatan: String, alamat: String, phone: String, harga: String,
                         latitude: String, longitude: String, gambar: File, deskripsi: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseProdukUpdate: ResponseProdukUpdate)
        fun showMessage(message: String)
    }
}