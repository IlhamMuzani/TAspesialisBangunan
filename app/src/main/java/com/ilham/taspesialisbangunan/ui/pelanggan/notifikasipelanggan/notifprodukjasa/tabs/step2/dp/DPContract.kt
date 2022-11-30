package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step2.dp

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface DPContract {


        interface Presenter {
            fun getPengajuandp(kd_userpelenggan : Long)
        }

        interface View {
            fun initFragment(view: android.view.View)
            fun onLoadingPengajuanDP(loading: Boolean)
            fun onResultPengajuanDP(responsePengajuanList1: ResponsePengajuanList1)
            fun showMessage(message: String)
        }
    }