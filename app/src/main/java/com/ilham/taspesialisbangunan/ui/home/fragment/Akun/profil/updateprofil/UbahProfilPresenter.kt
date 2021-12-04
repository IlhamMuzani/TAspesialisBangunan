package com.ilham.taspesialisbangunan.ui.home.fragment.Akun.profil.updateprofil

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.userpelanggan.ResponsePelangganUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UbahProfilPresenter (val view: UbahProfilContract.View) : UbahProfilContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getDetailProfil(id: Long) {
        view.onLoading(true)
        ApiConfig.endpoint.getPelangganDetail(id).enqueue( object :
            Callback<ResponseUserdetail> {
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    view.onResultDetailProfil( responseUserdetail!! )
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun updateProfil(
        id: Long,
        username: String,
        email: String,
        alamat: String,
        phone: String
    ) {
        view.onLoading(true)
        ApiConfig.endpoint.updatePelanggan(id, username, email, alamat, phone, "PUT") .enqueue(object : Callback<ResponsePelangganUpdate> {
            override fun onResponse(
                call: Call<ResponsePelangganUpdate>,
                response: Response<ResponsePelangganUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePelangganUpdate: ResponsePelangganUpdate? = response.body()
                    view.onResultUpdateProfil( responsePelangganUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponsePelangganUpdate>, t: Throwable) {
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