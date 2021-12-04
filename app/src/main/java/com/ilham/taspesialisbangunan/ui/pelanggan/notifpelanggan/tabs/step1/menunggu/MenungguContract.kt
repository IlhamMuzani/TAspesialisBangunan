package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step1.menunggu

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface MenungguContract {

    interface Presenter {
        fun getPengajuanmenunggu(kd_userpelenggan : Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanmenunggu(loading: Boolean)
        fun onResultPengajuanmenunggu(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}