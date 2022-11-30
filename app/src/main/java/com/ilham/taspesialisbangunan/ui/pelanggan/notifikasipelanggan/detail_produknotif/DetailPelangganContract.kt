package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.detailnotif

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekTampilrekening
import java.io.File

interface DetailPelangganContract {
    interface Presenter {
        fun getDetail(id: Long)
        fun buktiPengajuan(id: Long, bukti: File)
        fun buktiPengajuanMaterial(id: Long, bukti: File)
        fun buktiPelunasan(id: Long, bukti: File)
        fun getTampilprodukrekening(kd_user: String)

        fun insertSaran(
            kd_produk: String,
            kd_user: String,
            kd_pengajuan: String,
            deskripsi: String
        )

    }
    interface View {
        fun initActivity()
        fun initListener()
        fun onShowDialogRek(dataTambahrek: List<DataTambahrek>)
        fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail)
        fun onResultUpdate(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onResultTampilprodukrek(responseTambahrekTampilrekening: ResponseTambahrekTampilrekening)
        fun onResultSaran(responseSaranInsert: ResponseSaranInsert)
        fun showMessage(message: String)
        fun onLoading(loading: Boolean)
    }
}