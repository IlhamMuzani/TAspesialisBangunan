package com.ilham.taspesialisbangunan.ui.userjasa.profiljasa.tambahrek

import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekList
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahrekPresenter (var view: TambahrekContract.View) : TambahrekContract.Presenter{

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getTambahrek(kd_user: String) {
        view.onLoadingTambahrek(true)
        ApiConfig.endpoint.myproducttambahrek(kd_user).enqueue(object : Callback<ResponseTambahrekList>{
            override fun onResponse(
                call: Call<ResponseTambahrekList>,
                response: Response<ResponseTambahrekList>
            ) {
            view.onLoadingTambahrek(false)
            if (response.isSuccessful){
                val responseTambahrekList: ResponseTambahrekList? = response.body()
                view.onResultTambahrek( responseTambahrekList!!)
            }
            }

            override fun onFailure(call: Call<ResponseTambahrekList>, t: Throwable) {
            view.onLoadingTambahrek(false)
            }

        })
    }

    override fun deleteTambahrek(kd_rekening: Long) {
        view.onLoadingTambahrek(true)
        ApiConfig.endpoint.deleteTambahrek(kd_rekening).enqueue(object : Callback<ResponseTambahrekUpdate>{
            override fun onResponse(
                call: Call<ResponseTambahrekUpdate>,
                response: Response<ResponseTambahrekUpdate>
            ) {
                view.onLoadingTambahrek(false)
                if (response.isSuccessful){
                    val responseTambahrekUpdate: ResponseTambahrekUpdate? = response.body()
                    view.onResultDelete( responseTambahrekUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponseTambahrekUpdate>, t: Throwable) {
                view.onLoadingTambahrek(false)
            }

        })
    }

}