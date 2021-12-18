package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa

import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaterialPresenter (var view: MaterialContract.View) : MaterialContract.Presenter {

    override fun getMaterial(kd_user : String) {
        view.onLoadingMaterial(true)
        ApiConfig.endpoint.myproductmaterial(kd_user).enqueue(object : Callback<ResponseMaterialList> {
            override fun onResponse(
                call: Call<ResponseMaterialList>,
                response: Response<ResponseMaterialList>
            ) {
                view.onLoadingMaterial(false)
                if (response.isSuccessful){
                    val responseMaterialList: ResponseMaterialList? = response.body()
                    view.onResultMaterial(responseMaterialList!!)
                }

            }

            override fun onFailure(call: Call<ResponseMaterialList>, t: Throwable) {
                view.onLoadingMaterial(false)
            }

        } )
    }

    override fun deleteMaterial(kd_material: Long) {
        view.onLoadingMaterial(true)
        ApiConfig.endpoint.deleteMaterial(kd_material).enqueue(object : Callback<ResponseMaterialUpdate>{
            override fun onResponse(
                call: Call<ResponseMaterialUpdate>,
                response: Response<ResponseMaterialUpdate>
            ) {
                view.onLoadingMaterial(false)
                if (response.isSuccessful) {
                    val responseMaterialUpdate: ResponseMaterialUpdate? = response.body()
                    view.onResultDelete( responseMaterialUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponseMaterialUpdate>, t: Throwable) {
                view.onLoadingMaterial(false)
            }

        })
    }

}