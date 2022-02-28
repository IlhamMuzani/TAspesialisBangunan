package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.produkdetail

import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import java.io.File

interface ProdukdetailContract {

    interface Presenter {
        fun getProdukdetail(id: Long)
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
        fun onResultSaran(responseSaranInsert: ResponseSaranInsert)
        fun showDialogSaran(dataProduk: DataProduk)
        fun showMessage(message: String)
    }
}