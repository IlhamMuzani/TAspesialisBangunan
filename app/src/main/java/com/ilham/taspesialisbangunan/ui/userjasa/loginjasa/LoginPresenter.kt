package com.ilham.taspesialisbangunan.ui.userjasa.loginjasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter (val view:LoginContract.View): LoginContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun doLogin(phone: String, password: String, fcm: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.login_jasa(phone, password, fcm)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful){
                        val responseUser: ResponseUser? = response.body()
                        view.showMessage(responseUser!!.msg)

                        if (responseUser!!.status) {
                            view.onResult(responseUser)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }

    override fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser) {
        prefsManager.prefsIsLogin = true
        prefsManager.prefsId = dataUser.id.toString()!!
        prefsManager.prefsUsername = dataUser.username!!
        prefsManager.prefsNamatoko = dataUser.nama_toko!!
        prefsManager.prefsPassword = dataUser.password!!
        prefsManager.prefsKecamatan = dataUser.kecamatan!!
        prefsManager.prefsKelurahan = dataUser.kelurahan!!
        prefsManager.prefsAlamat = dataUser.alamat!!
        prefsManager.prefsLatitude = dataUser.latitude!!
        prefsManager.prefsLongitude = dataUser.longitude!!
        prefsManager.prefsPhone = dataUser.phone!!
        prefsManager.prefsStatus = dataUser.status!!
    }
}