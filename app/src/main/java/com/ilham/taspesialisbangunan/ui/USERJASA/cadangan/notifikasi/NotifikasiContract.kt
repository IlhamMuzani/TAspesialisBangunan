package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.notifikasi

import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList

interface NotifikasiContract {

    interface Presenter {
        fun getNotifPengajuan(kd_jasa: String)
        fun mypengajuan(kd_jasa: String)

        fun getNotifSaran ()
        fun mysaran(kd_jasa: String)

        fun insertPengajuanterima(kd_pengajuan: Long)
        fun insertPengajuantolak(kd_pengajuan: Long)
    }


    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingNotifikasi(loading: Boolean)
        fun onResultNotifPengajuan(responsePengajuanList1: ResponsePengajuanList1)
        fun onResultNotifSaran(responseSaranList: ResponseSaranList)
        fun showDialogKonfirmasi(dataPengajuan: DataPengajuan, position: Int)
        fun showDialogTolak(dataPengajuan: DataPengajuan, position: Int)
        fun onResultPengajuanUpdate(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun showMessage(message: String)
    }
}