package com.ilham.taspesialisbangunan.ui.fragment.notifications

import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import com.ilham.taspesialisbangunan.ui.fragment.home.HomeContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsPresenter (val view: NotificationsContract.View): HomeContract.Presenter {

    override fun userDetail(id: String) {
        ApiConfig.endpoint.userDetail(id).enqueue(object :Callback<ResponseUserdetail>{
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                if (response.isSuccessful){
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    if (responseUserdetail!!.status){
                        view.onResult(responseUserdetail)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {

            }

        })
    }
}