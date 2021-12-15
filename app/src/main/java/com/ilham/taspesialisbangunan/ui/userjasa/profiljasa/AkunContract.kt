package com.ilham.taspesialisbangunan.ui.userjasa.profiljasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager

interface AkunContract {

    interface Presenter {
        fun doLogin(prefsManager: PrefsManager)
        fun doLogout(prefsManager: PrefsManager)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onResultLogin(prefsManager: PrefsManager)
        fun onResultLogout()
        fun showMessage(message: String)
    }

}