package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.diterima

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiterimajasaPresenter (var view: DiterimajasaContract.View) : DiterimajasaContract.Presenter {

    override fun getPengajuanditerimajasa(kd_jasa: String) {
        view.onLoadingPengajuanditerima(true)
        ApiConfig.endpoint.pengajuanjasaditerima(kd_jasa).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanditerima(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanditerima(responsePengajuanList1!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanditerima(false)
            }

        })
    }

}