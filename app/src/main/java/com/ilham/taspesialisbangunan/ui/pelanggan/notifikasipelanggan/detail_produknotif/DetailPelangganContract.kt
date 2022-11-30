package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.detail_produknotif

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
        fun buktibayarcast(id: Long, bukti: File)
        fun buktiPelunasan(id: Long, bukti: File)
        fun tolakkesepakatan(id: Long, pesan: String)
        fun getTampilprodukrekening(kd_user: String)
        fun deletePengajuanuser(id: Long)
        fun pengajuanjasakonfirmasibertemu (id: Long)
        fun pengajuanbayarditempat (id: Long)
        fun pengajuanuserselesaicash (id: Long)

        fun insertSaran(
            kd_produk: String,
            kd_user: String,
            kd_pengajuan: String,
            deskripsi: String,
            rating: String,
            status: String
        )

    }
    interface View {
        fun initActivity()
        fun initListener()
        fun onShowDialogPilihcastdp()
        fun onShowDialogTerima()
        fun onShowDialogRek(dataTambahrek: List<DataTambahrek>)
        fun onShowDialogbayarcast()
        fun onShowDialogDP()
        fun onShowDialogSaran()
        fun onShowDialogPelunasan()
        fun onShowDialogHapuspengajuan()
        fun onShowDialogBayarditempat()
        fun onShowDialogtolaksaran()
        fun onShowDialogtolakkesepakatan()
        fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail)
        fun onResultUpdate(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onResultUpdatekonfirbertemu(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onResultBayarditempat(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onResultTampilprodukrek(responseTambahrekTampilrekening: ResponseTambahrekTampilrekening)
        fun onResultSaran(responseSaranInsert: ResponseSaranInsert)
        fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun showMessage(message: String)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
    }
}