package com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.Akun.profil.updateprofil

import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.data.model.userpelanggan.DataPelanggan
import com.ilham.taspesialisbangunan.data.model.userpelanggan.ResponsePelangganUpdate
import com.ilham.taspesialisbangunan.data.model.userpelanggan.ResponsePelanggandetail

interface UbahProfilContract {

    interface Presenter {
        fun getDetailProfil(id: Long)
        fun updateProfil(id: Long, username: String, email: String, alamat: String, phone: String
        )
        fun setPrefs(prefsManageruser: PrefsManageruser, dataPelanggan: DataPelanggan)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResultDetailProfil(responsePelanggandetail: ResponsePelanggandetail)
        fun onResultUpdateProfil(responsePelangganUpdate: ResponsePelangganUpdate)
        fun showMessage(message: String)
    }
}