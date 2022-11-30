package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.tabsprodukjasa.step1.menunggu

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenungguPresenter (var view: MenungguContract.View) : MenungguContract.Presenter {

    override fun getPengajuanmenunggu(kd_userpelenggan: Long) {
        view.onLoadingPengajuanmenunggu(true)
        ApiConfig.endpoint.Pengajuanusermenunggu(kd_userpelenggan).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanmenunggu(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanmenunggu( responsePengajuanList1!! )
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanmenunggu(false)
            }

        })
    }

    override fun deletePengajuanMenunggu(id: Long) {
        view.onLoadingPengajuanmenunggu(true)
        ApiConfig.endpoint.deletePengajuanmenunggu(id).enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoadingPengajuanmenunggu(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultDelete( responsePengajuanUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoadingPengajuanmenunggu(false)
            }

        })
    }
}