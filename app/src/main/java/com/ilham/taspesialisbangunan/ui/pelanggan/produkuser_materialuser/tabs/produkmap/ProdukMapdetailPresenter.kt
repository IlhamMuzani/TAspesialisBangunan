package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produkmap

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukMapdetailPresenter (var view: ProdukMapdetailContract.View) : ProdukMapdetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoadingProduk(false)
    }

    override fun getMapproduk() {
        view.onLoadingProduk(true)
        ApiConfig.endpoint.getProduk().enqueue(object : Callback<ResponseProdukList> {
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

}