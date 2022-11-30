package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.pelunasan

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface PelunasanjasaContract {

    interface Presenter {
        fun getPengajuanPelunasan(kd_jasa: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanPelunasan(loading: Boolean)
        fun onResultPengajuanPelunasan(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}