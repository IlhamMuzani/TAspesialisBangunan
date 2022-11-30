package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko

import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.saran.ResponseRating
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import java.io.File

interface LihatTokoContract {

    interface Presenter {
        fun getProdukdetail(id: Long)

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingLihatToko(loading: Boolean, message: String? = "Loading...")
        fun onResultProdukdetail(responseProdukDetail: ResponseProdukDetail)
        fun showMessage(message: String)
    }
}