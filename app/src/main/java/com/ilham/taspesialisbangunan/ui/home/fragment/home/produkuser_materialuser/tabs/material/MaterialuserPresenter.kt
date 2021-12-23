package com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.material

import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaterialuserPresenter (var view: MaterialuserContract.View) : MaterialuserContract.Presenter {
    override fun getMaterial() {
        view.onLoadingMaterialUser(true)
        ApiConfig.endpoint.getProdukmaterial().enqueue(object : Callback<ResponseProdukList>{
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingMaterialUser(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultMaterialUser( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingMaterialUser(false)
            }

        })
    }

    override fun searchMaterial(keyword: String) {
        view.onLoadingMaterialUser(true)
        ApiConfig.endpoint.searchMaterial(keyword).enqueue(object : Callback<ResponseProdukList>{
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingMaterialUser(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultMaterialUser(responseProdukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingMaterialUser(false)
            }

        })
    }

}