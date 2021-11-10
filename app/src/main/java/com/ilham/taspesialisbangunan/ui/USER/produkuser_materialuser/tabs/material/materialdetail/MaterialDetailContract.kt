package com.ilham.taspesialisbangunan.ui.USER.produkuser_materialuser.tabs.material.materialdetail

import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialDetail
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import java.io.File

interface MaterialDetailContract {

    interface Presenter {
        fun getMaterialdetail(kd_material: Long)
        fun insertPengajuan(
            kd_jasa: String,
            kd_userpelanggan: String,
            gambar: File,
            deskripsi: String,
            status: String,
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingMaterialdetail(loading: Boolean)
        fun onResultMaterialdetail(responseDetail: ResponseMaterialDetail)
        fun onResultPengajuan(responsePengajuanInsert: ResponsePengajuanInsert)
        fun showDialogPengajuan(dataProduk: DataProduk)
        fun showMessage(message: String)
    }
}