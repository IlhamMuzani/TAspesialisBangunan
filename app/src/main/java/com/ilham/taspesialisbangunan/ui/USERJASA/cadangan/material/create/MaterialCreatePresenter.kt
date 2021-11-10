package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.material.create

import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MaterialCreatePresenter (val view: MaterialCreateContract.View) : MaterialCreateContract.Presenter{

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }


    override fun insertMaterial(
        jasausers_id: String,
        nama_toko: String,
        jenis_material: String,
        alamat: String,
        phone: String,
        harga: String,
        latitude: String,
        longitude: String,
        gambar: File,
        deskripsi: String
    ) {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/+"), gambar)
        val multipartBody: MultipartBody.Part? = MultipartBody.Part.createFormData( "gambar",
        gambar.name, requestBody)

        view.onLoading(true)
        ApiConfig.endpoint.insertMaterial(
            jasausers_id,
            nama_toko,
            jenis_material,
            alamat,
            phone,
            harga,
            latitude,
            longitude,
            multipartBody!!,
            deskripsi
        )
            .enqueue(object : Callback<ResponseMaterialUpdate>{
                override fun onResponse(
                    call: Call<ResponseMaterialUpdate>,
                    response: Response<ResponseMaterialUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful){
                        val responseMaterialUpdate: ResponseMaterialUpdate? = response.body()
                        view.onResult( responseMaterialUpdate!! )
                    }
                }

                override fun onFailure(call: Call<ResponseMaterialUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }

}