package com.ilham.taspesialisbangunan.ui.pelanggan.invoice

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import java.io.File

interface InvoiceContract {

    interface Presenter {
        fun getDetail(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun showMessage(message: String)
    }
}