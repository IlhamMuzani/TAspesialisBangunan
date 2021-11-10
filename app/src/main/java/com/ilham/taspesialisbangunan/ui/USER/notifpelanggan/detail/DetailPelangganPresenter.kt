package com.ilham.taspesialisbangunan.ui.USER.notifpelanggan.detail

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekList
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

    override fun getTampilprodukrekening(kd_jasa: String) {
        view.onLoading(true)
        ApiConfig.endpoint.produkrekeningtampil(kd_jasa).enqueue(object : Callback<ResponseTambahrekList>{
            override fun onResponse(
                call: Call<ResponseTambahrekList>,
                response: Response<ResponseTambahrekList>
            ) {
                view.onLoading(true)
                if (response.isSuccessful){
                    val responseTambahrekList: ResponseTambahrekList? = response.body()
                    view.onResultTampilprodukrek(responseTambahrekList!!)

                }
            }

            override fun onFailure(call: Call<ResponseTambahrekList>, t: Throwable) {
                view.onLoading(true)
            }

        })
    }
}