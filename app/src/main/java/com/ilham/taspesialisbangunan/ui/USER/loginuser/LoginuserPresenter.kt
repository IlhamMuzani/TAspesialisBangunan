package com.ilham.taspesialisbangunan.ui.USER.loginuser

import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.data.model.loginuser.DataLoginuser
import com.ilham.taspesialisbangunan.data.model.loginuser.ResponseLoginuser
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginuserPresenter (val view: LoginuserContract.View): LoginuserContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading( false )

    }


    override fun doLogin(email: String, password: String) {
        view.onLoading(true)
        ApiConfig.endpoint.loginUserpelanggan(email, password)
            .enqueue(object : Callback<ResponseLoginuser> {
                override fun onResponse(
                    call: Call<ResponseLoginuser>,
                    response: Response<ResponseLoginuser>
                ) {
                    view.onLoading(false)
                    if(response.isSuccessful) {
                        val responseLoginuser: ResponseLoginuser? = response.body()
                        view.showMessage(responseLoginuser!!.msg)

                        if (responseLoginuser!!.status) {
                            view.onResult(responseLoginuser)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseLoginuser>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }
    override fun setPrefs(prefsManageruser: PrefsManageruser, dataLoginuser: DataLoginuser) {
        prefsManageruser.prefsisLoginuser = true
        prefsManageruser.prefsId = dataLoginuser.id!!
        prefsManageruser.prefsUsername = dataLoginuser.username!!
        prefsManageruser.prefsEmail = dataLoginuser.email!!
        prefsManageruser.prefsPassword = dataLoginuser.password!!
        prefsManageruser.prefsAlamat = dataLoginuser.alamat!!
        prefsManageruser.prefsPhone = dataLoginuser.phone!!
    }

}