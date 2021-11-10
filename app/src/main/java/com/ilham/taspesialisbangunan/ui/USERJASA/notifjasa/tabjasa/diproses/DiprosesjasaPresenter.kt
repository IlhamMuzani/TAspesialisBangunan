package com.ilham.taspesialisbangunan.ui.USERJASA.notifjasa.tabjasa.diproses

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiprosesjasaPresenter (var view: DiprosesjasaContract.View) : DiprosesjasaContract.Presenter {

    override fun getPengajuanjasadiproses(kd_jasa: String) {
        view.onLoadingPengajuandiproses(true)
        ApiConfig.endpoint.pengajuanjasatampildiproses(kd_jasa).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuandiproses(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuandiproses(responsePengajuanList1!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuandiproses(false)
            }

        })
    }

}