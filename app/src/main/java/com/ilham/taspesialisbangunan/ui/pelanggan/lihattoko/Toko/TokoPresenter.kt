package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.Toko

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokoPresenter (val view: TokoContract.View): TokoContract.Presenter {

//    override fun getNameToko(id: Long) {
//        view.onLoadingToko(true, "Menampilkan Toko...")
//        ApiConfig.endpoint.produkDetail(id).enqueue(object : Callback<ResponseProdukDetail>{
//            override fun onResponse(
//                call: Call<ResponseProdukDetail>,
//                response: Response<ResponseProdukDetail>
//            ) {
//                view.onLoadingToko(false)
//                if (response.isSuccessful) {
//                    val responseProdukDetail: ResponseProdukDetail? = response.body()
//                    view.onResultToko( responseProdukDetail!!)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseProdukDetail>, t: Throwable) {
//                view.onLoadingToko(false)
//            }
//        } )
//    }

    override fun getDetailProfiljasa(id: String) {
        view.onLoadingToko(true, "Menampilkan Toko...")
        ApiConfig.endpoint.userDetail(id).enqueue( object :
            Callback<ResponseUserdetail> {
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                view.onLoadingToko(false)
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    view.onResultDetailljasa( responseUserdetail!! )
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoadingToko(false)
            }

        })
    }


}