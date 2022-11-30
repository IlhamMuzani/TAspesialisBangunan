package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.produkjasa.tabjasa.dp

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DPjasaPresenter (var view: DPjasaContract.View) : DPjasaContract.Presenter {

    override fun getPengajuanDP(kd_jasa: String) {
        view.onLoadingPengajuanDP(true)
        ApiConfig.endpoint.pengajuanjasaDP(kd_jasa).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanDP(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanDP(responsePengajuanList1!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanDP(false)
            }

        })
    }

}