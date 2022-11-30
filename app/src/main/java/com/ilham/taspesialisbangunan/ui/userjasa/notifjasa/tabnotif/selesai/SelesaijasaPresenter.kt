package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.produkjasa.tabjasa.selesai

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelesaijasaPresenter (var view: SelesaijasaContract.View) : SelesaijasaContract.Presenter {

    override fun getPengajuanSelesai(kd_jasa: String) {
        view.onLoadingPengajuanSelesai(true)
        ApiConfig.endpoint.pengajuanjasatampilselesai(kd_jasa).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanSelesai(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanSelesai(responsePengajuanList1!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanSelesai(false)
            }

        })
    }

}