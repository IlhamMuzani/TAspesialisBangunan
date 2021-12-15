package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.detail

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate

interface DetailPengajuanContract {
    interface Presenter {
        fun getDetail(id: Long)
        fun hargaPengajuan(id: Long, harga: String)
        fun pengajuandiproses (id: Long)
        fun pengajuanselesai (id: Long)


    }
    interface View {
        fun initActivity()
        fun initListener()
        fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail)
        fun onResultUpdateharga(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onResultUpdateproses(responsePengajuanUpdate: ResponsePengajuanUpdate)
        fun onLoading(loading: Boolean)
    }
}