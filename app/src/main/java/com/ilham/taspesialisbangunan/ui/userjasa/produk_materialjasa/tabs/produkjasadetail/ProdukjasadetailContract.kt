package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.produkjasadetail

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.saran.ResponseRating

interface ProdukjasadetailContract {

    interface Presenter {
        fun getProdukjasadetail(id: Long)
        fun getRatingjasa(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingProdukJasadetail(loading: Boolean, message: String? = "Loading...")
        fun onResultProdukjasadetail(responseProdukDetail: ResponseProdukDetail)
        fun onResultgetRatingjasa(responseRating: ResponseRating)
        fun showMessage(message: String)
    }
}