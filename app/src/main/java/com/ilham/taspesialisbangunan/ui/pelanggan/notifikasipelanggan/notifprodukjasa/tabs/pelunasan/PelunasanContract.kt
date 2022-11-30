package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.selesai

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface SelesaiContract {

    interface Presenter {
        fun getPengajuanselesai(kd_userpelenggan : Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanselesai(loading: Boolean)
        fun onResultPengajuanselesai(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}