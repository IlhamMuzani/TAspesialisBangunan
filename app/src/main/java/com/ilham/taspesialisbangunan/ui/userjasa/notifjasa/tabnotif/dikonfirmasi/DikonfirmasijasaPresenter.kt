package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.dikonfirmasi

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DikonfirmasijasaPresenter (var view: DikonfirmasijasaContract.View) : DikonfirmasijasaContract.Presenter {

    override fun getPengajuandikonfirmasi(kd_jasa: String) {
        view.onLoadingPengajuandikonfirmasi(true)
        ApiConfig.endpoint.PengajuanjasaDikonfirmasi(kd_jasa).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuandikonfirmasi(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuandikonfirmasi(responsePengajuanList1!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuandikonfirmasi(false)
            }

        })
    }

}