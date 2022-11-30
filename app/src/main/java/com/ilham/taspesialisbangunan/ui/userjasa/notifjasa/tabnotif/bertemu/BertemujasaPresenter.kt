package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.produkjasa.tabjasa.bertemu

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BertemujasaPresenter (var view: BertemujasaContract.View) : BertemujasaContract.Presenter {

    override fun getPengajuanbertemu(kd_jasa: String) {
        view.onLoadingPengajuanbertemu(true)
        ApiConfig.endpoint.Pengajuanjasabertemu(kd_jasa).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanbertemu(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanbertemu(responsePengajuanList1!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanbertemu(false)
            }

        })
    }

}