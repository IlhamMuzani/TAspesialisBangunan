package com.ilham.taspesialisbangunan.ui.USERJASA.loginjasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.loginjasa.DataLogin
import com.ilham.taspesialisbangunan.data.model.loginjasa.ResponseLogin

interface LoginContract {

    interface Presenter {
        fun doLogin(email: String, password: String)
        fun setPrefs(prefsManager: PrefsManager, dataLogin: DataLogin)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseLogin: ResponseLogin)
        fun showMessage(message: String)
    }

}