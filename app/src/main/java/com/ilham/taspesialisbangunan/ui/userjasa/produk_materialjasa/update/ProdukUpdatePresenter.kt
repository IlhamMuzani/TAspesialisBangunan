package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.update

import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.bahanproduk.ResponseBahanprodukList
import com.ilham.taspesialisbangunan.data.model.harga.ResponseHargaList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File
import retrofit2.Callback
import retrofit2.Response

class ProdukUpdatePresenter (val view: ProdukUpdateContract.View) : ProdukUpdateContract.Presenter {


    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getDetail(id: Long) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.produkDetail(id).enqueue( object : Callback<ResponseProdukDetail> {
            override fun onResponse(
                call: Call<ResponseProdukDetail>,
                response: Response<ResponseProdukDetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukDetail:ResponseProdukDetail? = response.body()
                    view.onResultDetail( responseProdukDetail!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukDetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun updateProduk(
        kd_produkjasa: Long,
        category: String,
        jenis_pembuatan: String,
        model: String,
        ukuran: String,
//        bahan: String,
//        berat: String,
        kecamatan: String,
        kelurahan: String,
        alamat: String,
        phone: String,
        harga: String,
        latitude: String,
        longitude: String,
        gambar: File?,
        deskripsi: String
    ) {
        val requestBody: RequestBody
        val multipartBody: MultipartBody.Part

        if (gambar != null) {
            requestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
            multipartBody = MultipartBody.Part.createFormData("gambar",
                gambar.name, requestBody)
        } else {
            requestBody = RequestBody.create(MediaType.parse("image/*"), "")
            multipartBody= MultipartBody.Part.createFormData("gambar",
                "", requestBody)
        }

        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.updateProduk(kd_produkjasa, category, jenis_pembuatan, model, ukuran, kecamatan, kelurahan, alamat, phone, harga, latitude,
            longitude, multipartBody, deskripsi, "PUT") .enqueue(object : Callback<ResponseProdukUpdate> {
            override fun onResponse(
                call: Call<ResponseProdukUpdate>,
                response: Response<ResponseProdukUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukUpdate: ResponseProdukUpdate? = response.body()
                    view.onResultUpdate( responseProdukUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchukuranjasadanmaterialkanopiupdate(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchukuranjasadanmaterialkanopi(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchukuranjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchukuranjasadanmaterialpagarupdate(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchukuranjasadanmaterialpagar(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchukuranjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchukuranjasadanmaterialtralisupdate(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchukuranjasadanmaterialtralis(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchukuranjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchukuranjasadanmaterialtanggaupdate(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchukuranjasadanmaterialtangga(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchukuranjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchukuranjasadanmaterialhalamanupdate(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchukuranjasadanmaterialhalaman(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchukuranjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchukuranjasasajaupdate(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchukuranjasasaja(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchukuranjasasaja(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchhargaprodukjasasajaupdate(keyword: String) {
        ApiConfig.endpoint.searchHargaprodukjasasaja(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchHargaprodukjasasaja(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchhargajasadanmaterialupdatekanopi(keyword: String) {
        ApiConfig.endpoint.searchHargajasadanmaterial(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchhargajasadanmaterialupdatepagar(keyword: String) {
        ApiConfig.endpoint.searchHargajasadanmaterialpagar(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchhargajasadanmaterialupdatetangga(keyword: String) {
        ApiConfig.endpoint.searchHargajasadanmaterialtangga(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchhargajasadanmaterialupdatetralis(keyword: String) {
        ApiConfig.endpoint.searchHargajasadanmaterialtralis(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchhargajasadanmaterialupdatehalaman(keyword: String) {
        ApiConfig.endpoint.searchHargajasadanmaterialhalaman(keyword).enqueue(object : Callback<ResponseHargaList>{
            override fun onResponse(
                call: Call<ResponseHargaList>,
                response: Response<ResponseHargaList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseHargaList: ResponseHargaList? = response.body()
                    view.onResultSearchjasadanmaterial(responseHargaList!!)
                }
            }

            override fun onFailure(call: Call<ResponseHargaList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun updatejasasaja(
        id: Long,
        category: String,
        jenis_pembuatan: String,
        model: String,
        kecamatan: String,
        kelurahan: String,
        alamat: String,
        phone: String,
        harga: String,
        latitude: String,
        longitude: String,
        gambar: File?,
        deskripsi: String
    ) {
        val requestBody: RequestBody
        val multipartBody: MultipartBody.Part

        if (gambar != null) {
            requestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
            multipartBody = MultipartBody.Part.createFormData("gambar",
                gambar.name, requestBody)
        } else {
            requestBody = RequestBody.create(MediaType.parse("image/*"), "")
            multipartBody= MultipartBody.Part.createFormData("gambar",
                "", requestBody)
        }

        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.updateProdukjasasaja(id, category, jenis_pembuatan, model, kecamatan, kelurahan, alamat, phone, harga, latitude,
            longitude, multipartBody, deskripsi, "PUT") .enqueue(object : Callback<ResponseProdukUpdate> {
            override fun onResponse(
                call: Call<ResponseProdukUpdate>,
                response: Response<ResponseProdukUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukUpdate: ResponseProdukUpdate? = response.body()
                    view.onResultUpdate( responseProdukUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchCategoriprodukupdate(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchJenisPembuatan(keyword).enqueue(object : Callback<ResponseProdukList>{
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultSearchProdukupdate(responseProdukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchkecamatanprodukupdate(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchAlamatkecamatan(keyword).enqueue(object : Callback<ResponseALamatList> {
            override fun onResponse(
                call: Call<ResponseALamatList>,
                response: Response<ResponseALamatList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseALamatList: ResponseALamatList? = response.body()
                    view.onResultSearchkecamatanupdate(responseALamatList!!)
                }
            }

            override fun onFailure(call: Call<ResponseALamatList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

}