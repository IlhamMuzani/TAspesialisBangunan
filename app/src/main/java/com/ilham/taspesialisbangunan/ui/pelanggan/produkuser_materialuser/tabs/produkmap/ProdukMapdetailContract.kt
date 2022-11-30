package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produkmap

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList


interface ProdukMapdetailContract {

    interface Presenter {
        fun getMapproduk()

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingProduk(loading: Boolean)
        fun onResultProduk(responseProdukList: ResponseProdukList)
    }
}