package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.materialuser

import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList

interface MaterialuserContract {

    interface Presenter {
        fun getMaterial()
        fun searchMaterial(keyword: String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingMaterialUser(loading: Boolean)
        fun onResultMaterialUser(responseMaterialList: ResponseMaterialList)
        fun showDialogDetail(dataMaterial: DataMaterial, position: Int)
        fun showMessage(message: String)
    }
}