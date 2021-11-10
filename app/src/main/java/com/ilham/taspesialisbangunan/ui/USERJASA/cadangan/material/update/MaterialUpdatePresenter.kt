package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.material.update

import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialDetail
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MaterialUpdatePresenter (val view: MaterialUpdateContarct.View): MaterialUpdateContarct.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getDetail(kd_material: Long) {
        view.onLoading(true)
        ApiConfig.endpoint.getMaterialDetail(kd_material)
            .enqueue(object : Callback<ResponseMaterialDetail> {
                override fun onResponse(
                    call: Call<ResponseMaterialDetail>,
                    response: Response<ResponseMaterialDetail>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseMaterialDetail: ResponseMaterialDetail? = response.body()
                        view.onResultDetail(responseMaterialDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseMaterialDetail>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }

    override fun updateMaterial(
        kd_material: Long,
        nama_toko: String,
        jenis_material: String,
        alamat: String,
        phone: String,
        harga: String,
        latitude: String,
        longitude: String,
        gambar: File?,
        deskripsi: String
    ) {
        val requestBody: RequestBody
        val multipartBody: MultipartBody.Part

        if (gambar != null) {
            requestBody = RequestBody.create(MediaType.parse("image/+"), gambar)
            multipartBody = MultipartBody.Part.createFormData(
                "gambar",
                gambar.name, requestBody
            )
        } else {
            requestBody = RequestBody.create(MediaType.parse("image/+"), "")
            multipartBody = MultipartBody.Part.createFormData(
                "gambar",
                "", requestBody
            )
        }

        view.onLoading(true)
        ApiConfig.endpoint.updateMaterial(
            kd_material, nama_toko, jenis_material, alamat, phone, harga, latitude,
            longitude, multipartBody, deskripsi, "PUT"
        ).enqueue(object : Callback<ResponseMaterialUpdate> {
            override fun onResponse(
                call: Call<ResponseMaterialUpdate>,
                response: Response<ResponseMaterialUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseMaterialUpdate: ResponseMaterialUpdate? = response.body()
                    view.onResultUpdate(responseMaterialUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseMaterialUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}