package com.ilham.taspesialisbangunan.ui.userjasa.profiljasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager

class AkunPresenter (val view: AkunContract.View): AkunContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun doLogin(prefsManager: PrefsManager) {
        if (prefsManager.prefsIsLogin) view.onResultLogin(prefsManager)
    }

    override fun doLogout(prefsManager: PrefsManager) {
        prefsManager.logout()
        view.showMessage("Berhasil Keluar")
        view.onResultLogout()
    }

}