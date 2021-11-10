package com.ilham.taspesialisbangunan.ui.USER.loginuser

import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.data.model.loginuser.DataLoginuser
import com.ilham.taspesialisbangunan.data.model.loginuser.ResponseLoginuser

interface LoginuserContract {

    interface Presenter {
        fun doLogin(email: String, password: String)
        fun setPrefs(prefsManageruser: PrefsManageruser, dataLoginuser: DataLoginuser)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseLoginuser: ResponseLoginuser)
        fun showMessage(message: String)
    }

}