package com.ilham.taspesialisbangunan.ui.pelanggan.register

import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import java.io.File

interface RegisterPelangganContract {

    interface Presenter {

        fun insertRegister(username: String, email: String, alamat: String, phone: String, password: String, fcm:String
        )

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun showMessage(message: String)
    }
}