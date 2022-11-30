package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.menunggu

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenunggujasaPresenter (var view: MenunggujasaContract.View) : MenunggujasaContract.Presenter {

    override fun getPengajuanmenunggujasa(kd_jasa: String) {
        view.onLoadingPengajuanmenunggu(true)
        ApiConfig.endpoint.pengajuanjasamenunggu(kd_jasa).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanmenunggu(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanmenunggu(responsePengajuanList1!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanmenunggu(false)
            }

        })
    }

}