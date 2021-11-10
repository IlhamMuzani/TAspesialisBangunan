package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.Aduanjasa

import com.ilham.taspesialisbangunan.data.model.aduanjasa.ResponseAduanInsert
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ReportPresenter (val view: ReportContract.View) : ReportContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun insertAduanjasa(
        gambar: File,
        deskripsi: String
    ) {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
        val multipartBody: MultipartBody.Part? = MultipartBody. Part.createFormData("gambar",
        gambar.name, requestBody)

        view.onLoading(true)
        ApiConfig.endpoint.insertAduanjasa( multipartBody!!, deskripsi )
            .enqueue(object : Callback<ResponseAduanInsert>{
                override fun onResponse(
                    call: Call<ResponseAduanInsert>,
                    response: Response<ResponseAduanInsert>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseAduanInsert: ResponseAduanInsert? = response.body()
                        view.onResult(responseAduanInsert!!)
                    }
                }
                override fun onFailure(call: Call<ResponseAduanInsert>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }

}