package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.KategoriToko

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList

interface KategoriTokoContract {

    interface Presenter {
        fun getProduktampilkanopi(kd_user: Long, jenis_pembuatan: String)
        fun getProduktampilpagar(kd_user: Long, jenis_pembuatan: String)
        fun getProduktampiltangga(kd_user: Long, jenis_pembuatan: String)
        fun getProduktampiltralis(kd_user: Long, jenis_pembuatan: String)
        fun getProduktampilhalaman(kd_user: Long, jenis_pembuatan: String)

    }

   interface View{
       fun initFragment(view: android.view.View)
       fun onLoading(loading: Boolean, message: String? = "Loading...")
       fun onResulttampilkanopi(responseProdukList: ResponseProdukList)
       fun onResulttampilpagar(responseProdukList: ResponseProdukList)
       fun onResulttampiltangga(responseProdukList: ResponseProdukList)
       fun onResulttampiltralis(responseProdukList: ResponseProdukList)
       fun onResulttampilhalaman(responseProdukList: ResponseProdukList)
       fun showMessage(message: String)
   }
}