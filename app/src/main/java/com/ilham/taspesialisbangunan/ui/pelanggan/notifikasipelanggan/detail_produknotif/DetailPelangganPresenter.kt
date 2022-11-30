package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.detailnotif

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekTampilrekening
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DetailPelangganPresenter(val view: DetailPelangganContract.View): DetailPelangganContract.Presenter {

   init {
       view.initActivity()
       view.initListener()
       view.onLoading(true)
   }
    override fun getDetail(id: Long) {
        view.onLoading(true)
        ApiConfig.endpoint.detailPengajuan(id).enqueue(object: Callback<ResponsePengajuanDetail>{
            override fun onResponse(
                call: Call<ResponsePengajuanDetail>,
                response: Response<ResponsePengajuanDetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanDetail: ResponsePengajuanDetail? = response.body()
                    view.onResultDetail(responsePengajuanDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanDetail>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun buktiPengajuan(id: Long, bukti: File) {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), bukti)
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("bukti",
            bukti.name, requestBody)
        view.onLoading(true)
        ApiConfig.endpoint.buktiPengajuan(id, multipartBody, "PUT").enqueue(object: Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdate(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun buktiPengajuanMaterial(id: Long, bukti: File) {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), bukti)
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("bukti",
            bukti.name, requestBody)
        view.onLoading(true)
        ApiConfig.endpoint.buktiPengajuanMaterial(id, multipartBody, "PUT").enqueue(object: Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdate(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun buktiPelunasan(id: Long, bukti: File) {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), bukti)
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("bukti",
            bukti.name, requestBody)
        view.onLoading(true)
        ApiConfig.endpoint.buktiPelunasan(id, multipartBody, "PUT").enqueue(object: Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdate(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun getTampilprodukrekening(kd_user: String) {
        view.onLoading(true)
        ApiConfig.endpoint.produkrekeningtampil(kd_user).enqueue(object : Callback<ResponseTambahrekTampilrekening>{
            override fun onResponse(
                call: Call<ResponseTambahrekTampilrekening>,
                response: Response<ResponseTambahrekTampilrekening>
            ) {
                view.onLoading(false)
                if (response.isSuccessful){
                    val responseTambahrekTampilrekening: ResponseTambahrekTampilrekening? = response.body()
                    view.onResultTampilprodukrek(responseTambahrekTampilrekening!!)

                }
            }

            override fun onFailure(call: Call<ResponseTambahrekTampilrekening>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun insertSaran(kd_produk: String, kd_user: String, kd_pengajuan: String, deskripsi: String) {
        view.onLoading(true)
        ApiConfig.endpoint.insertSaran(kd_produk, kd_user, kd_pengajuan, deskripsi).enqueue(object: Callback<ResponseSaranInsert>{
            override fun onResponse(
                call: Call<ResponseSaranInsert>,
                response: Response<ResponseSaranInsert>
            ) {

                view.onLoading(true)
                if (response.isSuccessful) {
                    val responseSaranInsert: ResponseSaranInsert? = response.body()
                    view.onResultSaran(responseSaranInsert!!)
                }
            }

            override fun onFailure(call: Call<ResponseSaranInsert>, t: Throwable) {
                view.onLoading(true)
            }

        })
    }
}