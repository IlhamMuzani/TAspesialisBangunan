package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa

import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate


interface ProdukContract {

    interface Presenter {
        fun getProduk(kd_user: Long)
        fun deleteProduk(kd_produkjasa: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingProduk(loading: Boolean)
        fun onResultProduk(responseProdukList: ResponseProdukList)
        fun onResultDelete(responseProdukUpdate: ResponseProdukUpdate)
        fun showDialogDelete(dataProduk: DataProduk, position: Int)
        fun showMessage(message: String)
    }
}