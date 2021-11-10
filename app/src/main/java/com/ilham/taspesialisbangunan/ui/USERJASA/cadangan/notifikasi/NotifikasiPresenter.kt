package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.notifikasi

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifikasiPresenter (var view: NotifikasiContract.View) : NotifikasiContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getNotifPengajuan(kd_jasa: String) {
        view.onLoadingNotifikasi(true)
        ApiConfig.endpoint.pengajuanjasamenunggu(kd_jasa).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingNotifikasi(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultNotifPengajuan(responsePengajuanList1!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingNotifikasi(false)
            }

        })
    }

    override fun mypengajuan(kd_jasa: String) {
        view.onLoadingNotifikasi(true)
//        ApiConfig.endpoint.mypengajuan(kd_jasa).enqueue(object : Callback<ResponsePengajuanList> {
//            override fun onResponse(
//                call: Call<ResponsePengajuanList>,
//                response: Response<ResponsePengajuanList>
//            ) {
//                view.onLoadingNotifikasi(false)
//                if (response.isSuccessful) {
//                    val responsePengajuanList1: ResponsePengajuanList? = response.body()
//                    view.onResultNotifPengajuan(responsePengajuanList1!!)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponsePengajuanList>, t: Throwable) {
//                view.onLoadingNotifikasi(false)
//            }
//        })
    }

    override fun getNotifSaran() {
        view.onLoadingNotifikasi(true)
        ApiConfig.endpoint.getSaran().enqueue(object : Callback<ResponseSaranList> {
            override fun onResponse(
                call: Call<ResponseSaranList>,
                response: Response<ResponseSaranList>
            ) {
                view.onLoadingNotifikasi(false)
                if (response.isSuccessful) {
                    val responseSaranList: ResponseSaranList? = response.body()
                    view.onResultNotifSaran(responseSaranList!!)
                }
            }

            override fun onFailure(call: Call<ResponseSaranList>, t: Throwable) {
                view.onLoadingNotifikasi(false)
            }

        })
    }

    override fun mysaran(kd_jasa: String) {
        view.onLoadingNotifikasi(true)
        ApiConfig.endpoint.mysaran(kd_jasa).enqueue(object : Callback<ResponseSaranList> {
            override fun onResponse(
                call: Call<ResponseSaranList>,
                response: Response<ResponseSaranList>
            ) {
                view.onLoadingNotifikasi(false)
                if (response.isSuccessful) {
                    val responseSaranList: ResponseSaranList? = response.body()
                    view.onResultNotifSaran(responseSaranList!!)
                }
            }

            override fun onFailure(call: Call<ResponseSaranList>, t: Throwable) {
                view.onLoadingNotifikasi(false)
            }
        })
    }

    override fun insertPengajuanterima(kd_pengajuan: Long) {
        ApiConfig.endpoint.Pengajuanterima(kd_pengajuan)
            .enqueue(object : Callback<ResponsePengajuanUpdate> {
                override fun onResponse(
                    call: Call<ResponsePengajuanUpdate>,
                    response: Response<ResponsePengajuanUpdate>
                ) {

                    if (response.isSuccessful) {
                        val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                        view.onResultPengajuanUpdate(responsePengajuanUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {

                }
            })
    }

    override fun insertPengajuantolak(kd_pengajuan: Long) {
        ApiConfig.endpoint.Pengajuantolak(kd_pengajuan)
            .enqueue(object : Callback<ResponsePengajuanUpdate> {
                override fun onResponse(
                    call: Call<ResponsePengajuanUpdate>,
                    response: Response<ResponsePengajuanUpdate>
                ) {

                    if (response.isSuccessful) {
                        val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                        view.onResultPengajuanUpdate(responsePengajuanUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {

                }
            })
    }
}