package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.diterima

import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate


interface DiterimaContract {


        interface Presenter {
            fun getPengajuanditerima(kd_userpelenggan : Long)
            fun deletePengajuanDiterima(id: Long)

        }

        interface View {
            fun initFragment(view: android.view.View)
            fun onLoadingPengajuanditerima(loading: Boolean)
            fun onResultPengajuanditerima(responsePengajuanList1: ResponsePengajuanList1)
            fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate)
            fun showDialogDelete(dataPengajuan: DataPengajuan, position: Int)
            fun showMessage(message: String)
        }
    }