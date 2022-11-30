package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.ProdukToko

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList

interface ProdukTokoContract {

    interface Presenter {
        fun getProduk1(kd_user: Long)
        fun searchProduk1(keyword: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingProduk1(loading: Boolean)
        fun onResultProduk1(responseProdukList: ResponseProdukList)
        fun showMessage1(message: String)
    }
}