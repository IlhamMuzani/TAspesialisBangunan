package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.dikonfirmasi

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface DikonfirmasijasaContract {

    interface Presenter {
        fun getPengajuandikonfirmasi(kd_jasa: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuandikonfirmasi(loading: Boolean)
        fun onResultPengajuandikonfirmasi(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}