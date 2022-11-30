package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.KategoriToko.Pembuatan

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList


interface PembuatanContract {

    interface Presenter {
        fun getProdukpembuatan(kd_user: Long, jenis_pembuatan: String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingProduktoko(loading: Boolean)
        fun onResultProduktoko(responseProdukList: ResponseProdukList)
        fun showMessage(message: String)
    }
}