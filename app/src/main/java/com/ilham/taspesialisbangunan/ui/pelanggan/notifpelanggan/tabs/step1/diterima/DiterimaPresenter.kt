package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step1.diterima

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiterimaPresenter (var view: DiterimaContract.View) : DiterimaContract.Presenter {

    override fun getPengajuanditerima(kd_userpelenggan: Long) {
        view.onLoadingPengajuanditerima(true)
        ApiConfig.endpoint.Pengajuanuserditerima(kd_userpelenggan).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanditerima(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanditerima( responsePengajuanList1!! )
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanditerima(false)
            }

        })
    }
}