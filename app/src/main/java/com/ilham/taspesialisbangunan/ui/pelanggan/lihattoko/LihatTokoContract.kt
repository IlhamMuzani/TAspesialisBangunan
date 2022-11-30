package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.produkdetail

import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.saran.ResponseRating
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import java.io.File

interface ProdukdetailContract {

    interface Presenter {
        fun getProdukdetail(id: Long)
        fun getsaranproduk(id: Long)
        fun getProduklainya()
        fun getRating(id: Long)


    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingProdukdetail(loading: Boolean)
        fun onResultProdukdetail(responseProdukDetail: ResponseProdukDetail)
        fun onResultSaranProduk(responseSaranList: ResponseSaranList)
        fun onResultProduklainya(responseProdukList: ResponseProdukList)
        fun onResultgetRating(responseRating: ResponseRating)
        fun showMessage(message: String)
    }
}