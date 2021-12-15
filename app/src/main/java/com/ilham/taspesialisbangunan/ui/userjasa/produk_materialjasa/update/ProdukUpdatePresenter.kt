package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.update

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
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
    }


    override fun getDetail(id: Long) {
        view.onLoading(true)
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
        nama_toko: String,
        jenis_pembuatan: String,
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

        view.onLoading(true)
        ApiConfig.endpoint.updateProduk(kd_produkjasa, nama_toko, jenis_pembuatan, alamat, phone, harga, latitude,
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

}