package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.material.create

import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import java.io.File

interface MaterialCreateContract {

    interface Presenter {
        fun insertMaterial(jasausers_id: String, nama_toko: String, jenis_material: String, alamat: String, phone: String, harga: String,
                           latitude: String, longitude: String, gambar: File, deskripsi: String)
    }


    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseMaterialUpdate: ResponseMaterialUpdate)
        fun showMessage(message: String)
    }
}