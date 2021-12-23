package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.produkjasadetail

import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import java.io.File

interface ProdukjasadetailContract {

    interface Presenter {
        fun getProdukjasadetail(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingProdukJasadetail(loading: Boolean)
        fun onResultProdukjasadetail(responseProdukDetail: ResponseProdukDetail)
        fun showMessage(message: String)
    }
}