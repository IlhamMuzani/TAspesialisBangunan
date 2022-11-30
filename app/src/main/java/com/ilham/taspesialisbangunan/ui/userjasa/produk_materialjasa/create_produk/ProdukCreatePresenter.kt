package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk

import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.bahanproduk.ResponseBahanprodukList
import com.ilham.taspesialisbangunan.data.model.harga.ResponseHargaList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProdukCreatePresenter(val view: ProdukCreateContract.View) : ProdukCreateContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun insertProduk(
        jasausers_id: String,
        category: String,
        jenis_pembuatan: String,
        model: String,
        ukuran: String,
        kecamatan: String,
        kelurahan: String,
        alamat: String,
        phone: String,
        harga: String,
        latitude: String,
        longitude: String,
        gambar: File,
        deskripsi: String
    ) {

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData(
            "gambar",
            gambar.name, requestBody
        )

        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.insertProduk(
            jasausers_id,
            category,
            jenis_pembuatan,
            model,
            ukuran,
            kecamatan,
            kelurahan,
            alamat,
            phone,
            harga,
            latitude,
            longitude,
            multipartBody!!,
            deskripsi
        )
            .enqueue(object : Callback<ResponseProdukUpdate> {
                override fun onResponse(
                    call: Call<ResponseProdukUpdate>,
                    response: Response<ResponseProdukUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseProdukUpdate: ResponseProdukUpdate? = response.body()
                        view.onResult(responseProdukUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseProdukUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }

    override fun insertProdukjasasaja(
        kd_user: String,
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
        gambar: File,
        deskripsi: String
    ) {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData(
            "gambar",
            gambar.name, requestBody
        )

        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.insertProdukjasasaja(
            kd_user,
            category,
            jenis_pembuatan,
            model,
            kecamatan,
            kelurahan,
            alamat,
            phone,
            harga,
            latitude,
            longitude,
            multipartBody!!,
            deskripsi
        )
            .enqueue(object : Callback<ResponseProdukUpdate> {
                override fun onResponse(
                    call: Call<ResponseProdukUpdate>,
                    response: Response<ResponseProdukUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseProdukUpdate: ResponseProdukUpdate? = response.body()
                        view.onResultprodukjasasaja(responseProdukUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseProdukUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }

    override fun searchbahanproduk(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchBahanproduk(keyword).enqueue(object : Callback<ResponseBahanprodukList>{
            override fun onResponse(
                call: Call<ResponseBahanprodukList>,
                response: Response<ResponseBahanprodukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseBahanprodukList: ResponseBahanprodukList? = response.body()
                    view.onResultSearchBahanproduk(responseBahanprodukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseBahanprodukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchkecamatan(keyword: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.searchAlamatkecamatan(keyword).enqueue(object : Callback<ResponseALamatList>{
            override fun onResponse(
                call: Call<ResponseALamatList>,
                response: Response<ResponseALamatList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseALamatList: ResponseALamatList? = response.body()
                    view.onResultSearchKecamatan(responseALamatList!!)
                }
            }

            override fun onFailure(call: Call<ResponseALamatList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchukuranjasadanmaterialkanopi(keyword: String) {
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

    override fun searchukuranjasadanmaterialpagar(keyword: String) {
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

    override fun searchukuranjasadanmaterialtralis(keyword: String) {
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

    override fun searchukuranjasadanmaterialtangga(keyword: String) {
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

    override fun searchukuranjasadanmaterialhalaman(keyword: String) {
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

    override fun searchukuranjasasaja(keyword: String) {
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

    override fun searchhargaprodukjasasaja(keyword: String) {
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

    override fun searchhargajasadanmaterial(keyword: String) {
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

    override fun searchhargajasadanmaterialtepagar(keyword: String) {
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

    override fun searchhargajasadanmaterialtangga(keyword: String) {
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

    override fun searchhargajasadanmaterialtralis(keyword: String) {
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

    override fun searchhargajasadanmaterialtehalaman(keyword: String) {
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

    override fun getDetailuserjasa(id: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.userDetail(id).enqueue( object :
            Callback<ResponseUserdetail> {
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    view.onResultDetailuserjasa( responseUserdetail!! )
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchAlamatkecamatanupdate(keyword: String) {
        ApiConfig.endpoint.searchAlamatkecamatan(keyword).enqueue(object : Callback<ResponseALamatList>{
            override fun onResponse(
                call: Call<ResponseALamatList>,
                response: Response<ResponseALamatList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseALamatList: ResponseALamatList? = response.body()
                    view.onResultSearchKecamatan(responseALamatList!!)
                }
            }

            override fun onFailure(call: Call<ResponseALamatList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}