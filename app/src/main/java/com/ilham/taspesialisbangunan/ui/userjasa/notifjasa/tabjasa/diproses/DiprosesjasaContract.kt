package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.diproses

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface DiprosesjasaContract {

    interface Presenter {
        fun getPengajuanjasadiproses(kd_jasa: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuandiproses(loading: Boolean)
        fun onResultPengajuandiproses(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}