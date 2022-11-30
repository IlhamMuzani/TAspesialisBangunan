package com.ilham.taspesialisbangunan.ui.userjasa.register

import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import java.io.File

interface RegisterContract {

    interface Presenter {
        fun searchAlamatkecamatan(keyword: String)

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResultSearchalamatkecamatan(responseALamatList: ResponseALamatList)
        fun showMessage(message: String)
    }
}