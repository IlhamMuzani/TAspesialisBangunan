package com.ilham.taspesialisbangunan.ui.home.fragment.Akun

import com.ilham.taspesialisbangunan.data.database.PrefsManager

interface AkunuserContract {

    interface Presenter {
        fun doLogin(prefsManager: PrefsManager)
        fun doLogout(prefsManager: PrefsManager)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onResultLogin(prefsManager: PrefsManager)
        fun onResultLogout()
        fun showMessage(message: String)
    }

}