package com.ilham.taspesialisbangunan.ui.USER.produkuser_materialuser.tabs.material

import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList

interface MaterialuserContract {

    interface Presenter {
        fun getMaterial()
        fun searchMaterial(keyword: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingMaterialUser(loading: Boolean)
        fun onResultMaterialUser(responseMaterialList: ResponseMaterialList)
        fun showDialogDetail(dataMaterial: DataMaterial, position: Int)
        fun showMessage(message: String)
    }
}