package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step2.diproses

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface DiprosesContract {

    interface Presenter {
        fun getPengajuandiproses(kd_userpelenggan : Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuandiproses(loading: Boolean)
        fun onResultPengajuandiproses(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}