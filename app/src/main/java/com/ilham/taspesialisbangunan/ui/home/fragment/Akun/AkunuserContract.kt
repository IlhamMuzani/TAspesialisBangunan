package com.ilham.taspesialisbangunan.ui.home.fragment.Akun

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail

interface AkunuserContract {

    interface Presenter {
        fun doLogin(prefsManager: PrefsManager)
        fun doLogout(prefsManager: PrefsManager)
        fun profilDetail(id:String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onResultLogin(prefsManager: PrefsManager)
        fun onResultLogout()
        fun onResult(responseUserdetail: ResponseUserdetail)
        fun showMessage(message: String)
    }

}