package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.material.materialdetail

import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import java.io.File

interface MaterialDetailContract {

    interface Presenter {
        fun getMaterialdetail(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingMaterialdetail(loading: Boolean)
        fun onResultMaterialdetail(responseProdukDetail: ResponseProdukDetail)
        fun showMessage(message: String)
    }
}