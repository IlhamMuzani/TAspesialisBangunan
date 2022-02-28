package com.ilham.taspesialisbangunan.ui.userjasa.tambahrek.create

import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate

interface TambahrekCreateContract {

    interface Presenter {
        fun insertTambahrek(kd_user: String, jenis_bank: String, norek: String, nama: String)
    }


    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseTambahrekUpdate: ResponseTambahrekUpdate)
        fun showMessage(message: String)
    }
}