package com.ilham.taspesialisbangunan.ui.userjasa.profiljasa.tambahrek.create

import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate

interface TambahrekCreateContract {

    interface Presenter {
        fun insertTambahrek(jasausers_id: String, jenis_bank: String, norek: String, nama: String)
    }


    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseTambahrekUpdate: ResponseTambahrekUpdate)
        fun showMessage(message: String)
    }
}