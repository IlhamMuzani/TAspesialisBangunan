package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.produkjasa.tabjasa.selesai

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface SelesaijasaContract {

    interface Presenter {
        fun getPengajuanSelesai(kd_jasa: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanSelesai(loading: Boolean)
        fun onResultPengajuanSelesai(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}