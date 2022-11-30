package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.pelunasan

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface PelunasanContract {

    interface Presenter {
        fun getPengajuanPelunasan(kd_userpelenggan : Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanpelunasan(loading: Boolean)
        fun onResultPengajuanpelunasan(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}