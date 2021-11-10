package com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.Akun.profil.updateprofil

import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.data.model.userpelanggan.DataPelanggan
import com.ilham.taspesialisbangunan.data.model.userpelanggan.ResponsePelangganUpdate
import com.ilham.taspesialisbangunan.data.model.userpelanggan.ResponsePelanggandetail
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
            Callback<ResponsePelanggandetail> {
            override fun onResponse(
                call: Call<ResponsePelanggandetail>,
                response: Response<ResponsePelanggandetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePelanggandetail: ResponsePelanggandetail? = response.body()
                    view.onResultDetailProfil( responsePelanggandetail!! )
                }
            }

            override fun onFailure(call: Call<ResponsePelanggandetail>, t: Throwable) {
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
    override fun setPrefs(prefsManageruser: PrefsManageruser, dataPelanggan: DataPelanggan) {
        prefsManageruser.prefsisLoginuser = true
        prefsManageruser.prefsUsername = dataPelanggan.username!!
        prefsManageruser.prefsEmail = dataPelanggan.email!!
        prefsManageruser.prefsAlamat = dataPelanggan.alamat!!
        prefsManageruser.prefsPhone = dataPelanggan.phone!!
    }
}