package com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.user.*
import java.io.File

interface UbahProfiljasaContract {

    interface Presenter {
        fun getDetailProfiljasa(id: String)
        fun updateProfiljasa(id: Long, username: String, kecamatan: String, kelurahan: String, alamat: String, phone: String, gambar: File?
        )
        fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser)

        fun searchAlamatkecamatanupdate(keyword: String)

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResultDetailProfiljasa(responseUserdetail: ResponseUserdetail)
        fun onResultUpdateProfiljasa(responseUserUpdate: ResponseUserUpdate)
        fun onResultSearchalamatkecamatanupdate(responseALamatList: ResponseALamatList)
        fun showMessage(message: String)
    }
}