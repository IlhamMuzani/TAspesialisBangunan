package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.detail

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekList
import java.io.File

interface DetailPelangganContract {
    interface Presenter {
        fun getDetail(id: Long)
        fun buktiPengajuan(id: Long, bukti: File)
        fun getTampilprodukrekening(kd_user: String)

    }
    interface View {
        fun initActivity()
        fun initListener()
        fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail)
        fun onResultUpdate(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onResultTampilprodukrek(responseTambahrekList: ResponseTambahrekList)
        fun onLoading(loading: Boolean)
    }
}