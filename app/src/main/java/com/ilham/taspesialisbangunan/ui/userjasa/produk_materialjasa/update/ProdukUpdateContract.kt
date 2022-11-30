package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.update

import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.bahanproduk.ResponseBahanprodukList
import com.ilham.taspesialisbangunan.data.model.harga.ResponseHargaList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import java.io.File

interface ProdukUpdateContract {

    interface Presenter {
        fun getDetail(id: Long)
        fun updateProduk(id: Long, category: String, jenis_pembuatan: String, model: String, ukuran: String, kecamatan: String,  kelurahan: String, alamat: String, phone: String, harga: String,
                         latitude: String, longitude: String, gambar: File?, deskripsi: String
        )
        fun updatejasasaja(kd_produkjasa: Long, category: String, jenis_pembuatan: String, model: String, kecamatan: String,  kelurahan: String, alamat: String, phone: String, harga: String,
                           latitude: String, longitude: String, gambar: File?, deskripsi: String)

        fun searchukuranjasadanmaterialkanopiupdate(keyword: String)
        fun searchukuranjasadanmaterialpagarupdate(keyword: String)
        fun searchukuranjasadanmaterialtralisupdate(keyword: String)
        fun searchukuranjasadanmaterialtanggaupdate(keyword: String)
        fun searchukuranjasadanmaterialhalamanupdate(keyword: String)
        fun searchukuranjasasajaupdate(keyword: String)

        fun searchhargajasadanmaterialupdatekanopi(keyword: String)
        fun searchhargajasadanmaterialupdatepagar(keyword: String)
        fun searchhargajasadanmaterialupdatetangga(keyword: String)
        fun searchhargajasadanmaterialupdatetralis(keyword: String)
        fun searchhargajasadanmaterialupdatehalaman(keyword: String)
        fun searchhargaprodukjasasajaupdate(keyword: String)

        fun searchCategoriprodukupdate(keyword: String)
        fun searchkecamatanprodukupdate(keyword: String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultDetail(responseProdukDetail: ResponseProdukDetail)
        fun onResultUpdate(responseProdukUpdate: ResponseProdukUpdate)
        fun onResultSearchProdukupdate(responseProdukList: ResponseProdukList)
        fun onResultSearchkecamatanupdate(responseALamatList: ResponseALamatList)
        fun onResultSearchukuranjasadanmaterial(responseHargalist: ResponseHargaList)
        fun onResultSearchukuranjasasaja(responseHargalist: ResponseHargaList)
        fun onResultSearchHargaprodukjasasaja(responseHargalist: ResponseHargaList)
        fun onResultSearchjasadanmaterial(responseHargalist: ResponseHargaList)
        fun showMessage(message: String)
    }
}