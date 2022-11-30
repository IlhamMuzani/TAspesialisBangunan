package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.Toko

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail

interface TokoContract {

    interface Presenter {
//        fun getNameToko(id: Long)
        fun getDetailProfiljasa(id: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingToko(loading: Boolean, message: String? = "Loading...")
        fun onResultDetailljasa(responseUserdetail: ResponseUserdetail)
//        fun onResultToko(responseProdukDetail: ResponseProdukDetail)
        fun showMessage1(message: String)
    }
}