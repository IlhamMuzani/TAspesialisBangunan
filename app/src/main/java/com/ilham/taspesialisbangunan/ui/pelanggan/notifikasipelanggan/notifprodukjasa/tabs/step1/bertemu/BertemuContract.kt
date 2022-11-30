package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.bertemu

import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail


interface BertemuContract {

    interface Presenter {
        fun getPengajuanbertemu(kd_user : Long)
        fun deletePengajuanbertemu(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanbertemu(loading: Boolean)
        fun onResultPengajuanbertemu(responsePengajuanList1: ResponsePengajuanList1)
        fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun showDialogDelete(dataPengajuan: DataPengajuan, position: Int)
        fun showMessage(message: String)
    }
}