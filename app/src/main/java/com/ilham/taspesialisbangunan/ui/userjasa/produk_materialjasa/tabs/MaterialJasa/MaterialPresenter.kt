package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaterialPresenter(var view: MaterialContract.View) : MaterialContract.Presenter {

    override fun getProdukMat(kd_user: Long) {
        view.onLoadingProdukM(true)
        ApiConfig.endpoint.mymaterials(kd_user).enqueue(object : Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingProdukM(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultProdukM(responseProdukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingProdukM(false)
            }

        })
    }


    override fun deleteProduk(kd_produkjasa: Long) {
        view.onLoadingProdukM(true)
        ApiConfig.endpoint.deleteProduk(kd_produkjasa)
            .enqueue(object : Callback<ResponseProdukUpdate> {
                override fun onResponse(
                    call: Call<ResponseProdukUpdate>,
                    response: Response<ResponseProdukUpdate>
                ) {
                    view.onLoadingProdukM(false)
                    if (response.isSuccessful) {
                        val responseProdukUpdate: ResponseProdukUpdate? = response.body()
                        view.onResultDeleteM(responseProdukUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseProdukUpdate>, t: Throwable) {
                    view.onLoadingProdukM(false)
                }

            })
    }

}