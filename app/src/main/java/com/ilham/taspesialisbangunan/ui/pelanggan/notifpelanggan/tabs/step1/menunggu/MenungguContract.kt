package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step1.menunggu

import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate


interface MenungguContract {

    interface Presenter {
        fun getPengajuanmenunggu(kd_user : Long)
        fun deletePengajuanMenunggu(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanmenunggu(loading: Boolean)
        fun onResultPengajuanmenunggu(responsePengajuanList1: ResponsePengajuanList1)
        fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun showDialogDelete(dataPengajuan: DataPengajuan, position: Int)
        fun showMessage(message: String)
    }
}