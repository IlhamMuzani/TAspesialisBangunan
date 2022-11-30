package com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.user.*
import java.io.File

interface UbahProfiljasaContract {

    interface Presenter {
        fun getDetailProfiljasa(id: String)
        fun updateProfiljasa(id: Long, username: String, nama_toko: String, kecamatan: String, kelurahan: String, alamat: String, latitude: String, longitude: String, phone: String, phone_baru: String, deskripsi: String, gambar: File?
        )
        fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser)

        fun searchAlamatkecamatanupdate(keyword: String)

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultDetailProfiljasa(responseUserdetail: ResponseUserdetail)
        fun onResultUpdateProfiljasa(responseUser: ResponseUser)
        fun onResultSearchalamatkecamatanupdate(responseALamatList: ResponseALamatList)
        fun showMessage(message: String)
    }
}