package com.ilham.taspesialisbangunan.ui.pelanggan.phoneferifi

import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhoneverifiPresenter(val view: PhoneverifiContract.View) : PhoneverifiContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getverifi(id: Long) {
        view.onLoading(true,"Verifikasi Berhasil...")
        ApiConfig.endpoint.userverifi(id).enqueue(object : Callback<ResponseUserdetail>{
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    view.onResultDatauserupdate( responseUserdetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoading(false)
            }
        } )
    }

    override fun phone_baru(id: Long, phone: String) {
        view.onLoading(true, "Loading...")
        ApiConfig.endpoint.userupdatephone(id, phone, "PUT")
            .enqueue(object : Callback<ResponseUserUpdate> {
                override fun onResponse(
                    call: Call<ResponseUserUpdate>,
                    response: Response<ResponseUserUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseUserUpdate: ResponseUserUpdate? = response.body()
                        view.onResultphonebaru( responseUserUpdate!! )
                    }
                }

                override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }
}