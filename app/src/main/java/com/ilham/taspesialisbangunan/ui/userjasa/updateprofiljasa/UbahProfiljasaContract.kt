package com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseJasaUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.user.ResponsePelangganUpdate

interface UbahProfiljasaContract {

    interface Presenter {
        fun getDetailProfiljasa(id: String)
        fun updateProfiljasa(id: Long, username: String, email: String, alamat: String, phone: String
        )
        fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResultDetailProfiljasa(responseUserdetail: ResponseUserdetail)
        fun onResultUpdateProfiljasa(responseJasaUpdate: ResponseJasaUpdate)
        fun showMessage(message: String)
    }
}