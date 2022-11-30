package com.ilham.taspesialisbangunan.ui.userjasa.detailalamat

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.user.*
import java.io.File

interface DetailAlamatContract {

    interface Presenter {
        fun getDetailProfiljasa(id: String)
        fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser)

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResultDetailProfiljasa(responseUserdetail: ResponseUserdetail)
        fun showMessage(message: String)
    }
}