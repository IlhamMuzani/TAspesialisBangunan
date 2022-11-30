package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukuserPresenter (var view: ProdukuserContract.View) : ProdukuserContract.Presenter {

    override fun getProduk() {
        view.onLoadingProduk(true)
        ApiConfig.endpoint.mytampilprodukuser().enqueue(object : Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingProduk(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultProduk( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingProduk(false)
            }

        })
    }

    override fun searchProduk(keyword: String) {
        view.onLoadingProduk(true)
        ApiConfig.endpoint.searchProdukjasa(keyword).enqueue(object : Callback<ResponseProdukList>{
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingProduk(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultProduk(responseProdukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingProduk(false)
            }

        })
    }

}