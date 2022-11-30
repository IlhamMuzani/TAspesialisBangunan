package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.diterima

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface DiterimajasaContract {

    interface Presenter {
        fun getPengajuanditerimajasa(kd_jasa: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanditerima(loading: Boolean)
        fun onResultPengajuanditerima(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}