package com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseJasaUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.user.ResponsePelangganUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UbahProfiljasaPresenter (val view: UbahProfiljasaContract.View) : UbahProfiljasaContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getDetailProfiljasa(id: String) {
        view.onLoading(true)
        ApiConfig.endpoint.userDetail(id).enqueue( object :
            Callback<ResponseUserdetail> {
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    view.onResultDetailProfiljasa( responseUserdetail!! )
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun updateProfiljasa(
        id: Long,
        username: String,
        email: String,
        alamat: String,
        phone: String
    ) {
        view.onLoading(true)
        ApiConfig.endpoint.updateJasa(id, username, email, alamat, phone, "PUT") .enqueue(object : Callback<ResponseJasaUpdate> {
            override fun onResponse(
                call: Call<ResponseJasaUpdate>,
                response: Response<ResponseJasaUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseJasaUpdate: ResponseJasaUpdate? = response.body()
                    view.onResultUpdateProfiljasa( responseJasaUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponseJasaUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
    override fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser) {
        prefsManager.prefsIsLogin = true
        prefsManager.prefsUsername = dataUser.username!!
        prefsManager.prefsEmail = dataUser.email!!
        prefsManager.prefsAlamat = dataUser.alamat!!
        prefsManager.prefsPhone = dataUser.phone!!
    }
}