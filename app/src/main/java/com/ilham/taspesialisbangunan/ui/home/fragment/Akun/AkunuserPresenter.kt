package com.ilham.taspesialisbangunan.ui.home.fragment.Akun

import com.google.android.gms.common.api.Api
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AkunuserPresenter (val view: AkunuserContract.View): AkunuserContract.Presenter {

    override fun doLogin(prefsManager: PrefsManager) {
        if (prefsManager.prefsIsLogin) view.onResultLogin(prefsManager)
    }



    override fun doLogout(prefsManager: PrefsManager) {
        prefsManager.logout()
        view.showMessage("Berhasil Keluar")
        view.onResultLogout()
    }

    override fun profilDetail(id: String) {
        ApiConfig.endpoint.ProfilDetail(id).enqueue(object : Callback<ResponseUserdetail>{
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    if (responseUserdetail!!.status)
                        view.onResult(responseUserdetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {

            }

        })
    }

}