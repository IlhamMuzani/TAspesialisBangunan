package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.detail

import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSarandetail
import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek

interface DetailPengajuanContract {
    interface Presenter {
        fun getDetail(id: Long)
        fun hargaPengajuan(id: Long, harga: String, deskripsi_kesepakatan: String, status_dp: String, panjang: String, lebar: String)
        fun cektempatlokasi (id: Long)
        fun pengajuanjasaDPkeProses (id: Long)
        fun pengajuanjasaDPkeProsesbayarcash (id: Long)
        fun pengajuanjasaProseskePelunasan (id: Long)
        fun pengajuanjasaProseskePelunasanbayarcash (id: Long)
//        fun pengajuanselesai (id: Long)
        fun getSaranperproduk(id: Long)
        fun deletePengajuanjasa(id: Long)



    }
    interface View {
        fun initActivity()
        fun initListener()
        fun onShowDialogkonfirmasi()
        fun onShowDialogkerjakan()
        fun onShowDialogkerjakanbayarcash()
        fun onShowDialogkepelunasan()
        fun onShowDialogkepelunasanbayarcash()
        fun onShowDialogdetailgambar()
        fun onShowDialogdetailgambarpengajuanfull()
        fun onShowDialogHapus()
        fun onShowDialogHapuslainya()
        fun onShowDialogHarga()
        fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail)
        fun onResultUpdateharga(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onResultUpdateproses(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onResultSaranJasaProduk(responseSaranList: ResponseSaranList)
        fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun showMessage(message: String)

    }
}