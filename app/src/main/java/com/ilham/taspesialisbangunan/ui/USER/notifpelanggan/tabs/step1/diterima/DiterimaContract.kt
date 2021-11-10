package com.ilham.taspesialisbangunan.ui.USER.notifpelanggan.tabs.step1.diterima

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface DiterimaContract {


        interface Presenter {
            fun getPengajuanditerima(kd_userpelenggan : Long)
        }

        interface View {
            fun initFragment(view: android.view.View)
            fun onLoadingPengajuanditerima(loading: Boolean)
            fun onResultPengajuanditerima(responsePengajuanList1: ResponsePengajuanList1)
            fun showMessage(message: String)
        }
    }