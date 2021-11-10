package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.materialuser

import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaterialuserPresenter (var view: MaterialuserContract.View) : MaterialuserContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getMaterial() {
        view.onLoadingMaterialUser(true)
        ApiConfig.endpoint.getMaterial().enqueue(object : Callback<ResponseMaterialList>{
            override fun onResponse(
                call: Call<ResponseMaterialList>,
                response: Response<ResponseMaterialList>
            ) {
                view.onLoadingMaterialUser(false)
                if (response.isSuccessful) {
                    val responseMaterialList: ResponseMaterialList? = response.body()
                    view.onResultMaterialUser( responseMaterialList!! )
                }
            }

            override fun onFailure(call: Call<ResponseMaterialList>, t: Throwable) {
                view.onLoadingMaterialUser(false)
            }

        })
    }

    override fun searchMaterial(keyword: String) {
        view.onLoadingMaterialUser(true)
        ApiConfig.endpoint.searchMaterial(keyword).enqueue(object : Callback<ResponseMaterialList>{
            override fun onResponse(
                call: Call<ResponseMaterialList>,
                response: Response<ResponseMaterialList>
            ) {
                view.onLoadingMaterialUser(false)
                if (response.isSuccessful) {
                    val responseMaterialList: ResponseMaterialList? = response.body()
                    view.onResultMaterialUser(responseMaterialList!!)
                }
            }

            override fun onFailure(call: Call<ResponseMaterialList>, t: Throwable) {
                view.onLoadingMaterialUser(false)
            }

        })
    }

}