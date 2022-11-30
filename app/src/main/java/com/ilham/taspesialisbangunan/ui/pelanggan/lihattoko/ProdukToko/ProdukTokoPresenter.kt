package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.ProdukToko

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukTokoPresenter (var view: ProdukTokoContract.View) : ProdukTokoContract.Presenter {

    override fun getProduk1(kd_user: Long) {
        view.onLoadingProduk1(true)
        ApiConfig.endpoint.tampilprodukperuser(kd_user).enqueue(object : Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingProduk1(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultProduk1( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingProduk1(false)
            }

        })
    }

    override fun searchProduk1(keyword: String) {
        view.onLoadingProduk1(true)
        ApiConfig.endpoint.searchProdukjasa(keyword).enqueue(object : Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingProduk1(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultProduk1(responseProdukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingProduk1(false)
            }

        })
    }

}