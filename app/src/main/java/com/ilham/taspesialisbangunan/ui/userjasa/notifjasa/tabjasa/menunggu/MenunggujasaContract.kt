package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.menunggu

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface MenunggujasaContract {

    interface Presenter {
        fun getPengajuanmenunggujasa(kd_jasa: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanmenunggu(loading: Boolean)
        fun onResultPengajuanmenunggu(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}