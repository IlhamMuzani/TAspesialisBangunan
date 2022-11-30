package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.produkjasa.tabjasa.bertemu

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface BertemujasaContract {

    interface Presenter {
        fun getPengajuanbertemu(kd_jasa: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanbertemu(loading: Boolean)
        fun onResultPengajuanbertemu(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}