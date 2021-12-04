package com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.produk.produkdetail

import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import java.io.File

interface ProdukdetailContract {

    interface Presenter {
        fun getProdukdetail(kd_produkjasa: Long)
        fun insertPengajuan(
            kd_jasa: String,
            kd_userpelanggan: String,
            gambar: File,
            deskripsi: String,
            status: String,
        )
        fun insertSaran(
            kd_jasa: String,
            kd_userpelanggan: String,
            deskripsi: String,
        )
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingProdukdetail(loading: Boolean)
        fun onResultProdukdetail(responseProdukDetail: ResponseProdukDetail)
        fun onResultPengajuan(responsePengajuanInsert: ResponsePengajuanInsert)
        fun showDialogPengajuan(dataProduk: DataProduk)
        fun onResultSaran(responseSaranInsert: ResponseSaranInsert)
        fun showDialogSaran(dataProduk: DataProduk)
        fun showMessage(message: String)
    }
}