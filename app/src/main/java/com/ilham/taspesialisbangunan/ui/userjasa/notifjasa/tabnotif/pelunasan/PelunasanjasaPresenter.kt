package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.pelunasan

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PelunasanjasaPresenter (var view: PelunasanjasaContract.View) : PelunasanjasaContract.Presenter {

    override fun getPengajuanPelunasan(kd_jasa: String) {
        view.onLoadingPengajuanPelunasan(true)
        ApiConfig.endpoint.pengajuanjasatampilpelunasan(kd_jasa).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanPelunasan(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanPelunasan(responsePengajuanList1!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanPelunasan(false)
            }

        })
    }

}