package com.ilham.taspesialisbangunan.ui.home.fragment.Akun.profil.updateprofil

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.userpelanggan.ResponsePelangganUpdate

interface UbahProfilContract {

    interface Presenter {
        fun getDetailProfil(id: Long)
        fun updateProfil(id: Long, username: String, email: String, alamat: String, phone: String
        )
        fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResultDetailProfil(responseUserdetail: ResponseUserdetail)
        fun onResultUpdateProfil(responsePelangganUpdate: ResponsePelangganUpdate)
        fun showMessage(message: String)
    }
}