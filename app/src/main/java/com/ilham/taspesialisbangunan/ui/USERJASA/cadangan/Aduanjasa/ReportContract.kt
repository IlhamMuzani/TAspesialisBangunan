package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.Aduanjasa

import com.ilham.taspesialisbangunan.data.model.aduanjasa.ResponseAduanInsert
import java.io.File

interface ReportContract {

    interface Presenter {
        fun insertAduanjasa(gambar: File, deskripsi: String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseAduanInsert: ResponseAduanInsert)
        fun showMessage(message: String)
    }
}