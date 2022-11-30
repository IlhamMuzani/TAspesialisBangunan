package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.tabsprodukjasa.step2.diproses

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiprosesPresenter (var view: DiprosesContract.View) : DiprosesContract.Presenter {


    override fun getPengajuandiproses(kd_userpelenggan: Long) {
        view.onLoadingPengajuandiproses(true)
        ApiConfig.endpoint.Pengajuanuserditerima(kd_userpelenggan).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuandiproses(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuandiproses( responsePengajuanList1!! )
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuandiproses(false)
            }

        })
    }
}