package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.material.materialdetail

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

class MaterialDetailPresenter  (var view: MaterialDetailContract.View) : MaterialDetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoadingMaterialdetail(false)
    }


    override fun getMaterialdetail(id: Long) {
        view.onLoadingMaterialdetail(true)
        ApiConfig.endpoint.produkDetail(id).enqueue(object : Callback<ResponseProdukDetail>{
            override fun onResponse(
                call: Call<ResponseProdukDetail>,
                response: Response<ResponseProdukDetail>
            ) {
                view.onLoadingMaterialdetail(false)
                if (response.isSuccessful) {
                    val responseProdukDetail: ResponseProdukDetail? = response.body()
                    view.onResultMaterialdetail( responseProdukDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukDetail>, t: Throwable) {
                view.onLoadingMaterialdetail(false)
            }
        } )
    }

}