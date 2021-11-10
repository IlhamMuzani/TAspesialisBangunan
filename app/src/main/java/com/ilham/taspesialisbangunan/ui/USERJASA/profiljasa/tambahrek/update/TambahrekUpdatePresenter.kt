package com.ilham.taspesialisbangunan.ui.USERJASA.profiljasa.tambahrek.update

import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekDetail
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahrekUpdatePresenter (var view: TambahrekUpdateContract.View) : TambahrekUpdateContract.Presenter{

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getDetailTambahrek(kd_rekening: Long) {
        view.onLoading(true)
        ApiConfig.endpoint.getTambahrekDetail(kd_rekening)
            .enqueue(object : Callback<ResponseTambahrekDetail> {
                override fun onResponse(
                    call: Call<ResponseTambahrekDetail>,
                    response: Response<ResponseTambahrekDetail>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseTambahrekDetail: ResponseTambahrekDetail? = response.body()
                        view.onResultDetailTambahrek(responseTambahrekDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseTambahrekDetail>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }

    override fun updateTambahrek(
        kd_rekening: Long,
        jenis_bank: String,
        norek: String,
        nama: String
    ) {
        view.onLoading(true)
        ApiConfig.endpoint.updateTambahrek(
            kd_rekening, jenis_bank, norek, nama,"PUT"
        ).enqueue(object : Callback<ResponseTambahrekUpdate> {
            override fun onResponse(
                call: Call<ResponseTambahrekUpdate>,
                response: Response<ResponseTambahrekUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseTambahrekUpdate: ResponseTambahrekUpdate? = response.body()
                    view.onResultUpdateTambahrek(responseTambahrekUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseTambahrekUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}