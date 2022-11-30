package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk

import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.bahanproduk.ResponseBahanprodukList
import com.ilham.taspesialisbangunan.data.model.harga.ResponseHargaList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import java.io.File

interface ProdukCreateContract {

    interface Presenter {
        fun insertProduk(kd_user: String,category: String, jenis_pembuatan: String, model: String, ukuran: String, kecamatan: String, kelurahan: String, alamat: String, phone: String, harga: String,
                         latitude: String, longitude: String, gambar: File, deskripsi: String
        )

        fun insertProdukjasasaja(kd_user: String,category: String, jenis_pembuatan: String, model: String, kecamatan: String, kelurahan: String, alamat: String, phone: String, harga: String,
                         latitude: String, longitude: String, gambar: File, deskripsi: String
        )
        fun searchbahanproduk(keyword: String)
        fun searchkecamatan(keyword: String)
        fun searchukuranjasadanmaterialkanopi(keyword: String)
        fun searchukuranjasadanmaterialpagar(keyword: String)
        fun searchukuranjasadanmaterialtralis(keyword: String)
        fun searchukuranjasadanmaterialtangga(keyword: String)
        fun searchukuranjasadanmaterialhalaman(keyword: String)
        fun searchukuranjasasaja(keyword: String)

        fun searchhargaprodukjasasaja(keyword: String)
        fun searchhargajasadanmaterial(keyword: String)
        fun searchhargajasadanmaterialtepagar(keyword: String)
        fun searchhargajasadanmaterialtangga(keyword: String)
        fun searchhargajasadanmaterialtralis(keyword: String)
        fun searchhargajasadanmaterialtehalaman(keyword: String)


        fun getDetailuserjasa(id: String)
        fun searchAlamatkecamatanupdate(keyword: String)



    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseProdukUpdate: ResponseProdukUpdate)
        fun onResultprodukjasasaja(responseProdukUpdate: ResponseProdukUpdate)
        fun onResultDetailuserjasa(responseUserdetail: ResponseUserdetail)
        fun onResultSearchBahanproduk(responseBahanprodukList: ResponseBahanprodukList)
        fun onResultSearchKecamatan(responseALamatList: ResponseALamatList)
        fun onResultSearchukuranjasadanmaterial(responseHargalist: ResponseHargaList)
        fun onResultSearchukuranjasasaja(responseHargalist: ResponseHargaList)
        fun onResultSearchHargaprodukjasasaja(responseHargalist: ResponseHargaList)
        fun onResultSearchjasadanmaterial(responseHargalist: ResponseHargaList)
        fun showMessage(message: String)
    }
}