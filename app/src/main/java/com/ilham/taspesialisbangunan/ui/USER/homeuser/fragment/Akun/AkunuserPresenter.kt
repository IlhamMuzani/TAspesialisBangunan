package com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.Akun

import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.Akun.AkunuserContract

class AkunuserPresenter (val view: AkunuserContract.View): AkunuserContract.Presenter {

    override fun doLogin(prefsManageruser: PrefsManageruser) {
        if (prefsManageruser.prefsisLoginuser) view.onResultLogin(prefsManageruser)
    }

    override fun doLogout(prefsManageruser: PrefsManageruser) {
        prefsManageruser.logout()
        view.showMessage("Berhasil Keluar")
        view.onResultLogout()
    }

}