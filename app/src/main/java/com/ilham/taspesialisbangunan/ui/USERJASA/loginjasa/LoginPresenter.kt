package com.ilham.taspesialisbangunan.ui.USERJASA.loginjasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.loginjasa.DataLogin
import com.ilham.taspesialisbangunan.data.model.loginjasa.ResponseLogin
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

    override fun doLogin(email: String, password: String) {
        view.onLoading(true)
        ApiConfig.endpoint.loginJasauser(email, password)
            .enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful){
                        val responseLogin: ResponseLogin? = response.body()
                        view.showMessage(responseLogin!!.msg)

                        if (responseLogin!!.status) {
                            view.onResult(responseLogin)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }

    override fun setPrefs(prefsManager: PrefsManager, dataLogin: DataLogin) {
        prefsManager.prefsIsLogin = true
        prefsManager.prefsId = dataLogin.id!!
        prefsManager.prefsUsername = dataLogin.username!!
        prefsManager.prefsEmail = dataLogin.email!!
        prefsManager.prefsPassword = dataLogin.password!!
        prefsManager.prefsAlamat = dataLogin.alamat!!
        prefsManager.prefsPhone = dataLogin.phone!!
    }
}