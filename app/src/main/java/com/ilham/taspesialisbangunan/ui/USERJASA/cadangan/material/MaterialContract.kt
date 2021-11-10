package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.material

import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate

interface MaterialContract {

        interface Presenter {
            fun getMaterial(jasausers_id: String)
            fun deleteMaterial(kd_material: Long)
        }

        interface View {
            fun initActivity()
            fun initListener()
            fun onLoadingMaterial(loading: Boolean)
            fun onResultMaterial(responseMaterialList: ResponseMaterialList)
            fun onResultDelete(responseMaterialUpdate: ResponseMaterialUpdate)
            fun showDialogDelete(dataMaterial: DataMaterial, position: Int)
            fun showMessage(message: String)
        }
}