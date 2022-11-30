package com.ilham.taspesialisbangunan.ui.userjasa.register

import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RegisterPresenter(val view: RegisterContract.View) : RegisterContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }



    override fun searchAlamatkecamatan(keyword: String) {
        view.onLoading(true)
        ApiConfig.endpoint.searchAlamatkecamatan(keyword).enqueue(object : Callback<ResponseALamatList>{
            override fun onResponse(
                call: Call<ResponseALamatList>,
                response: Response<ResponseALamatList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseALamatList: ResponseALamatList? = response.body()
                    view.onResultSearchalamatkecamatan(responseALamatList!!)
                }
            }

            override fun onFailure(call: Call<ResponseALamatList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}