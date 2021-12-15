package com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.produk.produkdetail

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProdukdetailPresenter  (var view: ProdukdetailContract.View) : ProdukdetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoadingProdukdetail(false)
    }

    override fun getProdukdetail(id: Long) {
        view.onLoadingProdukdetail(true)
        ApiConfig.endpoint.produkDetail(id).enqueue(object : Callback<ResponseProdukDetail>{
            override fun onResponse(
                call: Call<ResponseProdukDetail>,
                response: Response<ResponseProdukDetail>
            ) {
                view.onLoadingProdukdetail(false)
                if (response.isSuccessful) {
                    val responseProdukDetail: ResponseProdukDetail? = response.body()
                    view.onResultProdukdetail( responseProdukDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukDetail>, t: Throwable) {
                view.onLoadingProdukdetail(false)
            }
        } )
    }

    override fun insertPengajuan(kd_jasa: String, kd_userpelanggan: String, gambar: File,
                                 deskripsi: String, status: String) {

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
        val multipartBody: MultipartBody.Part? = MultipartBody. Part.createFormData("gambar",
            gambar.name, requestBody)

        ApiConfig.endpoint.insertPengajuan(kd_jasa, kd_userpelanggan, multipartBody!!, deskripsi, status).enqueue(object: Callback<ResponsePengajuanInsert>{
            override fun onResponse(
                call: Call<ResponsePengajuanInsert>,
                response: Response<ResponsePengajuanInsert>
            ) {

                if (response.isSuccessful) {
                    val responsePengajuanInsert: ResponsePengajuanInsert? = response.body()
                    view.onResultPengajuan(responsePengajuanInsert!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanInsert>, t: Throwable) {

            }

        })
    }

    override fun insertSaran(kd_jasa: String, kd_userpelanggan: String, deskripsi: String) {

        ApiConfig.endpoint.insertSaran(kd_jasa, kd_userpelanggan, deskripsi)
            .enqueue(object : Callback<ResponseSaranInsert> {
                override fun onResponse(
                    call: Call<ResponseSaranInsert>,
                    response: Response<ResponseSaranInsert>
                ) {

                    if (response.isSuccessful) {
                        val responseSaranInsert: ResponseSaranInsert? = response.body()
                        view.onResultSaran(responseSaranInsert!!)
                    }
                }

                override fun onFailure(call: Call<ResponseSaranInsert>, t: Throwable) {

                }

            })
    }
}