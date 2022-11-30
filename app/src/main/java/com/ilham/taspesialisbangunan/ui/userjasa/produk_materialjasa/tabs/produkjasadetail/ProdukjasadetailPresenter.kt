package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.produkjasadetail

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.saran.ResponseRating
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukjasadetailPresenter  (var view: ProdukjasadetailContract.View) : ProdukjasadetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoadingProdukJasadetail(false)
    }

    override fun getProdukjasadetail(id: Long) {
        view.onLoadingProdukJasadetail(true,"Loading...")
        ApiConfig.endpoint.produkjasaDetail(id).enqueue(object : Callback<ResponseProdukDetail>{
            override fun onResponse(
                call: Call<ResponseProdukDetail>,
                response: Response<ResponseProdukDetail>
            ) {
                view.onLoadingProdukJasadetail(false)
                if (response.isSuccessful) {
                    val responseProdukDetail: ResponseProdukDetail? = response.body()
                    view.onResultProdukjasadetail( responseProdukDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukDetail>, t: Throwable) {
                view.onLoadingProdukJasadetail(false)
            }
        } )
    }

    override fun getRatingjasa(id: Long) {
        view.onLoadingProdukJasadetail(true,"Mendapatkan Detail...")
        ApiConfig.endpoint.getRating(id).enqueue(object : Callback<ResponseRating> {
            override fun onResponse(
                call: Call<ResponseRating>,
                response: Response<ResponseRating>
            ) {
                view.onLoadingProdukJasadetail(false)
                if (response.isSuccessful) {
                    val responseRating: ResponseRating? = response.body()
                    view.onResultgetRatingjasa( responseRating!! )
                }
            }

            override fun onFailure(call: Call<ResponseRating>, t: Throwable) {
                view.onLoadingProdukJasadetail(false)
            }

        })
    }
}