package com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil.perbaruipassword

import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerbaruiPasswordPresenter(val view: PerbaruiPasswordContract.View) : PerbaruiPasswordContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun Perbaruipassword(id: Long,password: String, password_confirmation: String) {
        view.onLoading(true, "Loading...")
        ApiConfig.endpoint.Perbaruipassword(id,password, password_confirmation,"PUT")
            .enqueue(object : Callback<ResponseUserUpdate> {
                override fun onResponse(
                    call: Call<ResponseUserUpdate>,
                    response: Response<ResponseUserUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseUserupdate: ResponseUserUpdate? = response.body()
                        view.onResult( responseUserupdate!! )
                    }
                }

                override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }
}