package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa.materialjasadetail

import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialDetail
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import java.io.File

interface MaterialJasaDetailContract {

    interface Presenter {
        fun getMaterialdetail(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingMaterialdetailjas(loading: Boolean)
        fun onResultMaterialdetailjas(responseProdukDetail: ResponseProdukDetail)
        fun showMessage(message: String)
    }
}