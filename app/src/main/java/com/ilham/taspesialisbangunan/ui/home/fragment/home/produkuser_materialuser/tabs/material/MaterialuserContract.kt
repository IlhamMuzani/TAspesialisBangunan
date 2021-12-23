package com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.material

import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList

interface MaterialuserContract {

    interface Presenter {
        fun getMaterial()
        fun searchMaterial(keyword: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingMaterialUser(loading: Boolean)
        fun onResultMaterialUser(responseProdukList: ResponseProdukList)
        fun showMessage(message: String)
    }
}