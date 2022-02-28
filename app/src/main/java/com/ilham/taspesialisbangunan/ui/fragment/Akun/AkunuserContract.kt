package com.ilham.taspesialisbangunan.ui.fragment.Akun

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail

interface AkunuserContract {

    interface Presenter {
        fun doLogout(prefsManager: PrefsManager)
        fun profilDetail(id:String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onResultLogin(responseUser: ResponseUser)
        fun onLoadingAkun(loading: Boolean)
        fun onResultLogout()
        fun onResult(responseUserdetail: ResponseUserdetail)
        fun showMessage(message: String)
    }

}