package com.ilham.taspesialisbangunan.ui.pelanggan.pengajuan

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import java.io.File

interface PengajuanContract {

    interface Presenter {
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
        fun onLoading(loading: Boolean)
        fun onResultPengajuan(responsePengajuanInsert: ResponsePengajuanInsert)
        fun showMessage(message: String)
    }
}