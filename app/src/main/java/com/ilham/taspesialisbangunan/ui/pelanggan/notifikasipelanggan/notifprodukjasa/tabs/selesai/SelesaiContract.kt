package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.selesai

import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate


interface SelesaiContract {

    interface Presenter {
        fun getPengajuanselesai(kd_userpelenggan : Long)
        fun pengajuanselesaikehistori (id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanselesai(loading: Boolean)
        fun onResultPengajuanselesai(responsePengajuanList1: ResponsePengajuanList1)
        fun onResultSelesaikeHistori(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun showDialogHistori(dataPengajuan: DataPengajuan, position: Int)
        fun showMessage(message: String)
    }
}