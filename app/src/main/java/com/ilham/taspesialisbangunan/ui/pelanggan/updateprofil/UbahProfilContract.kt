package com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.user.ResponsePelangganUpdate
import java.io.File

interface UbahProfilContract {

    interface Presenter {
        fun getDetailProfil(id: String)
        fun updateProfil(id: Long, username: String, alamat: String, phone: String, phone_baru: String, gambar: File?
        )
        fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultDetailProfil(responseUserdetail: ResponseUserdetail)
        fun onResultUpdateProfil(responsePelangganUpdate: ResponsePelangganUpdate)
        fun showMessage(message: String)
    }
}