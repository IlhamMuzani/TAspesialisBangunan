package com.ilham.taspesialisbangunan.ui.home.fragment.Akun

import com.ilham.taspesialisbangunan.data.database.PrefsManager

class AkunuserPresenter (val view: AkunuserContract.View): AkunuserContract.Presenter {

    override fun doLogin(prefsManager: PrefsManager) {
        if (prefsManager.prefsIsLogin) view.onResultLogin(prefsManager)
    }

    override fun doLogout(prefsManager: PrefsManager) {
        prefsManager.logout()
        view.showMessage("Berhasil Keluar")
        view.onResultLogout()
    }

}