package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList


interface ProdukuserContract {

    interface Presenter {
        fun getProduk()
        fun searchProduk(keyword: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingProduk(loading: Boolean)
        fun onResultProduk(responseProdukList: ResponseProdukList)
        fun showMessage(message: String)
    }
}