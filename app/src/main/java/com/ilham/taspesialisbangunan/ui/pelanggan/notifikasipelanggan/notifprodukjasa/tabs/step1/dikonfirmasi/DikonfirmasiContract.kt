package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.dikonfirmasi

import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail


interface DikonfirmasiContract {

    interface Presenter {
        fun getPengajuandikonfirmasi(kd_user : Long)
        fun deletePengajuanDikonfirmasi(id: Long)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuandikonfirmasi(loading: Boolean)
        fun onResultPengajuandikonfirmasi(responsePengajuanList1: ResponsePengajuanList1)
        fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun showDialogDelete(dataPengajuan: DataPengajuan, position: Int)
        fun showMessage(message: String)
    }
}