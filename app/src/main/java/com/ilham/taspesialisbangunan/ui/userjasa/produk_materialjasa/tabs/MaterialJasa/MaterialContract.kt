package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa

import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate


interface MaterialContract {

    interface Presenter {
        fun getMaterial(kd_user: String)
        fun deleteMaterial(kd_material: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingMaterial(loading: Boolean)
        fun onResultMaterial(responseMaterialList: ResponseMaterialList)
        fun onResultDelete(responseMaterialUpdate: ResponseMaterialUpdate)
        fun showDialogDelete(dataMaterial: DataMaterial, position: Int)
        fun showDialogDetail(dataMaterial: DataMaterial, position: Int)
        fun showMessage(message: String)
    }
}