package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa

import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate


interface MaterialContract {

    interface Presenter {
        fun getProdukMat(kd_user: Long)
        fun deleteProduk(kd_produkjasa: Long)
    }

    interface View {
        fun initFragmentM(view: android.view.View)
        fun onLoadingProdukM(loading: Boolean)
        fun onResultProdukM(responseProdukList: ResponseProdukList)
        fun onResultDeleteM(responseProdukUpdate: ResponseProdukUpdate)
        fun showDialogDeleteM(dataProduk: DataProduk, position: Int)
        fun showMessage(message: String)
    }
}