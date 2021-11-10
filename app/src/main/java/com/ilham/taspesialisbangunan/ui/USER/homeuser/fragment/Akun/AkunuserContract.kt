package com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.Akun

import android.view.View
import com.ilham.taspesialisbangunan.data.database.PrefsManageruser

interface AkunuserContract {

    interface Presenter {
        fun doLogin(prefsManageruser: PrefsManageruser)
        fun doLogout(prefsManageruser: PrefsManageruser)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onResultLogin(prefsManageruser: PrefsManageruser)
        fun onResultLogout()
        fun showMessage(message: String)
    }

}