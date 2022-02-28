package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.material

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