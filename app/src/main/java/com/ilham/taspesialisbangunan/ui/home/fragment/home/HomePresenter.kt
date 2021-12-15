package com.ilham.taspesialisbangunan.ui.home.fragment.home

import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(val view : HomeContract.View): HomeContract.Presenter {
    override fun userDetail(id: String) {
        ApiConfig.endpoint.userDetail(id).enqueue(object : Callback<ResponseUserdetail> {
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                if (response.isSuccessful){
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    if (responseUserdetail!!.status){
                        view.onResult(responseUserdetail!!)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {

            }

        })
    }
}