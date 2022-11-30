package com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil.perbaruipassword

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import java.io.File

interface PasswordbaruContract {

    interface Presenter {
        fun Perbaruipassword(id: Long, password: String, password_confirmation : String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseUserUpdate: ResponseUserUpdate)
        fun showMessage(message: String)
    }
}