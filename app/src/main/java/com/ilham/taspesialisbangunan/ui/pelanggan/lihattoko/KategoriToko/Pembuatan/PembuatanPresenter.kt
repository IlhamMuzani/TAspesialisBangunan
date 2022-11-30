package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.KategoriToko.PembuatanKanopi

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembuatanKanopiPresenter (var view: PembuatanKanopiContract.View) : PembuatanKanopiContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoadingProduktoko(false)
    }

    override fun getProdukpembuatan(kd_user: Long, jenis_pembuatan: String) {
        view.onLoadingProduktoko(true)
        ApiConfig.endpoint.tampilprodukperjenispembuatan(kd_user, jenis_pembuatan).enqueue(object : Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingProduktoko(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultProduktoko( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingProduktoko(false)
            }

        })
    }

}