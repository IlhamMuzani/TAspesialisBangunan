package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import java.io.File

interface ProdukCreateContract {

    interface Presenter {
        fun insertProduk(kd_user: String,nama_toko: String, category: String, jenis_pembuatan: String, alamat: String, kelurahan: String, phone: String, harga: String,
                         latitude: String, longitude: String, gambar: File, deskripsi: String
        )
        fun searchCategoriproduk(keyword: String)
        fun searchkecamatan(keyword: String)

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseProdukUpdate: ResponseProdukUpdate)
        fun onResultSearchProduk(responseProdukList: ResponseProdukList)
        fun onResultSearchKecamatan(responseProdukList: ResponseProdukList)
        fun showMessage(message: String)
    }
}