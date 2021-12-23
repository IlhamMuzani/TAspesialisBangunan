package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa.materialjasadetail

import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MaterialJasaDetailPresenter  (var view: MaterialJasaDetailContract.View) : MaterialJasaDetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoadingMaterialdetailjas(false)
    }


    override fun getMaterialdetail(id: Long) {
        view.onLoadingMaterialdetailjas(true)
        ApiConfig.endpoint.produkjasaDetail(id).enqueue(object : Callback<ResponseProdukDetail>{
            override fun onResponse(
                call: Call<ResponseProdukDetail>,
                response: Response<ResponseProdukDetail>
            ) {
                view.onLoadingMaterialdetailjas(false)
                if (response.isSuccessful) {
                    val responseProdukDetail: ResponseProdukDetail? = response.body()
                    view.onResultMaterialdetailjas( responseProdukDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukDetail>, t: Throwable) {
                view.onLoadingMaterialdetailjas(false)
            }
        } )
    }
}