package com.ilham.taspesialisbangunan.ui.USER.notifpelanggan.tabs.step3.selesai

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelesaiPresenter (var view: SelesaiContract.View) : SelesaiContract.Presenter {

    override fun getPengajuanselesai(kd_userpelenggan: Long) {
        view.onLoadingPengajuanselesai(true)
        ApiConfig.endpoint.Pengajuanuserselesai(kd_userpelenggan).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanselesai(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanselesai( responsePengajuanList1!! )
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanselesai(false)
            }

        })
    }
}