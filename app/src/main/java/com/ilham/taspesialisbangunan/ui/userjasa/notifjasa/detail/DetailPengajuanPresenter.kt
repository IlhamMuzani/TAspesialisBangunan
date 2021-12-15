package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.detail

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPengajuanPresenter(val view: DetailPengajuanContract.View): DetailPengajuanContract.Presenter {

   init {
       view.initActivity()
       view.initListener()
   }
    override fun getDetail(id: Long) {
        view.onLoading(true)
        ApiConfig.endpoint.detailPengajuan(id).enqueue(object: Callback<ResponsePengajuanDetail>{
            override fun onResponse(
                call: Call<ResponsePengajuanDetail>,
                response: Response<ResponsePengajuanDetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanDetail: ResponsePengajuanDetail? = response.body()
                    view.onResultDetail(responsePengajuanDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanDetail>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun hargaPengajuan(id: Long, harga: String) {
        view.onLoading(true)
        ApiConfig.endpoint.hargaPengajuan(id, harga, "PUT").enqueue(object: Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdateharga(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun pengajuandiproses(id: Long) {
        ApiConfig.endpoint.pengajuanjasadiproses(id, "PUT").enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdateproses(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun pengajuanselesai(id: Long) {
        ApiConfig.endpoint.pengajuanjasaselesai(id, "PUT").enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdateharga(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}