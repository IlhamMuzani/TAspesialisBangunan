package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.dikonfirmasi

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DikonfirmasiPresenter (var view: DikonfirmasiContract.View) : DikonfirmasiContract.Presenter {

    override fun getPengajuandikonfirmasi(kd_userpelenggan: Long) {
        view.onLoadingPengajuandikonfirmasi(true)
        ApiConfig.endpoint.Pengajuanuserdikonfirmasi(kd_userpelenggan).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuandikonfirmasi(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuandikonfirmasi( responsePengajuanList1!! )
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuandikonfirmasi(false)
            }

        })
    }

    override fun deletePengajuanDikonfirmasi(id: Long) {
        view.onLoadingPengajuandikonfirmasi(true)
        ApiConfig.endpoint.deletePengajuanmenunggu(id).enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoadingPengajuandikonfirmasi(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultDelete( responsePengajuanUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoadingPengajuandikonfirmasi(false)
            }

        })
    }
}