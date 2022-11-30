package com.ilham.taspesialisbangunan.ui.fragment.Akun

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import java.io.File

interface AkunuserContract {

    interface Presenter {
        fun doLogout(prefsManager: PrefsManager)
        fun profilDetail(id:String)
        fun fotoProfil(id: Long, gambar: File)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onResultLogin(responseUser: ResponseUser)
        fun onLoadingAkun(loading: Boolean, message: String? = "Loading Akun...")
        fun onResultLogout()
        fun onResult(responseUserdetail: ResponseUserdetail)
        fun onResultUpdate(responseUserUpdate: ResponseUserUpdate)
        fun showMessage(message: String)
    }

}