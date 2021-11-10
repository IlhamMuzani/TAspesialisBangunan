package com.ilham.taspesialisbangunan.ui.USERJASA.profiljasa.tambahrek.update

import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekDetail
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate

interface TambahrekUpdateContract {

    interface Presenter {
        fun getDetailTambahrek(kd_rekening: Long)
        fun updateTambahrek(kd_rekening: Long, jenis_bank: String, norek: String, nama: String
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResultDetailTambahrek(responseTambahrekDetail: ResponseTambahrekDetail)
        fun onResultUpdateTambahrek(responseTambahrekUpdate: ResponseTambahrekUpdate)
        fun showMessage(message: String)
    }
}