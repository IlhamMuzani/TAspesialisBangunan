package com.ilham.taspesialisbangunan.ui.userjasa.tambahrek.create

import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahrekCreatePresenter (val view: TambahrekCreateContract.View) : TambahrekCreateContract.Presenter{

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }


    override fun insertTambahrek(
        kd_user: String,
        jenis_bank: String,
        norek: String,
        nama: String
    ) {

        view.onLoading(true)
        ApiConfig.endpoint.insertTambahrek(
            kd_user,
            jenis_bank,
            norek,
            nama
        )
            .enqueue(object : Callback<ResponseTambahrekUpdate>{
                override fun onResponse(
                    call: Call<ResponseTambahrekUpdate>,
                    response: Response<ResponseTambahrekUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful){
                        val responseTambahrekUpdate: ResponseTambahrekUpdate? = response.body()
                        view.onResult( responseTambahrekUpdate!! )
                    }
                }

                override fun onFailure(call: Call<ResponseTambahrekUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }

}